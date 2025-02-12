package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class ProductoController {

    private Long idCounter = 1L; // Contador de IDs

    @GetMapping
    public String listarProductos(HttpSession session, Model model) {
        // Obtener la lista de productos desde la sesión
        List<Producto> productos = (List<Producto>) session.getAttribute("productos");
        if (productos == null) {
            productos = new ArrayList<>();
            session.setAttribute("productos", productos);
        }

        model.addAttribute("productos", productos);
        return "index";
    }

    @GetMapping("/detalle/{id}")
    public String verDetalleProducto(@PathVariable Long id, HttpSession session, Model model) {
        List<Producto> productos = (List<Producto>) session.getAttribute("productos");
        if (productos == null) {
            throw new RuntimeException("No hay productos disponibles.");
        }

        // Buscar el producto por ID
        Optional<Producto> productoOpt = productos.stream().filter(p -> p.getId().equals(id)).findFirst();
        if (productoOpt.isEmpty()) {
            throw new RuntimeException("Producto no encontrado.");
        }

        model.addAttribute("producto", productoOpt.get());
        return "detalle";
    }

    @GetMapping("/nuevo")
    public String formularioProducto(Model model) {
        model.addAttribute("producto", new Producto());
        return "nuevo";
    }

    @PostMapping("/nuevo")
    public String agregarProducto(
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio") double precio,
            @RequestParam("imagen") MultipartFile imagen,
            HttpSession session,
            Model model) {
        try {
            // Verificar si hay una lista de productos en la sesión
            List<Producto> productos = (List<Producto>) session.getAttribute("productos");
            if (productos == null) {
                productos = new ArrayList<>();
                session.setAttribute("productos", productos);
            }

            // Guardar la imagen en una carpeta local
            String nombreArchivo = imagen.getOriginalFilename();
            Path rutaArchivo = Paths.get("src/main/resources/static/uploads/" + nombreArchivo);
            Files.createDirectories(rutaArchivo.getParent());
            Files.write(rutaArchivo, imagen.getBytes());

            // Crear un nuevo producto con la ruta de la imagen
            Producto producto = new Producto(nombre, descripcion, precio, "/uploads/" + nombreArchivo);
            productos.add(producto);

            model.addAttribute("mensaje", "Producto agregado exitosamente");
        } catch (IOException e) {
            model.addAttribute("error", "Error al subir la imagen: " + e.getMessage());
        }

        return "redirect:/";
    }

}

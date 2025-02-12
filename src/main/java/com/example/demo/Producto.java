package com.example.demo;

public class Producto {
    private Long id; // Identificador único
    private String nombre;
    private String descripcion;
    private double precio;
    private String imagen; // Ruta de la imagen

    private static long idCounter = 0; // Generador de IDs

    public Producto(String nombre, String descripcion, double precio, String imagen) {
        this.id = ++idCounter; // Generar un ID único
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
    }
    public Producto() {
        this.id = ++idCounter; // Generar un ID único
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}

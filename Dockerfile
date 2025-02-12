# Etapa 1: Construcción de la aplicación
FROM eclipse-temurin:17-jdk AS builder

# Definir directorio de trabajo
WORKDIR /app

# Copiar los archivos del proyecto
COPY . .

# Dar permisos de ejecución a Maven Wrapper
RUN chmod +x mvnw

# Construir el JAR de la aplicación
RUN ./mvnw clean package -DskipTests

# Etapa 2: Imagen ligera para ejecutar la aplicación
FROM eclipse-temurin:17-jre

# Definir directorio de trabajo
WORKDIR /app

# Copiar el JAR generado en la etapa de construcción
COPY --from=builder /app/target/*.jar app.jar

# Exponer el puerto de la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]

# Use Java 17
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the built jar
COPY target/myapp.jar app.jar

# Copy your database folder with JSON files
COPY database ./database

# Expose port (Render sets PORT automatically)
EXPOSE 8080

# Run the app
ENTRYPOINT ["java","-jar","app.jar"]

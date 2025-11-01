# Use Java 17
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy WAR file into the image
COPY target/*.war app.war

# Copy your JSON-based file database (if you have one)
COPY database ./database

# Expose port 8080
EXPOSE 8080

# Run Spring Boot app (WAR still runs fine via java -jar)
ENTRYPOINT ["java", "-jar", "app.war"]

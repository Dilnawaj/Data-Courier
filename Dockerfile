# Use official lightweight OpenJDK 21 image
FROM eclipse-temurin:21-jdk-alpine

# Set working directory
WORKDIR /app

# Copy built jar into the container
COPY target/datacourier-0.0.1-SNAPSHOT.jar app.jar

# Expose port 5000 (as you set in application.properties)
EXPOSE 5000

# Run the Spring Boot app on port 5000
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=5000"]

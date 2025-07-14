# Use official OpenJDK 21 base image
FROM eclipse-temurin:21-jdk-alpine

# Set working directory
WORKDIR /app

# Copy built jar file into the container
COPY target/*.jar app.jar

# Expose port 5000 (Beanstalk Docker expects this)
EXPOSE 5000

# Run the Spring Boot app on port 5000
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=5000"]

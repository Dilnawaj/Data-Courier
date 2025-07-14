# Stage 1: Build the application
FROM eclipse-temurin:21-jdk-alpine AS builder

# Install Maven
RUN apk add --no-cache maven

# Set working directory
WORKDIR /app

# Copy pom.xml first for better caching
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Create the final image
FROM eclipse-temurin:21-jdk-alpine

# Set working directory
WORKDIR /app

# Copy built jar from builder stage
COPY --from=builder /app/target/datacourier-0.0.1-SNAPSHOT.jar app.jar

# Expose port 5000
EXPOSE 5000

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=5000"]
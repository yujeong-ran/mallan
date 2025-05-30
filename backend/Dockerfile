# Base image
FROM eclipse-temurin:21-jdk-alpine

# Set working directory
WORKDIR /app

# Copy source code
COPY . .

# Grant permission to gradlew
RUN chmod +x ./gradlew

# Build the project
RUN ./gradlew clean build -x test

# Copy built jar
COPY build/libs/*.jar app.jar

# Expose port
EXPOSE 8080

# Run app
ENTRYPOINT ["java", "-jar", "app.jar"]

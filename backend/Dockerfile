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

# Expose port
EXPOSE 8080

# Run app
ENTRYPOINT ["java", "-jar", "build/libs/backend-0.0.1-SNAPSHOT.jar"]

# Based on Java 21 Image
FROM eclipse-temurin:21-jdk-alpine

# Create a working directory
WORKDIR /app

# Copy jar file
COPY build/libs/backend-0.0.1-SNAPSHOT.jar app.jar

# Port Open
EXPOSE 8080

# Execute Commands
ENTRYPOINT ["java", "-jar", "app.jar"]
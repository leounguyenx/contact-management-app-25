# Use OpenJDK 21 as the base image
FROM openjdk:21-slim

# Set the working directory
WORKDIR /app

# Copy the Maven wrapper and pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Copy the source code
COPY src src

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose the port the app runs on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "target/contactmgt-v2-0.0.1-SNAPSHOT.jar"] 
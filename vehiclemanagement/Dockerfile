# 1. Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# 2. Set a working directory inside the container
WORKDIR /app

# 3. Copy the built JAR file from local to the container
COPY target/vehiclemanagement-0.0.1-SNAPSHOT.jar /app/vehiclemanagement-0.0.1-SNAPSHOT.jar

# 4. Expose the application port (Spring Boot default is 8080)
EXPOSE 8081

# 5. Define the command to run the application
CMD ["java", "-jar", "vehiclemanagement-0.0.1-SNAPSHOT.jar"]

# Use the official OpenJDK 11 image as the base image
FROM openjdk:11-jre-slim

# Metadata as described above
LABEL maintainer="mahfuzhasan@example.com" \
      version="1.0" \
      description="Docker image for  Spring Boot Application"

# Specify a base directory inside the container
WORKDIR /app

# Copy the JAR file built by Maven into the container
COPY target/Devops_Infinity-0.0.1-SNAPSHOT.jar /app/app.jar

# Command to run the application
ENTRYPOINT ["java","-jar","/app/app.jar"]
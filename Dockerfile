# Use OpenJDK 21 as the base image
FROM openjdk:21-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the build output to the container
COPY target/*.jar /app/app.jar

# Expose the port on which the application runs
EXPOSE 9000

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

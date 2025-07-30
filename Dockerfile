#FROM maven:3.8.7-eclipse-temurin-19 AS build
#WORKDIR /app
#COPY pom.xml .
#RUN mvn -B clean package -DskipTests
#
#FROM eclipse-temurin:19-jdk
#WORKDIR /app
#COPY --from=build /app/target/*.jar SharpWallet.jar
#EXPOSE 9060
#ENTRYPOINT ["java", "-jar", "SharpWallet.jar"]

# Use Java 21 as the base image
FROM eclipse-temurin:21-jdk-jammy

# Set working directory
WORKDIR /app

# Copy the Maven wrapper files
COPY .mvn/ .mvn/

# Download dependencies (this layer will be cached if pom.xml doesn't change)
RUN ./mvn dependency:go-offline

# Copy the project source
COPY src ./src

# Build the application
RUN ./mvn clean package -DskipTests

# Use a smaller runtime image
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copy the jar file from the build stage
COPY --from=0 /app/target/*.jar SharpWallet.jar

# Expose the port the app runs on
EXPOSE 9060

# Command to run the application
ENTRYPOINT ["java", "-jar", "SharpWallet.jar"]
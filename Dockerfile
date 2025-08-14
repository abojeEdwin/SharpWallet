# Use Java 21 as the base image
FROM eclipse-temurin:21-jdk-jammy AS build

# Set working directory
WORKDIR /app

# Copy the Maven wrapper and pom.xml to leverage Docker layer caching
COPY mvnw .mvn/ pom.xml ./

# Download dependencies (this layer will be cached if pom.xml doesn't change)
RUN mvn dependency:go-offline -B

# Copy the project source
COPY src ./src

# Build the application
RUN ./mvn package -DskipTests -B

# Use a smaller runtime image
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/*.jar SharpWallet.jar

# Expose the port the app runs on
EXPOSE 9060

# Command to run the application
ENTRYPOINT ["java", "-jar", "SharpWallet.jar"]
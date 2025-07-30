FROM maven:3.8.7-eclipse-temurin-19 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -B clean package -DskipTests

FROM eclipse-temurin:19-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar SharpWallet.jar
EXPOSE 9060
ENTRYPOINT ["java", "-jar", "SharpWallet.jar"]


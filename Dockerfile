# Replace second stage image
FROM openjdk:17-jdk-slim as build
WORKDIR /app
COPY pom.xml .
COPY src src
RUN apt-get update && apt-get install -y maven
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/hanna-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
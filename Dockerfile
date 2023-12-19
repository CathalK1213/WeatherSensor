# Using Maven image with OpenJDK 17 as the base image
FROM maven:3.8.4-openjdk-17-slim

WORKDIR /app

COPY . .

RUN mvn clean package

COPY target/**.jar weather-api.jar

EXPOSE 8080

CMD ["java", "-jar", "weather-api.jar"]

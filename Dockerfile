FROM openjdk:17-slim

WORKDIR /data/www

COPY build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

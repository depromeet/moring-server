FROM openjdk:17-slim

WORKDIR /data/www

COPY moring-api/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

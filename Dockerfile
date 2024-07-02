FROM openjdk:17-slim

WORKDIR /data/www

COPY build/libs/*.jar app.jar
COPY --from=docker.elastic.co/observability/apm-agent-java:latest /usr/agent/elastic-apm-agent.jar elastic-apm-agent.jar

ENTRYPOINT ["java", "-javaagent:elastic-apm-agent.jar", "-jar", "app.jar"]

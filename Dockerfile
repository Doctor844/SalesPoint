# Dockerfile approach when moving to production, now we are using direct image
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY target/*.jar SalesPoint.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "SalesPoint.jar"]
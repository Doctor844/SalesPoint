# Dockerfile approach when moving to production, now we are using direct image
FROM openjdk:17
WORKDIR /app
COPY target/SalesPoint.jar SalesPoint.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "SalesPoint.jar"]
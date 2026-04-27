FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
RUN wget github.link
CMD ["java", "-jar", "app.jar"]
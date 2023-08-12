FROM openjdk:17-alpine

COPY */quickstart/build/libs/*.jar app.jar

ENTRYPOINT [ "sh", "-c", "java -jar /app.jar"]
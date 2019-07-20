FROM openjdk:12-alpine
COPY ./target/demo-0.0.1-SNAPSHOT.jar /app
WORKDIR /app
CMD ["java", "-jar","demo-0.0.1-SNAPSHOT.jar"]

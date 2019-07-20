FROM openjdk:12-alpine
COPY ./target/demo-0.0.1-SNAPSHOT.jar /var/www/app/
WORKDIR /var/www/app/
CMD ["java", "-jar","demo-0.0.1-SNAPSHOT.jar"]

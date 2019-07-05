FROM openjdk:8
VOLUME /tmp
# docker-abc-0.0.1-SNAPSHOT.jar为maven项目project.artifactId-project.version
ADD demo-0.0.1-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 8761
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
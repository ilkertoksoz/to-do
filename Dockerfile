FROM openjdk:8

WORKDIR /app

COPY target/to-do-docker.jar /app/to-do-docker.jar

EXPOSE 9080

ENTRYPOINT ["java", "-jar", "to-do-docker.jar"]
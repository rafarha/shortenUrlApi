FROM openjdk:8
ADD target/shorten-url-api.jar docker-shorten-url-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "docker-shorten-url-api.jar"]
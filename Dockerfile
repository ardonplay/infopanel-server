FROM openjdk:22-ea-17-jdk
COPY . /server
WORKDIR /server
CMD ["./gradlew", "clean", "bootJar"]
COPY build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.datasource.url=jdbc:postgresql://${DATABASE_CONTAINER_NAME}:${DATABASE_PORT}/${DATABASE_NAME}"]
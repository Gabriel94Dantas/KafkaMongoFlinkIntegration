# Build stage with maven, that's generates our jar
FROM maven:3.8.5-jdk-8 AS Build
ADD . /app
WORKDIR /app
RUN mvn clean compile assembly:single

#Run stage
FROM openjdk:8
COPY --from=build /app/target/KafkaMongoFlinkIntegration-1.0-SNAPSHOT-jar-with-dependencies.jar /usr/local/lib/KafkaMongoFlinkIntegration.jar
CMD [ "java", "-jar", "/usr/local/lib/KafkaMongoFlinkIntegration.jar" ]
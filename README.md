# Java Flink Runner
## Introduction

This application is responsible to consume all events posted on events cluster and transform them to produce events to another cluster and save them on MongoDB.

The MongoDB works as event log for us.

## Features

- Flink pipelines work as jobs, so we build a job that consume all events from cluster, transform and put them on another cluster and finally save on MongoDB, our event log.

An important note is that we treat each event, so we work in like a event application.

## Tech

Java Flink Runner uses only Java but we will specify the most important libs:

```
<dependencies>
    <dependency>
      <groupId>org.apache.flink</groupId>
      <artifactId>flink-core</artifactId>
      <version>1.14.4</version>
    </dependency>
    <dependency>
      <groupId>org.apache.flink</groupId>
      <artifactId>flink-connector-kafka_2.12</artifactId>
      <version>1.14.4</version>
    </dependency>
    <dependency>
      <groupId>org.apache.flink</groupId>
      <artifactId>flink-streaming-java_2.12</artifactId>
      <version>1.14.4</version>
      <scope>provided</scope>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.apache.flink/flink-clients -->
    <dependency>
      <groupId>org.apache.flink</groupId>
      <artifactId>flink-clients_2.12</artifactId>
      <version>1.14.4</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/com.google.code.gson/gson -->
    <dependency>
      <groupId>com.google.code.gson</groupId>
      <artifactId>gson</artifactId>
      <version>2.9.0</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients -->
    <dependency>
      <groupId>org.apache.kafka</groupId>
      <artifactId>kafka-clients</artifactId>
      <version>3.1.0</version>
    </dependency>
    <dependency>
      <groupId>org.mongodb</groupId>
      <artifactId>mongodb-driver-core</artifactId>
      <version>4.5.1</version>
    </dependency>
    <dependency>
      <groupId>org.mongodb</groupId>
      <artifactId>mongodb-driver-sync</artifactId>
      <version>4.5.1</version>
    </dependency>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.13</version>
      <scope>test</scope>
    </dependency>
  </dependencies>
```

- Apache Flink is a cross-platform stack for building Stateful Serverless applications
- Kafka client is the default connector to Kafka when we code in Java
- Mongo driver is the connector to MongoDB

## Installation

This is a common java application but we build this as a Maven project so if you want to run locally this application you have to follow these steps:

- You have to install JDK 8
- You have to install apache maven
- You have to look if apache maven can see your JDK folder, to make sure you have to open your mvn file and add this line on begin of the file.
```
JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_271.jdk/Contents/Home
```
- After that you have to run this command.
```
mvn clean compile assembly:single
```
- This command will generate a jar file with all depedencies
- Finally you have to run

```
java -jar yourFile.jar
```

## Development

This project was initiated by Gabriel Araujo Dantas a Brazilian Computer Engineer and my colleague Lucas Henrique A. de Paula a Brazilian Computer Scientist.

## Docker

We begin the creation of Devops part that isn't the way we want, but we start the develop of this feature. For while, we have some important considerations:

- When you work with two docker-compose you need to create the network first (
  ```
  docker network create --driver=bridge  --subnet=172.18.0.0/16  --ip-range=172.18.0.0/24  --gateway=172.18.0.1   my_network
  ```
  ), this is necessary because you have use same network on all docker-compose

- After that you have to add on your kafka docker-compose this (
```
networks: 
  default: 
    external: 
      name: kafka_confluent_network
```
      )
- And now you have to run all docker-compose and you are ready to use this API
```
docker-compose up -d
```
or
```
docker-compose up -d --build
```
.

## Important information

This application is a part of events plataform, so you have to create all other parts of this plataform

Another important information is we use the confluent kafka community for this test. You can find that on this link: https://github.com/confluentinc/cp-all-in-one/tree/7.0.1-post/cp-all-in-one-community

We use as interface REST to kafka our other project:
https://github.com/Gabriel94Dantas/api-eventos

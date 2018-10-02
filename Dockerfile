FROM openjdk:8-jre-alpine

ADD target/NDB-0.0.1-SNAPSHOT.jar NDB-0.0.1-SNAPSHOT.jar

EXPOSE 8081
ENTRYPOINT ["java","-jar","NDB-0.0.1-SNAPSHOT.jar"]

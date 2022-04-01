FROM openjdk:8
EXPOSE 3010

ADD target/post-service.jar post-service.jar
ENTRYPOINT ["java","-jar","post-service.jar"]
FROM openjdk:11
ARG JAR_FILE=target/neoflex-training.jar
WORKDIR /opt/app  
COPY ${JAR_FILE} app.jar  
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 8080/tcp
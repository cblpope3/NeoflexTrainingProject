# Build stage
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.skip

# Package stage
FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/neoflex-training.jar /usr/local/lib/neoflex-training.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/neoflex-training.jar"]

### Old configuration, without maven packaging
#ARG JAR_FILE=target/neoflex-training.jar
#WORKDIR /opt/app
# COPY ${JAR_FILE} app.jar
# ENTRYPOINT ["java","-jar","app.jar"]
# EXPOSE 8080/tcp
#########Maven build stage########
#FROM maven:3.6-jdk-11 as maven_build
#WORKDIR /app
#
##copy pom
#COPY pom.xml .
#
##resolve maven dependencies
#RUN mvn clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r target/
#
##copy source
#COPY src ./src
#
## build the app (no dependency download here)
#RUN mvn clean package  -Dmaven.test.skip

#COPY ./wait-for-it.sh /wait-for-it.sh
#RUN chmod +x wait-for-it.sh

FROM openjdk:11
ADD target/app.jar app.jar
EXPOSE 8081
COPY scripts/wait-for-it.sh /wait-for-it.sh
RUN chmod +x wait-for-it.sh
ENTRYPOINT ["java", "-jar", "app.jar"]





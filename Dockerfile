#########Maven build stage########
FROM maven:3.6-jdk-11 as maven_build
WORKDIR /app
COPY . .
RUN mvn clean package -Dmaven.test.skip

#########Run jar stage########
FROM adoptopenjdk:11-jre-hotspot as application
WORKDIR /app
COPY --from=maven_build /app/target/WegoAcademy-0.0.1-SNAPSHOT.jar app.jar

COPY scripts/wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]





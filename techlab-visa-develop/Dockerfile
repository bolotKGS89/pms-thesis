# Use an official OpenJDK runtime as a base image
FROM maven:3.8.4-openjdk-17
WORKDIR .
COPY . .
RUN mvn clean package

COPY /visa-application/target/visa-application-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
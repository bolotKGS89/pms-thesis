# Use a Maven image to build the project
FROM maven:3.8.4-openjdk-17 as build
WORKDIR /app
COPY . .
RUN mvn clean package

# Create a new image for the runtime
FROM openjdk:17
WORKDIR /app
COPY --from=build /app/pms-application/target/pms-application-1.0-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
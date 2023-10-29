FROM maven:3.8.4-openjdk-17
WORKDIR .
COPY . .
RUN mvn clean package

COPY /paypal-application/target/paypal-application-1.0-SNAPSHOT.jar app.jar
EXPOSE 8082

CMD ["java", "-jar", "app.jar"]
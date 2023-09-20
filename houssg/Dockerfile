FROM maven:3.9.4-amazoncorretto-20-debian as builder

WORKDIR /app
COPY pom.xml .
RUN mvn clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r target/
COPY . .
RUN mvn clean package  -Dmaven.test.skip


FROM openjdk:21-ea-17-jdk

WORKDIR /app
COPY --from=builder /app/target/houssg-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 3200
CMD ["java", "-jar", "app.jar"]

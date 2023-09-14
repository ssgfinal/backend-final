# 베이스 이미지 정의
FROM maven:3.9.4-amazoncorretto-20-debian as builder
# 작업 디렉토리 설정
WORKDIR /app

COPY pom.xml .

RUN mvn clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r target/

COPY . .

RUN mvn clean package  -Dmaven.test.skip

FROM openjdk:21-ea-17-jdk

WORKDIR /app

COPY --from=builder /app/target/houssg-0.0.1-SNAPSHOT.jar /app/app.jar
# 포트 노출
EXPOSE 3200
# 애플리케이션 실행
CMD ["java", "-jar", "app.jar"]
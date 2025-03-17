
# OpenJDK 설치 !!!필요한 JDK 버전에 맞는 설치!!!
FROM openjdk:17-alpine

# 프로젝트 jar 파일 복사
ARG JAR_FILE=/build/libs/noteback-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /app.jar

# 외부 호스트 8080 포트로 노출
EXPOSE 8080

# 컨테이너 실행 시 JAR 파일 실행
# # Spring Boot 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]

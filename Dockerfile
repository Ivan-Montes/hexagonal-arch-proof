# BUILD STAGE
FROM maven:3.9.6-eclipse-temurin-17-alpine as builder

WORKDIR /app

COPY pom.xml ./
COPY src ./src
RUN mvn clean install -DskipTests

# DEPLOY STAGE
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=builder /app/target/*.jar ./app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","./app.jar"]
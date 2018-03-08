FROM maven:3.5.2-jdk-8-alpine as builder
COPY . /
WORKDIR /
RUN mvn install -DskipTests

FROM java:8
COPY --from=builder /target/*.jar /app.jar
ENTRYPOINT java -jar app.jar -Dspring.application.name=$APP_NAME -Dserver.port=$PORT -Ddownstream.server.url=$DOWNSTREAM
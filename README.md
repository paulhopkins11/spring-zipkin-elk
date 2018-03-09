# Distributed Tracing & Centralised Log Management

This project aims to showcase 2 key features of a microservices application. The first is distributed tracing. The second is the implementation of a centralised log management system. This application has been developed using Spring Boot

## Spring Cloud Sleuth & Zipkin

I am using Spring Cloud Sleuth for distributed tracing and Zipkin for visualisation.

## Logstash, Elasticsearch & Kibana

Spring is logging using Slf4j with Logback configured to write to Logstash. Logstash is writing to Elasticsearch. Kinana is used to visualise the logs

## Running the application
Follow these steps:

1. Build the Spring application
```
> ./mvnw install
```
1. Start the infrastructure
```
> docker-compose up
```
1. Start service 1
```
> ./mvnw spring-boot:run -Dspring.application.name=upstream -Dserver.port=8081 -Ddownstream.server.url=http://localhost:8082
```
1. Start service 2
```
> ./mvnw spring-boot:run -Dspring.application.name=middle -Dserver.port=8082 -Ddownstream.server.url=http://localhost:8083
```
1. Start service 3
```
> ./mvnw spring-boot:run -Dspring.application.name=downstream -Dserver.port=8083
```

## Invoking the endpoints
1. Call the rest endpoing a few times.
```
> curl localhost:8081/zipkin
upstream -> middle -> downstream
```

## Configure Kibana and view the logs
1. Open Kibana http://localhost:5601/
2. Under "Management" create an Index Pattern. `logstash-*` and `@timestamp`
3. Choose "Discover" to view the log entries

NB. If you want to filter on a particular trace you can use something similar to this
```
message: ">>> Call*" AND traceId: "7f3a243d1b53720f"
```

## View the tracing in Zipkin
1. Open Zipkin http://localhost:9411/
2. Choose "Find Traces"
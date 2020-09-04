FROM alpine:3.11.6

RUN apk update && \
    apk add openjdk11

COPY ./target/tof-data-processor-*.jar /app/app.jar
COPY ./target/lib /app/lib

WORKDIR /app/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
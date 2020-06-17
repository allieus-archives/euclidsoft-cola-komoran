FROM adoptopenjdk:8-jdk-hotspot AS builder

WORKDIR /code
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN chmod +x ./gradlew
RUN ./gradlew jar

FROM adoptopenjdk:8-jdk-hotspot
COPY --from=builder /code/build/libs/*.jar app.jar

EXPOSE 4567
CMD java -Djava.security.egd=file:/dev/urandom -jar app.jar

FROM adoptopenjdk:11-jdk-hotspot AS builder

WORKDIR /code
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN chmod +x ./gradlew
RUN ./gradlew jar

COPY userdic.txt .

FROM adoptopenjdk:11-jdk-hotspot
COPY --from=builder /code/build/libs/*.jar app.jar
COPY --from=builder /code/userdic.txt .

EXPOSE 50051

CMD java \
    -Djava.security.egd=file:/dev/urandom \
    -jar app.jar

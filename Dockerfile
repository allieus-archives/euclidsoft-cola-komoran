FROM adoptopenjdk:11-jdk-hotspot AS builder

WORKDIR /code
COPY gradlew .
COPY gradle gradle
RUN chmod +x ./gradlew
RUN ./gradlew --version

COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN ./gradlew jar

FROM adoptopenjdk:11-jdk-hotspot

WORKDIR /code
COPY --from=builder /code/build/libs/*.jar app.jar
COPY userdic*.txt ./

EXPOSE 50051

CMD java \
    -Djava.security.egd=file:/dev/urandom \
    -jar app.jar


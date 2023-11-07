FROM  amazoncorretto:11-alpine-jdk
MAINTAINER atolpeko <alextolpeko@gmail.com>

ARG VERSION
COPY /target/indexer-${VERSION}.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]

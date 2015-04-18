FROM docker.magnet.me/magnet-java

MAINTAINER Alex Kolpa "https://github.com/AlexKolpa"

ADD target/magnet-microservice.jar /opt/magnet-microservice/magnet-microservice.jar
ADD docker_fixtures/entry.sh /opt/magnet-microservice/entry.sh
ADD config.yml /opt/magnet-microservice/config.yml

VOLUME ['/opt/magnet-microservice/logs']

WORKDIR /opt/magnet-microservice

EXPOSE 8080
EXPOSE 8081

ENTRYPOINT bash entry.sh

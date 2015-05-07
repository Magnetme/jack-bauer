FROM docker.magnet.me/magnet-java

MAINTAINER Michael de Jong "https://github.com/michaeldejong"

ADD target/jack-bauer.jar /opt/jack-bauer/jack-bauer.jar
ADD docker_fixtures/entry.sh /opt/jack-bauer/entry.sh
ADD config.yml /opt/jack-bauer/config.yml

VOLUME ['/opt/jack-bauer/logs']

WORKDIR /opt/jack-bauer

EXPOSE 8080

ENTRYPOINT bash entry.sh

#!/bin/sh
set -e
ARGS=""

JVM_ARGS="-Xmx512m -Djava.net.preferIPv4Stack=true -Djava.awt.headless=true"

java -jar $JVM_ARGS magnet-microservice.jar db status config.yml $ARGS
java -jar $JVM_ARGS magnet-microservice.jar db migrate config.yml $ARGS
java -jar $JVM_ARGS magnet-microservice.jar server config.yml $ARGS

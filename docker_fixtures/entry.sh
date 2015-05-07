#!/bin/sh
set -e
ARGS=""

JVM_ARGS="-Xmx64m -Djava.net.preferIPv4Stack=true -Djava.awt.headless=true -XX:+UseParNewGC -XX:+UseConcMarkSweepGC"

java -jar $JVM_ARGS jack-bauer.jar server config.yml $ARGS

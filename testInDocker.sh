#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

INSTANCE=${1-"default"}

source $DIR/docker/setup.sh

echo $(getAddress)

function stopAll() {
  exitCodeWas=$?
  bash docker/teardown.sh $INSTANCE
  exit $exitCodeWas
}
trap stopAll EXIT

getAddress

mvn clean package install
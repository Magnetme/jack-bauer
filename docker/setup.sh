#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

INSTANCE=${1-"default"}

if [ ! -f $PWD/docker/create_db.sh ]; then
  echo "Entry point file not found"
  exit 1;
fi

if docker ps -a | grep --quiet postgres-$INSTANCE; then
        	echo "Stopping postgres-$INSTANCE"
        	docker stop -t 0 postgres-$INSTANCE # we stop with t=0 because we don't wait for a nice shutdown. We destroy everythig anyway.
          docker rm -f postgres-$INSTANCE
fi

VOLUME="-v $PWD/docker/create_db.sh:/docker-entrypoint-initdb.d/z_create_db.sh"

docker run -P -d $VOLUME --name postgres-$INSTANCE docker.magnet.me/in-memory-postgres # z to make it the last statement.

PORT=$(docker port postgres-$INSTANCE 5432)

echo "Started postgres-$INSTANCE. Port is $PORT"

function getAddress() {
  if type boot2docker >/dev/null 2>&1 ; then
    ADDRESS=${PORT/0.0.0.0/$(boot2docker ip)}
    echo "Boot2docker so Postgres is at $ADDRESS"
  else
    ADDRESS=${PORT/0.0.0.0/127.0.0.1}
    echo "Postgres is at $ADDRESS"
  fi
}

getAddress

TRIES=40
echo "trying ${ADDRESS/:/ }"
until nc -z ${ADDRESS/:/ } 2>/dev/null # Wait for command to succeed. It fails while the postgres server is still starting.
do
    echo "Waiting for Postgres to come online. $TRIES more tries."
    sleep 0.5
    ((TRIES--))

    if [ "$TRIES" -le "0" ]; then
        echo "Could not connect to postgres! Stopping the container." >&2
        exit 1
    fi
done
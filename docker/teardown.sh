#!/bin/bash
INSTANCE=${1-"default"}

echo "Stopping"
docker stop postgres-$INSTANCE

echo "Removing"
docker rm postgres-$INSTANCE

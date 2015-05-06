#!/bin/bash

function getAddress() {
  if type boot2docker >/dev/null 2>&1 ; then
    ADDRESS=${PORT/0.0.0.0/$(boot2docker ip)}
  else
    ADDRESS=${PORT/0.0.0.0/127.0.0.1}
  fi
}

getAddress


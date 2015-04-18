#!/bin/bash
echo "******CREATING DOCKER DATABASE******"
gosu postgres postgres --single <<- EOSQL
  DROP ROLE IF EXISTS dispatcher;
  CREATE USER magnet-service WITH PASSWORD 'password';
  CREATE DATABASE magnet-service OWNER magnet-service;
  GRANT ALL PRIVILEGES ON DATABASE magnet-service TO magnet-service;
EOSQL

gosu postgres postgres --single magnet-service <<- EOSQL
  ALTER SCHEMA public OWNER TO magnet-service;
EOSQL


echo "host    all             magnet-service             0.0.0.0/0            md5" > /var/lib/postgresql/data/pg_hba.conf

echo "******DOCKER DATABASE CREATED******"

cat /var/lib/postgresql/data/pg_hba.conf

#!/usr/bin/env bash

function compose {
    docker-compose --file docker-compose.yml --file ete-docker-compose.yml $*
}

compose kill && \
compose rm -f && \
./gradlew shadowJar && \
cd web-app && npm install && npm run build && cd .. && \
compose build && \
docker-compose up -d && \
docker-compose --file ete-docker-compose.yml up -d selenium && \
compose run ete

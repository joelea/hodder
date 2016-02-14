#!/usr/bin/env bash

function compose {
    docker-compose --file docker-compose.yml --file ete-docker-compose.yml $*
}

compose kill && \
compose rm -f && \
./gradlew shadowJar && \
cd web-app && npm install && npm run build && cd .. && \
compose build && \
compose up -d && \
compose logs ete

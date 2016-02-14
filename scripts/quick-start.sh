#!/usr/bin/env bash

./gradlew shadowJar && \
cd web-app && npm install && npm run build && cd .. && \
docker-compose up -d && \
open http://$(docker-machine ip $DOCKER_MACHINE_NAME)

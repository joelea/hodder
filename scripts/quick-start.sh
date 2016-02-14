#!/usr/bin/env bash

./gradlew shadowJar && \
cd web-app && npm build && cd .. && \
docker-compose up -d

#!/usr/bin/env bash

open vnc://secret:secret@$(docker-machine ip $DOCKER_MACHINE_NAME)

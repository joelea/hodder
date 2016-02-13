# Hodder

A dummy implementation of a reactive application with reads and writes completely decoupled.

#### Setup

**NOTE: Requires docker-compose 1.6**

To try it out (this currently sucks, but will get easier):

1. edit `KAFKA_ADVERTISED_HOST_NAME` in the docker-compose.yml to be your docker machine IP
2. run `npm build` in the web-app directory
3. run `docker-compose up -d`
4. run `open http://$(docker-machine ip $DOCKER_MACHINE_NAME)`
5. Click `increment` to your heart's content!

#### Components

* Clicking increment makes a post request to the server
* The server pushes an event into kafka in the `example-topic`
* the same server (though this is in no way necessary) consumes the event and makes a db write based on it

Completely independently:

* The frontend generates a stream of db state which it then renders as the counter. This should be replaced with websockets soon to avoid the crazy polling.

 

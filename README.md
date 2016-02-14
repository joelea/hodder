# Hodder

A dummy implementation of a reactive application with reads and writes completely decoupled.

#### Setup

**NOTE: Requires docker-compose 1.6**

To try it out run ./scripts/quick-start.sh (read the script to find out what it does)

You can run the ETE tests with ./scripts/ete-tests.sh and VNC into the selenium container for the tests using ./scripts/watch-ete-tests.sh


#### Components

* Clicking increment makes a post request to the server
* The server pushes an event into kafka in the `hodder.todos`
* the same server (though this is in no way necessary) consumes the event and makes a db write based on it

Completely independently:

* The frontend generates a stream of db state which it then renders as the list of todos. This should be replaced with websockets soon to avoid the crazy polling.

#### Docs

If you want to see hwo the project evolved feel free to look at doc/adr (architecture decision records) and doc/learnings (for less structured rambling)


 

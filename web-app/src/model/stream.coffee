Bacon = require 'baconjs'
$ = require 'jquery'

module.exports = (url) ->
  webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + url)
  messages = new Bacon.Bus()
  webSocket.onmessage = (msg) -> messages.push(msg.data)
  return messages

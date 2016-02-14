Bacon = require 'baconjs'
$ = require 'jquery'

module.exports = (url) ->
  return Bacon.interval(10, {url})
    .flatMapFirst((request) -> Bacon.fromPromise($.ajax(request)))

Bacon = require 'baconjs'
$ = require 'jquery'

module.exports = (url) ->
  return Bacon.interval(30, url: '/api/count')
    .flatMapFirst((request) -> Bacon.fromPromise($.ajax(request)))

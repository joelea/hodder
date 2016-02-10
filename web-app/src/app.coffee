Bacon = require 'baconjs'
{h} = require 'virtual-dom'
attach = require 'bacon-dom'
write = require './model/write'
stream = require './model/stream'

DomDelegator = require 'dom-delegator'
domDelegator = new DomDelegator()

count = stream("/api/count")

increment = -> write('/api/increment')

render = (n) ->
  h 'components',[
    h 'button', { 'ev-click': increment }, 'increment'
    h 'p', "#{n}"
  ]

html = count.map(render)
rootElement = document.getElementById('app')
attach(html).to(rootElement)

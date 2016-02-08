Bacon = require 'baconjs'
{h} = require 'virtual-dom'
attach = require 'bacon-dom'
write = require './model/write'
read = require './model/read'

DomDelegator = require 'dom-delegator'
domDelegator = new DomDelegator()

kafka = new Kafka(zookeeper: ['zookeeper:2181'])

increments = new Bacon.Bus()

count = increments.scan(0, (current) -> current + 1)

increment = write('increment')

render = (n) ->
  h 'components',[
    h 'button', { 'ev-click': increment }, 'increment'
    h 'p', "#{n}"
  ]

html = count.map(render)
rootElement = document.getElementById('app')
attach(html).to(rootElement)

Bacon = require 'baconjs'
{h} = require 'virtual-dom'
attach = require 'bacon-dom'
write = require './model/write'
stream = require './model/stream'

DomDelegator = require 'dom-delegator'
domDelegator = new DomDelegator()

todos = stream("/api/todos")
  .map(JSON.parse)
  .toProperty([])
newTodoUpdates = new Bacon.Bus()
newTodo = newTodoUpdates.scan('', (current, update) -> update)

updateNewTodo = (value) -> newTodoUpdates.push(value)

model = Bacon.combineTemplate({todos, newTodo})

add = (todo) ->
  write('/api/add', todo)
  updateNewTodo('')

deleteTodo = (id) -> write('/api/delete/' + id)
complete = (id) -> write('/api/complete/' + id)

textField = (value, onChange) ->
  h 'input.ete-set-todo',
    type: 'text'
    value: value
    'ev-input': onChange

status = (todo) -> if todo.complete then 'complete' else 'incomplete'

renderTodos = (todos) ->
  h '.todos.ete-todo-list', todos.map (todo, index) ->
    h ".ete-todo-#{index}.ete-#{status(todo)}", [
      h 'span.ete-todo-content', "#{todo.contents} (#{status(todo)})"
      h 'button.ete-delete', { 'ev-click': -> deleteTodo(todo.id) }, 'X'
      if todo.complete
        ''
      else
        h 'button.ete-complete', { 'ev-click': -> complete(todo.id) }, 'complete'
    ]


render = (model) ->
  h 'components',[
    renderTodos(model.todos)
    textField(model.newTodo, (event) -> updateNewTodo(event.target.value))
    h 'button.ete-add-todo', { 'ev-click': -> add(model.newTodo) }, 'add'
  ]

html = model.map(render)
rootElement = document.getElementById('app')
attach(html).to(rootElement)

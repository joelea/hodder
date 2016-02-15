module.exports =
  'create a todo' : (browser) ->
    browser
      .url('http://entry')
      .waitForElementVisible('.ete-set-todo')
      .setValue('.ete-set-todo', 'a todo')
      .waitForElementVisible('.ete-add-todo')
      .click('.ete-add-todo')

    browser.expect.element('.ete-todo-0 .ete-todo-content').text
      .to.contain('a todo')
      .before(5000)

  'complete a todo' : (browser) ->
    browser
      .click('.ete-todo-0 .ete-complete')

    browser.expect.element('.ete-todo-0 .ete-complete')
      .to.be.visible
      .before(5000)

  'delete a todo' : (browser) ->
    browser
      .click('.ete-todo-0 .ete-delete')
      .waitForElementNotPresent('.ete-todo-0 .ete-todo-content')


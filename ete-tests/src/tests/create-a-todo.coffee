module.exports =
  'create-a-todo' : (browser) ->
    browser
      .url('http://entry')
      .waitForElementVisible('.ete-set-todo')
      .setValue('.ete-set-todo', 'a todo')
      .waitForElementVisible('.ete-add-todo')
      .click('.ete-add-todo')

    browser.expect.element('.ete-todo-list > .ete-todo-content').text.to.equal('a todo')

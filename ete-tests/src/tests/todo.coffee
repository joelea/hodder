module.exports =
  'create a todo' : (browser) ->
    browser
      .url('http://entry')
      .waitForElementVisible('.ete-set-todo')
      .setValue('.ete-set-todo', 'a todo')
      .waitForElementVisible('.ete-add-todo')
      .click('.ete-add-todo')

    browser.expect.element('.ete-todo-0 .ete-todo-content').text
      .to.equal('a todo')
      .before(5000)

  'delete a todo' : (browser) ->
    browser
      .click('.ete-todo-1 .ete-delete')

    browser.expect.element('.ete-todo-0 .ete-todo-content').text
      .to.not.be.present.before(5000)



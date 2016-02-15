package hodder;

import hodder.events.TodoAdded;
import spark.Request;

import static spark.Spark.get;
import static spark.Spark.post;

public class Application {
    private static final String TOPIC = "hodder.todos";

    public static void main(String[] args) throws InterruptedException {
        Todos todos = Todos.createDefault();
        Events events = new Events(TOPIC);

        get("/todos", (request, response) -> todos.getAll());
        post("/add", (request, response) -> addTodo(events, request));
        post("/remove/:id", (request, response) -> removeTodo(events, request.params(":id")));

        System.out.println("Hodder is flowing strong!");
    }

    private static boolean removeTodo(Events events, String id) {
        System.out.println("remove " + id);
        return true;
    }

    private static boolean addTodo(Events events, Request request) {
        Event message = new TodoAdded(request.body());
        System.out.println(message);
        events.write(message);
        return true;
    }

}

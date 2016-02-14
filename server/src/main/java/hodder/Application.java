package hodder;

import spark.Request;

import static spark.Spark.get;
import static spark.Spark.post;

public class Application {
    private static final String TOPIC = "example-topic";

    public static void main(String[] args) throws InterruptedException {
        Todos todos = Todos.createDefault();
        Events events = new Events(TOPIC);

        get("/todos", (request, response) -> todos.getAll());
        post("/add", (request, response) -> addTodo(events, request));

        System.out.println("Hodder is flowing strong!");
    }

    private static boolean addTodo(Events events, Request request) {
        events.write(request.body());
        return true;
    }

}

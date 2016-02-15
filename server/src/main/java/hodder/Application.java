package hodder;

import com.fasterxml.jackson.databind.ObjectMapper;
import hodder.events.TodoAdded;
import hodder.events.TodoDeleted;
import spark.Request;

import static java.lang.Long.*;
import static spark.Spark.get;
import static spark.Spark.post;

public class Application {
    private static final String TOPIC = "hodder.todos";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static void main(String[] args) throws InterruptedException {
        Todos todos = Todos.createDefault();
        Events events = new Events(TOPIC);

        get("/todos", "application/json",
                (request, response) -> todos.getAll(),
                OBJECT_MAPPER::writeValueAsString);

        post("/add", (request, response) -> addTodo(events, request));
        post("/delete/:id", (request, response) -> removeTodo(events, request.params(":id")));
        post("/complete/:id", (request, response) -> complete(events, request.params(":id")));

        System.out.println("Hodder is flowing strong!");
    }

    private static boolean complete(Events events, String id) {
        events.write(new TodoCompleted(parseLong(id)));
        return true;
    }

    private static boolean removeTodo(Events events, String id) {
        System.out.println("remove " + id);
        events.write(new TodoDeleted(parseLong(id)));
        return true;
    }

    private static boolean addTodo(Events events, Request request) {
        Event message = new TodoAdded(request.body());
        System.out.println(message);
        events.write(message);
        return true;
    }

}

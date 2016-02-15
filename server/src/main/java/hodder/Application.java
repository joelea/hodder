package hodder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hodder.events.TodoAdded;
import hodder.events.TodoDeleted;
import org.eclipse.jetty.websocket.api.Session;
import spark.Request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import static java.lang.Long.parseLong;
import static java.util.function.Predicate.*;
import static spark.Spark.init;
import static spark.Spark.post;
import static spark.Spark.webSocket;

public class Application {
    private static final String TOPIC = "hodder.todos";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static final List<Session> sessions = new ArrayList<>();
    public static Todos todos = Todos.createDefault();
    public static List<Todo> lastTodos = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        Events events = new Events(TOPIC);

        webSocket("/todos", TodoWebsocketHandler.class);

        post("/add", (request, response) -> addTodo(events, request));
        post("/delete/:id", (request, response) -> removeTodo(events, request.params(":id")));
        post("/complete/:id", (request, response) -> complete(events, request.params(":id")));

        Executors.newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(Application::checkDb, 0, 10, TimeUnit.MILLISECONDS);

        init();

        System.out.println("Hodder is flowing strong!");
    }

    private static void checkDb() {
        System.out.println("checking db!");
        List<Todo> newTodos = todos.getAll();
        System.out.println(newTodos);
        if (!newTodos.equals(lastTodos)) {
            broadcast(newTodos, (session) -> true);
        }
        lastTodos = newTodos;
    }

    private static void broadcast(List<Todo> newTodos, Predicate<Session> sessionPredicate) {
        try {
            final String message = OBJECT_MAPPER.writeValueAsString(newTodos);
            sessions.stream().filter(sessionPredicate).forEach(session -> {
                try {
                    session.getRemote().sendString(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
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

    public static void update(Session session) {
        broadcast(lastTodos, isEqual(session));
    }
}

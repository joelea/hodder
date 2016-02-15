package hodder;

import com.google.common.collect.ImmutableMap;
import hodder.events.TodoAdded;
import kafka.Kafka;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Map;

import static hodder.Todos.*;

public class TodoConsumer {
    private static final String TOPIC = "hodder.todos";
    private static final Todos todos = createDefault();
    private static final Map<Class, EventHandler> handlers = ImmutableMap.of(
            TodoAdded.class, (todoAdded) -> handleTodoAdded(todoAdded)
    );

    private static void handleTodoAdded(TodoAdded todoAdded) {
        todos.addTodo(todoAdded.todoContents());
    }

    public static void main(String[] args) throws InterruptedException {
        Kafka kafka = new Kafka();

        todos.createTable();

        kafka.eventsIn(TOPIC)
                .map(ConsumerRecord::value)
                .subscribe( msg -> {
                    handleEvent(todos, msg);
                });

        System.out.println("Hodder is flowing strong!");
    }

    private static void handleEvent(Todos todos, Event event) {
        TodoAdded todoAdded = (TodoAdded) event;
        todos.addTodo(todoAdded.todoContents());
    }

}

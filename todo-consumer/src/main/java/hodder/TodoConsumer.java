package hodder;

import hodder.events.TodoAdded;
import hodder.events.TodoDeleted;
import kafka.Kafka;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import static hodder.Todos.createDefault;

public class TodoConsumer {
    private static final String TOPIC = "hodder.todos";
    private static final Todos todos = createDefault();
    private static final EventHandler handler = DelegatingEventHandler.create()
        .delegate(TodoAdded.class, TodoConsumer::handleTodoAdded)
        .delegate(TodoDeleted.class, TodoConsumer::handleTodoRemoved);

    private static void handleTodoRemoved(TodoDeleted todoDeleted) {
        todos.removeTodo(todoDeleted.id());
    }

    private static void handleTodoAdded(TodoAdded todoAdded) {
        todos.addTodo(todoAdded.todoContents());
    }

    public static void main(String[] args) throws InterruptedException {
        Kafka kafka = new Kafka();

        todos.createTable();

        kafka.eventsIn(TOPIC)
                .map(ConsumerRecord::value)
                .subscribe(handler::handle);

        System.out.println("Hodder is flowing strong!");
    }

}

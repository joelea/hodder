package hodder;

import kafka.Kafka;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class TodoConsumer {
    private static final String TOPIC = "example-topic";

    public static void main(String[] args) throws InterruptedException {
        Kafka kafka = new Kafka();
        Todos todos = Todos.createDefault();

        todos.createTable();

        kafka.eventsIn(TOPIC)
                .map(ConsumerRecord::value)
                .subscribe( msg -> {
                    todos.addTodo(msg);
                });

        System.out.println("Hodder is flowing strong!");
    }

}

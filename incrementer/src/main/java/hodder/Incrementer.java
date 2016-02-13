package hodder;

import kafka.Kafka;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class Incrementer {
    private static final String TOPIC = "example-topic";

    public static void main(String[] args) throws InterruptedException {
        Kafka kafka = new Kafka();
        Counts counts = Counts.createDefault();

        counts.createTable();
        counts.addNewCounter();

        kafka.eventsIn(TOPIC)
                .map(ConsumerRecord::value)
                .subscribe( msg -> {
                    counts.increment(1);
                    System.out.println("R");
                });

        System.out.println("Hodder is flowing strong!");
    }

}

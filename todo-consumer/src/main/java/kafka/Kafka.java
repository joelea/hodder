package kafka;

import hodder.Event;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import rx.Observable;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.StreamSupport;

import static java.util.Arrays.asList;

public class Kafka {

    private final Properties properties = propertiesFromFile();
    private final Executor executor = Executors.newCachedThreadPool();

    private static Properties propertiesFromFile() {
        try {
            Properties props = new Properties();
            props.load(Kafka.class.getResourceAsStream("/todo-consumer.properties"));
            return props;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Observable<ConsumerRecord<String, Event>> eventsIn(String topic) {
        KafkaConsumer<String, Event> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(asList(topic));
        return Observable.create((subscriber) -> {
            executor.execute(() -> {
                while(true) {
                    ConsumerRecords<String, Event> results = consumer.poll(100);
                    StreamSupport.stream(results.spliterator(), false)
                        .forEachOrdered(subscriber::onNext);
                }
            });
        });
    }
}

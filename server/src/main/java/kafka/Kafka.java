package kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import rx.Observable;

import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.StreamSupport;

import static java.util.Arrays.asList;

public class Kafka {

    private final Properties properties;
    private final Executor executor = Executors.newCachedThreadPool();

    public Kafka() {
        properties = new Properties();
        properties.put("bootstrap.servers", "kafka:9092");
        properties.put("group.id", "test");
        properties.put("enable.auto.commit", "false");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("session.timeout.ms", "30000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    }

    public Observable<ConsumerRecord<String, String>> eventsIn(String topic) {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(asList(topic));
        return Observable.create((subscriber) -> {
            executor.execute(() -> {
                while(true) {
                    ConsumerRecords<String, String> results = consumer.poll(100);
                    StreamSupport.stream(results.spliterator(), false)
                        .forEachOrdered(subscriber::onNext);
                }
            });
        });
    }
}

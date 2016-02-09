package hodder;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class Events {

    private final Producer<String, String> producer;
    private final String topic;

    public Events(String topic) {
        this.topic = topic;
        Properties props = new Properties();
        props.put("bootstrap.servers", "kafka:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        producer = new KafkaProducer(props);
        producer.send(new ProducerRecord<>("example-topic", Integer.toString(1), Integer.toString(1)));
    }

    public boolean write(Increment increment) {
        producer.send(new ProducerRecord<String, String>(topic, "increment"));
        return true;
    }
}

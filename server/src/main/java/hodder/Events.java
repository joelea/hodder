package hodder;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.util.Properties;

public class Events {

    private final Producer<String, Event> producer;
    private final String topic;

    public Events(String topic) {
        this.topic = topic;
        Properties props = propertiesFromFile();
        producer = new KafkaProducer(props);
    }

    private Properties propertiesFromFile() {
        try {
            Properties props = new Properties();
            props.load(this.getClass().getResourceAsStream("/kafka-producer.properties"));
            return props;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean write(Event message) {
        ProducerRecord<String, Event> record = new ProducerRecord<>(topic, message);

        producer.send(record);

        return true;
    }
}

package hodder;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.postgresql.jdbc3.Jdbc3PoolingDataSource;
import org.skife.jdbi.v2.DBI;

import java.util.Properties;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;

import static java.util.Arrays.asList;
import static spark.Spark.get;
import static spark.Spark.post;

public class Application {
    public static void main(String[] args) {
        Counts counts = createCounts();
        Events events = new Events();

        counts.createTable();
        counts.addNewCounter();

        get("/count", (request, response) -> counts.get(1) );
        post("/increment", (request, response) -> events.write(new Increment()) );

        onConsume((message) -> {
            System.out.println("INCREMENT");
            counts.increment(1);
        });

        System.out.println("Hodder is flowing strong!");
    }

    private static void onConsume(Consumer<String> eventConsumer) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "kafka:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(asList("example-topic"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            StreamSupport.stream(records.spliterator(), false)
                    .map(ConsumerRecord::value)
                    .forEach(eventConsumer);
        }
    }

    private static Counts createCounts() {
        DBI dbi = new DBI(createDataSource());
        return dbi.open(Counts.class);
    }

    private static Jdbc3PoolingDataSource createDataSource() {
        Jdbc3PoolingDataSource source = new Jdbc3PoolingDataSource();

        source.setDataSourceName("A Data Source");
        source.setServerName("db");
        source.setDatabaseName("postgres");
        source.setUser("postgres");
        source.setPassword("mysecretpassword");
        source.setMaxConnections(10);

        return source;
    }
}

package hodder;

import kafka.Kafka;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.postgresql.jdbc3.Jdbc3PoolingDataSource;
import org.skife.jdbi.v2.DBI;

import java.util.function.Supplier;

import static spark.Spark.get;
import static spark.Spark.post;

public class Incrementer {
    private static final String TOPIC = "example-topic";

    public static void main(String[] args) throws InterruptedException {
        Counts counts = createCounts();
        Events events = new Events(TOPIC);
        Kafka kafka = new Kafka();

        counts.createTable();
        counts.addNewCounter();

        get("/count", (request, response) -> counts.get(1) );
        post("/increment", (request, response) -> events.write(new Increment()) );

        kafka.eventsIn(TOPIC)
                .map(ConsumerRecord::value)
                .subscribe( msg -> {
                    counts.increment(1);
                    System.out.println("R");
                });

        System.out.println("Hodder is flowing strong!");
    }

    private static Counts createCounts() throws InterruptedException {
        DBI dbi = new DBI(createDataSource());
        return withRetry( () -> dbi.open(Counts.class));
    }

    private static <T> T withRetry(Supplier<T> supplier) throws InterruptedException {
        for(int i = 0; i < 60; i++) {
            try {
                return supplier.get();
            } catch(Exception e) {
                System.out.println("Failed to obtain connection to database. Retrying.");
                Thread.sleep(1000);
            }
        }
        throw new IllegalStateException("Failed to connect to the database after 60 attempts");
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

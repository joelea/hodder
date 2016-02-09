package hodder;

import org.postgresql.jdbc3.Jdbc3PoolingDataSource;
import org.skife.jdbi.v2.DBI;

import static spark.Spark.get;
import static spark.Spark.post;

public class Application {
    public static void main(String[] args) {
        Counts counts = createCounts();

        counts.createTable();
        counts.addNewCounter();

        get("/count", (request, response) -> counts.get(1) );
        post("/increment", (request, response) -> counts.increment(1) );

        System.out.println("Hodder is flowing strong!");
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

package hodder.db;

import org.skife.jdbi.v2.DBI;

import javax.sql.DataSource;
import java.util.function.Supplier;

public class DataSourceFactory {

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

    public static DataSource create() {
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

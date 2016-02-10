package hodder.db;

import java.util.function.Supplier;

public class Retrying {
    public static <T> T withRetry(Supplier<T> supplier) throws InterruptedException {
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
}

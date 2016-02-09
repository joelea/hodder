package hodder;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.Transaction;

public abstract class Counts {

    @SqlQuery("SELECT count FROM counts WHERE id = :id")
    public abstract int get(@Bind("id") int i);

    @SqlUpdate("UPDATE counts SET count = :value WHERE id = :counter")
    public abstract void setCount(@Bind("counter") int counter, @Bind("value") int value);

    @Transaction
    public int increment(int counter) {
        int currentCount = get(counter);
        int newCount = currentCount + 1;
        setCount(counter, newCount);
        return get(counter);
    }

    @SqlUpdate("CREATE TABLE IF NOT EXISTS counts (id SERIAL PRIMARY KEY, count integer DEFAULT 0)")
    public abstract void createTable();

    @SqlUpdate("INSERT INTO counts (count) VALUES (0)")
    public abstract void addNewCounter();

}

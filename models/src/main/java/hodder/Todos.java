package hodder;

import hodder.db.DataSourceFactory;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

import java.util.List;

import static hodder.db.Retrying.withRetry;

public abstract class Todos {

    public static Todos createDefault() {
        DBI dbi = new DBI(DataSourceFactory.create());
        try {
            return withRetry( () -> dbi.open(Todos.class));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @SqlQuery("SELECT todo FROM todos")
    public abstract List<String> getAll();

    @SqlUpdate("CREATE TABLE IF NOT EXISTS todos (id SERIAL PRIMARY KEY, todo text)")
    public abstract void createTable();

    @SqlUpdate("INSERT INTO todos (todo) VALUES (:todo)")
    public abstract void addTodo(@Bind("todo") String todo);

}

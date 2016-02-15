package hodder;

import hodder.db.DataSourceFactory;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

import static hodder.db.Retrying.withRetry;

@RegisterMapper(TodoMapper.class)
public abstract class Todos {

    public static Todos createDefault() {
        DBI dbi = new DBI(DataSourceFactory.create());
        try {
            return withRetry( () -> dbi.open(Todos.class));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @SqlQuery("SELECT * FROM todos")
    public abstract List<Todo> getAll();

    @SqlUpdate("CREATE TABLE IF NOT EXISTS todos (id SERIAL PRIMARY KEY, todo text, complete boolean DEFAULT false)")
    public abstract void createTable();

    @SqlUpdate("INSERT INTO todos (todo) VALUES (:todo)")
    public abstract void addTodo(@Bind("todo") String todo);

    @SqlUpdate("DELETE FROM todos WHERE id = :id")
    public abstract void removeTodo(@Bind("id") long id);

    @SqlUpdate("UPDATE todos SET complete = true WHERE id = :id")
    public abstract void complete(@Bind("id") long id);
}

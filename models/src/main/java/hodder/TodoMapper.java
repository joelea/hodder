package hodder;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TodoMapper implements ResultSetMapper<Todo> {
    @Override
    public Todo map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Todo(
                r.getLong("id"),
                r.getString("todo"),
                r.getBoolean("complete")
        );
    }
}

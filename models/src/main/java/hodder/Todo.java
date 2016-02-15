package hodder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Todo {
    private final long id;
    private final String contents;
    private final boolean complete;

    public Todo(long id, String contents, boolean complete) {
        this.id = id;
        this.contents = contents;
        this.complete = complete;
    }

    @JsonProperty("id")
    public long getId() {
        return id;
    }

    @JsonProperty("contents")
    public String getContents() {
        return contents;
    }

    @JsonProperty("complete")
    public boolean isComplete() {
        return complete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Todo todo = (Todo) o;

        if (id != todo.id) return false;
        if (complete != todo.complete) return false;
        return contents.equals(todo.contents);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + contents.hashCode();
        result = 31 * result + (complete ? 1 : 0);
        return result;
    }
}

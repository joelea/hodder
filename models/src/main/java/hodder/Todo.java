package hodder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Todo {
    private final long id;
    private final String contents;

    public Todo(long id, String contents) {
        this.id = id;
        this.contents = contents;
    }

    @JsonProperty("id")
    public long getId() {
        return id;
    }

    @JsonProperty("contents")
    public String getContents() {
        return contents;
    }
}

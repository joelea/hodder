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
}

package hodder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TodoCompleted implements Event {
    private final long id;

    public TodoCompleted(@JsonProperty("id") long id) {
        this.id = id;
    }

    @JsonProperty("id")
    public long id() {
        return id;
    }
}

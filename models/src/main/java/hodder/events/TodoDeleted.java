package hodder.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import hodder.Event;

public class TodoDeleted implements Event {
    private final long id;

    public TodoDeleted(@JsonProperty("id") long id) {
        this.id = id;
    }

    @JsonProperty("id")
    public long id() {
        return id;
    }
}

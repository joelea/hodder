package hodder.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import hodder.Event;

public class TodoAdded implements Event {
    private final String contents;

    public TodoAdded(@JsonProperty("contents") String contents) {
        this.contents = contents;
    }

    @JsonProperty("contents")
    public String todoContents() {
        return contents;
    }
}

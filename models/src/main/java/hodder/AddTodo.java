package hodder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddTodo implements Message {
    private final String contents;

    public AddTodo(@JsonProperty("contents") String contents) {
        this.contents = contents;
    }

    @JsonProperty("contents")
    public String todoContents() {
        return contents;
    }
}

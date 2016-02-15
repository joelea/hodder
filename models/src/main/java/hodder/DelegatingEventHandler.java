package hodder;

import java.util.HashMap;
import java.util.Map;

public class DelegatingEventHandler {
    Map<Class, EventHandler<Event>> handlers = new HashMap<>();

    public static DelegatingEventHandler create() {
        return new DelegatingEventHandler();
    }

    public <T extends Event> DelegatingEventHandler delegate(
            Class<T> type,
            EventHandler<T> handler) {
        handlers.put(type, castAndThen(type, handler));
        return this;
    }

    private <T extends Event> EventHandler<Event> castAndThen(Class<T> type, EventHandler<T> handler) {
        return (event) -> {
            T typedEvent = type.cast(event);
            handler.handle(typedEvent);
        };
    }

    public void handle(Event event) {
        handlers.get(event.getClass()).handle(event);
    }
}

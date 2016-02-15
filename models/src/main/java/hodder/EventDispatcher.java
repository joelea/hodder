package hodder;

import java.util.HashMap;
import java.util.Map;

public class EventDispatcher {
    Map<Class, EventHandler<Event>> handlers = new HashMap<>();

    public static EventDispatcher create() {
        return new EventDispatcher();
    }

    public <T extends Event> EventDispatcher dispatch(Class<T> type, EventHandler<T> handler) {
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

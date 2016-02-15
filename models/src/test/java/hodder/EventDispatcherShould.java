package hodder;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class EventDispatcherShould {
    private Event received;

    @Test public void
    dispatch_an_event_cast_to_the_correct_type() {
        EventDispatcher dispatcher = EventDispatcher.create()
                .dispatch(EventTypeA.class, (event) -> received = event);

        dispatcher.handle(new EventTypeA());

        assertThat(received instanceof EventTypeA, is(true));
    }
}

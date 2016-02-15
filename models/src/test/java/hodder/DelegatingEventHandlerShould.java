package hodder;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DelegatingEventHandlerShould {
    private Event received;

    @Test public void
    dispatch_an_event_cast_to_the_correct_type() {
        DelegatingEventHandler dispatcher = DelegatingEventHandler.create()
                .delegate(EventTypeA.class, (event) -> received = event);

        dispatcher.handle(new EventTypeA());

        assertThat(received instanceof EventTypeA, is(true));
    }

    @Test public void
    dispatch_an_event_cast_to_the_correct_type_when_there_are_multiple_handlers() {
        DelegatingEventHandler dispatcher = DelegatingEventHandler.create()
                .delegate(EventTypeA.class, (event) -> received = event)
                .delegate(EventTypeB.class, (event) -> received = event);

        dispatcher.handle(new EventTypeB());

        assertThat(received instanceof EventTypeB, is(true));
    }
}

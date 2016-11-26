package pl.themolka.commons.session;

import pl.themolka.commons.event.Event;

public class SessionEvent extends Event {
    private final Session session;

    public SessionEvent(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return this.session;
    }
}

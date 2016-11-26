package pl.themolka.commons.session;

public class SessionCreateEvent extends SessionEvent {
    public SessionCreateEvent(Session session) {
        super(session);
    }
}

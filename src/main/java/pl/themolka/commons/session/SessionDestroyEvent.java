package pl.themolka.commons.session;

public class SessionDestroyEvent extends SessionEvent {
    public SessionDestroyEvent(Session session) {
        super(session);
    }
}

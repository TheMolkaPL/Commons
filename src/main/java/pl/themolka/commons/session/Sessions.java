package pl.themolka.commons.session;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Sessions<T> {
    private final Map<UUID, Session> sessionMap = new HashMap<>();

    public Session findSession(String query) {
        if (query.length() == 36) {
            try {
                return this.getSession(UUID.fromString(query));
            } catch (IllegalArgumentException ex) {
                return null;
            }
        } else if (query.length() <= 16) {
            return this.getSession(query);
        }

        return null;
    }

    public Session getSession(String username) {
        for (Session session : this.sessionMap.values()) {
            if (session.getUsername().equalsIgnoreCase(username)) {
                return session;
            }
        }
        return null;
    }

    public Session getSession(T representer) {
        for (Session session : this.sessionMap.values()) {
            if (session.getRepresenter().equals(representer)) {
                return session;
            }
        }
        return null;
    }

    public Session getSession(UUID uuid) {
        return this.sessionMap.get(uuid);
    }

    public Collection<Session> getSessions() {
        return this.sessionMap.values();
    }

    public Set<UUID> getSessionUuids() {
        return this.sessionMap.keySet();
    }

    // storage management
    protected boolean insertSession(Session session) {
        if (!this.sessionMap.containsKey(session.getUuid())) {
            SessionCreateEvent event = new SessionCreateEvent(session);
            event.post();

            this.sessionMap.put(session.getUuid(), session);
            return true;
        }

        return false;
    }

    protected void removeSession(Session session) {
        SessionDestroyEvent event = new SessionDestroyEvent(session);
        event.post();

        this.sessionMap.remove(session.getUuid());
    }
}

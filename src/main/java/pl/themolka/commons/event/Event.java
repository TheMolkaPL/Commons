package pl.themolka.commons.event;

public abstract class Event {
    private static Events eventPoster;

    private boolean posting;

    public String getEventName() {
        return this.getClass().getName();
    }

    public boolean post() {
        if (!this.posting) {
            this.posting = true;

            if (isAutoEventPosterSet()) {
                getAutoEventPoster().post(this);
            }

            if (this instanceof Cancelable) {
                return ((Cancelable) this).isCanceled();
            }
        }

        return false;
    }

    public static Events getAutoEventPoster() {
        return eventPoster;
    }

    public static boolean isAutoEventPosterSet() {
        return getAutoEventPoster() != null;
    }

    public static void setAutoEventPoster(Events events) {
        eventPoster = events;
    }
}

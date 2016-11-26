package pl.themolka.commons.event;

public interface Cancelable {
    boolean isCanceled();

    void setCanceled(boolean cancel);
}

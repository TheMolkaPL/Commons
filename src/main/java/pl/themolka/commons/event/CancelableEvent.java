package pl.themolka.commons.event;

public class CancelableEvent extends Event implements Cancelable {
    private boolean cancel;

    @Override
    public boolean isCanceled() {
        return this.cancel;
    }

    @Override
    public void setCanceled(boolean cancel) {
        this.cancel = cancel;
    }
}

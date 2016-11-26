package pl.themolka.commons.command;

import pl.themolka.commons.event.Cancelable;

public class CommandRegisterEvent extends CommandEvent implements Cancelable {
    private boolean cancel;

    public CommandRegisterEvent(Command command) {
        super(command);
    }

    @Override
    public boolean isCanceled() {
        return this.cancel;
    }

    @Override
    public void setCanceled(boolean cancel) {
        this.cancel = cancel;
    }
}

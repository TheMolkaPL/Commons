package pl.themolka.commons.command;

import pl.themolka.commons.event.Cancelable;
import pl.themolka.commons.session.Session;

public class CommandHandleEvent extends CommandEvent implements Cancelable {
    private boolean cancel;
    private final CommandContext context;
    private final Session sender;

    public CommandHandleEvent(CommandContext context, Session sender) {
        super (context.getCommand());

        this.context = context;
        this.sender = sender;
    }

    @Override
    public boolean isCanceled() {
        return this.cancel;
    }

    @Override
    public void setCanceled(boolean cancel) {
        this.cancel = cancel;
    }

    public CommandContext getContext() {
        return this.context;
    }

    public String getLabel() {
        return this.getLabel();
    }

    public Session getSender() {
        return this.sender;
    }
}

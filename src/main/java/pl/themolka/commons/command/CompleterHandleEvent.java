package pl.themolka.commons.command;

import pl.themolka.commons.session.Session;

public class CompleterHandleEvent extends CommandHandleEvent {
    public CompleterHandleEvent(CommandContext context, Session sender) {
        super(context, sender);
    }
}

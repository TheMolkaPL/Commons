package pl.themolka.commons;

import pl.themolka.commons.command.Commands;
import pl.themolka.commons.event.Events;
import pl.themolka.commons.session.Sessions;
import pl.themolka.commons.storage.Storages;

public interface Commons {
    Commands getCommands();

    Events getEvents();

    Sessions getSessions();

    Storages getStorages();
}

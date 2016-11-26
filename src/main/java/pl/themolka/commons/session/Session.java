package pl.themolka.commons.session;

import java.util.UUID;

public interface Session<T> {
    T getRepresenter();

    int getSessionId();

    String getUsername();

    UUID getUuid();

    boolean hasPermission(String permission);

    boolean isConsole();

    void send(String message);

    void sendError(String error);

    void sendInfo(String info);

    void sendSuccess(String success);
}

package pl.themolka.commons.command;

import pl.themolka.commons.session.Session;

import java.util.UUID;

public class ConsoleSender implements Session<Runtime> {
    public static final String NAME = "*Console";

    private final Runtime runtime = Runtime.getRuntime();

    @Override
    public Runtime getRepresenter() {
        return this.runtime;
    }

    @Override
    public int getSessionId() {
        return 0;
    }

    @Override
    public String getUsername() {
        return NAME;
    }

    @Override
    public UUID getUuid() {
        return null;
    }

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }

    @Override
    public boolean isConsole() {
        return true;
    }

    @Override
    public void send(String message) {
        System.out.println(message);
    }

    @Override
    public void sendError(String error) {
        this.send("[Error] " + error);
    }

    @Override
    public void sendInfo(String info) {
        this.send("[Info] " + info);
    }

    @Override
    public void sendSuccess(String success) {
        this.send("[Success]" + success);
    }
}

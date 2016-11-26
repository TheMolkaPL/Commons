package pl.themolka.commons;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.themolka.commons.command.BukkitCommands;
import pl.themolka.commons.command.Commands;
import pl.themolka.commons.event.Events;
import pl.themolka.commons.session.BukkitSessions;
import pl.themolka.commons.session.Sessions;
import pl.themolka.commons.storage.Storages;

public class BukkitCommons implements Commons {
    private final Plugin plugin;

    private final Commands commands;
    private final Events events;
    private final Sessions<Player> sessions;
    private final Storages storages;

    public BukkitCommons(Plugin plugin) {
        this.plugin = plugin;

        this.commands = new BukkitCommands(plugin);
        this.events = new Events();
        this.sessions = new BukkitSessions(plugin);
        this.storages = new Storages();
    }

    @Override
    public Commands getCommands() {
        return this.commands;
    }

    @Override
    public Events getEvents() {
        return this.events;
    }

    @Override
    public Sessions getSessions() {
        return this.sessions;
    }

    @Override
    public Storages getStorages() {
        return this.storages;
    }

    public Plugin getPlugin() {
        return this.plugin;
    }
}

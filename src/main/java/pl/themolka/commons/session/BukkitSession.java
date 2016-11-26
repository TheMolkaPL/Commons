package pl.themolka.commons.session;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BukkitSession implements Session<Player> {
    private final Player bukkit;
    private int sessionId;

    public BukkitSession(Player bukkit) {
        this.bukkit = bukkit;
    }

    @Override
    public Player getRepresenter() {
        return this.bukkit;
    }

    @Override
    public int getSessionId() {
        return this.sessionId;
    }

    @Override
    public String getUsername() {
        return this.bukkit.getName();
    }

    @Override
    public UUID getUuid() {
        return this.bukkit.getUniqueId();
    }

    @Override
    public boolean hasPermission(String permission) {
        return this.bukkit.hasPermission(permission);
    }

    @Override
    public boolean isConsole() {
        return false;
    }

    @Override
    public void send(String message) {
        this.bukkit.sendMessage(message);
    }

    @Override
    public void sendError(String error) {
        this.send(ChatColor.RED + error);
    }

    @Override
    public void sendInfo(String info) {
        this.send(ChatColor.DARK_AQUA + info);
    }

    @Override
    public void sendSuccess(String success) {
        this.send(ChatColor.GREEN + success);
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }
}

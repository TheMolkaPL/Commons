package pl.themolka.commons.session;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class BukkitSessions extends Sessions<Player> {
    private final Plugin plugin;

    public BukkitSessions(Plugin plugin) {
        this.plugin = plugin;

        this.plugin.getServer().getPluginManager().registerEvents(new Listeners(), this.plugin);
    }

    private class Listeners implements Listener {
        @EventHandler(priority = EventPriority.MONITOR)
        public void onPlayerJoin(PlayerJoinEvent event) {
            BukkitSessions.this.insertSession(new BukkitSession(event.getPlayer()));
        }

        @EventHandler(priority = EventPriority.MONITOR)
        public void onPlayerQuit(PlayerQuitEvent event) {
            BukkitSessions.this.removeSession(BukkitSessions.this.getSession(event.getPlayer().getUniqueId()));
        }
    }
}

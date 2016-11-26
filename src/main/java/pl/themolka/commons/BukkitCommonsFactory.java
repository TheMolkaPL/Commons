package pl.themolka.commons;

import org.bukkit.plugin.Plugin;
import pl.themolka.commons.command.BukkitCommands;

public class BukkitCommonsFactory extends CommonsFactory {
    private BukkitCommons commons;
    private Plugin plugin;

    public BukkitCommonsFactory(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Commons build() {
        if (this.commons == null) {
            this.commons = new BukkitCommons(this.getPlugin());
        }

        this.loadCommands();

        return this.commons;
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    private void loadCommands() {
        BukkitCommands commands = (BukkitCommands) this.commons.getCommands();
        commands.setSessions(this.commons.getSessions());
    }
}

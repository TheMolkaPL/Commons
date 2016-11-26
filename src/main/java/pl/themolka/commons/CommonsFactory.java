package pl.themolka.commons;

import org.apache.commons.lang3.builder.Builder;
import org.bukkit.plugin.Plugin;

public abstract class CommonsFactory implements Builder<Commons> {
    private static BukkitCommonsFactory bukkit;

    public static BukkitCommonsFactory bukkitFactory(Plugin plugin) {
        if (bukkit == null) {
            bukkit = new BukkitCommonsFactory(plugin);
        }

        return bukkit;
    }
}

package pl.themolka.commons.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.help.GenericCommandHelpTopic;
import org.bukkit.help.HelpTopic;
import org.bukkit.help.HelpTopicComparator;
import org.bukkit.help.IndexHelpTopic;
import org.bukkit.plugin.Plugin;
import pl.themolka.commons.session.Session;
import pl.themolka.commons.session.Sessions;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class BukkitCommands extends Commands implements CommandExecutor, TabCompleter {
    private final Plugin plugin;

    private CommandMap bukkitCommandMap;
    private Set<HelpTopic> helpTopics = new TreeSet<>(HelpTopicComparator.helpTopicComparatorInstance());
    private Sessions sessions;

    public BukkitCommands(Plugin plugin) {
        this.plugin = plugin;

        try {
            this.bukkitCommandMap = this.getBukkitCommandMap();
        } catch (ReflectiveOperationException ex) {
            ex.printStackTrace();
        }

        this.plugin.getServer().getHelpMap().addTopic(this.createHelpIndex());
    }

    @Override
    public void registerCommand(Command command) {
        super.registerCommand(command);

        this.injectCommand(this.getPrefixName(), command);
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        this.handleCommand(this.findSession(sender), this.getCommand(label), label, args);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        return this.handleCompleter(this.findSession(sender), this.getCommand(label), label, args);
    }

    public Set<HelpTopic> getHelpTopics() {
        return this.helpTopics;
    }

    public Sessions getSessions() {
        return this.sessions;
    }

    public void setSessions(Sessions sessions) {
        this.sessions = sessions;
    }

    private org.bukkit.command.Command createBukkitCommand(Command command) {
        List<String> aliases = new ArrayList<>();
        for (int i = 1; i < command.getName().length; i++) {
            aliases.add(command.getName()[i]);
        }

        org.bukkit.command.Command performer = new CommandPerformer(command.getCommand());
        performer.setAliases(aliases);
        performer.setDescription(command.getDescription());
        performer.setUsage(command.getUsage());
        return performer;
    }

    private IndexHelpTopic createHelpIndex() {
        return new IndexHelpTopic(
                this.getPluginName(),
                "Wszystkie komendy " + this.getPluginName(),
                null,
                this.getHelpTopics()
        );
    }

    private Session findSession(CommandSender sender) {
        if (this.getSessions() != null && sender instanceof Player) {
            return this.getSessions().getSession(((Player) sender).getUniqueId()); // use UUID for not looping, but for using the Map
        }

        return this.getConsole();
    }

    private CommandMap getBukkitCommandMap() throws ReflectiveOperationException {
        Field field = this.plugin.getServer().getClass().getDeclaredField("commandMap");
        field.setAccessible(true);
        return (CommandMap) field.get(this.plugin.getServer());
    }

    private String getPluginName() {
        return this.plugin.getName();
    }

    private String getPrefixName() {
        return this.getPluginName().toLowerCase();
    }

    private void injectCommand(String prefix, Command command) {
        org.bukkit.command.Command performer = this.createBukkitCommand(command);

        this.bukkitCommandMap.register(prefix, performer);
        this.helpTopics.add(new GenericCommandHelpTopic(performer));
    }

    private class CommandPerformer extends org.bukkit.command.Command {
        protected CommandPerformer(String name) {
            super(name);
        }

        @Override
        public boolean execute(CommandSender sender, String label, String[] args) {
            return BukkitCommands.this.onCommand(sender, this, label, args);
        }

        @Override
        public List<String> tabComplete(CommandSender sender, String label, String[] args) throws IllegalArgumentException {
            return BukkitCommands.this.onTabComplete(sender, this, label, args);
        }
    }
}

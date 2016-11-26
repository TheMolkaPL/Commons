package pl.themolka.commons.command;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CommandContext {
    private final Command command;
    private final Map<String, String> flags;
    private final String label;
    private final List<String> params;

    public CommandContext(Command command, Map<String, String> flags, String label, List<String> params) {
        this.command = command;
        this.flags = flags;
        this.label = label;
        this.params = params;
    }

    public Command getCommand() {
        return this.command;
    }

    // Flags - Strings
    public String getFlag(String flag) {
        return this.getFlag(flag, null);
    }

    public String getFlag(String flag, String def) {
        if (this.flags.containsKey(flag)) {
            return this.flags.get(flag);
        } else {
            return def;
        }
    }

    // Flags = Booleans
    public boolean getFlagBoolean(String flag) {
        return this.getFlagBoolean(flag, false);
    }

    public boolean getFlagBoolean(String flag, boolean def) {
        return this.parseBoolean(this.getFlag(flag, String.valueOf(def)), def);
    }

    // Flags - Doubles
    public double getFlagDouble(String flag) {
        return this.getFlagDouble(flag, 0.0);
    }

    public double getFlagDouble(String flag, double def) {
        return Double.parseDouble(this.getFlag(flag, String.valueOf(def)));
    }

    // Flags - Integers
    public int getFlagInt(String flag) {
        return this.getFlagInt(flag, 0);
    }

    public int getFlagInt(String flag, int def) {
        return Integer.parseInt(this.getFlag(flag, String.valueOf(def)));
    }

    // Flags
    public Set<String> getFlags() {
        return this.flags.keySet();
    }

    // Label
    public String getLabel() {
        return this.label;
    }

    // Params - Strings
    public String getParam(int index) {
        return this.getParam(index, null);
    }

    public String getParam(int index, String def) {
        if (this.params.size() <= index) {
            return def;
        }

        return this.params.get(index);
    }

    public String getParams(int from) {
        return this.getParams(from, this.params.size() - 1);
    }

    public String getParams(int from, int to) {
        return StringUtils.join(this.params.subList(from, to), " ");
    }

    // Params = Booleans
    public boolean getParamBoolean(int index) {
        return this.getParamBoolean(index, false);
    }

    public boolean getParamBoolean(int index, boolean def) {
        return this.parseBoolean(this.getParam(index, String.valueOf(def)), def);
    }

    // Params - Doubles
    public double getParamDouble(int index) {
        return this.getParamDouble(index, 0.0);
    }

    public double getParamDouble(int index, double def) {
        return Double.parseDouble(this.getParam(index, String.valueOf(def)));
    }

    // Params - Integers
    public int getParamInt(int index) {
        return this.getParamInt(index, 0);
    }

    public int getParamInt(int index, int def) {
        return Integer.parseInt(this.getParam(index, String.valueOf(def)));
    }

    // Params
    public int getParamsLength() {
        return this.params.size();
    }

    // Flags
    public boolean hasFlag(String flag) {
        return this.flags.containsKey(flag);
    }

    public boolean hasFlagValue(String flag) {
        return this.getFlag(flag) != null;
    }

    protected Boolean parseBoolean(String bool, Boolean def) {
        if (bool.equals("true") || bool.equals("1") || bool.equals("yes") || bool.equals("on")) {
            return Boolean.TRUE;
        } else if (bool.equals("false") || bool.equals("0") || bool.equals("no") || bool.equals("off")) {
            return Boolean.FALSE;
        } else {
            return def;
        }
    }

    public static CommandContext parse(Command command, String label, String[] args) {
        return parse(command, label, args, null);
    }

    public static CommandContext parse(Command command, String label, String[] args, IContextParser parser) {
        if (parser == null) {
            parser = new CommandContextParser();
        }

        return parser.parse(command, label, args);
    }

    public interface IContextParser {
        CommandContext parse(Command command, String label, String[] args);
    }
}
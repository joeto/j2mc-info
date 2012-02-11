package to.joe.j2mc.info;

import java.util.Arrays;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import to.joe.j2mc.info.command.HelpCommand;
import to.joe.j2mc.info.command.IntroCommand;
import to.joe.j2mc.info.command.ReloadInfoCommand;
import to.joe.j2mc.info.command.RulesCommand;

public class J2MC_Info extends JavaPlugin {

    public List<String> rules;
    public List<String> HelpLines;
    public List<String> IntroLines;

    @Override
    public void onDisable() {
        this.getLogger().info("Info module disabled");
    }

    @Override
    public void onEnable() {
        this.getConfig().options().copyDefaults(true);
        this.readData();
        this.getCommand("help").setExecutor(new HelpCommand(this));
        this.getCommand("intro").setExecutor(new IntroCommand(this));
        this.getCommand("rules").setExecutor(new RulesCommand(this));
        this.getCommand("reloadinfo").setExecutor(new ReloadInfoCommand(this));
        this.getLogger().info("Info module enabled");
    }

    public void readData() {
        this.rules = Arrays.asList(this.getConfig().getString("rules").split("\n"));
        if (this.rules == null) {
            this.shutDownEverything();
        }
        this.HelpLines = Arrays.asList(this.getConfig().getString("help").split("\n"));
        if (this.HelpLines == null) {
            this.shutDownEverything();
        }
        this.IntroLines = Arrays.asList(this.getConfig().getString("intro").split("\n"));
        if (this.IntroLines == null) {
            this.shutDownEverything();
        }
    }

    public void shutDownEverything() {
        this.getLogger().severe("Config file is derp. I repeat, config is derp.");
        this.getServer().getPluginManager().disablePlugin(this);
    }

}

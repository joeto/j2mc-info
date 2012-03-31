package to.joe.j2mc.info;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import to.joe.j2mc.core.J2MC_Manager;
import to.joe.j2mc.info.command.ReloadInfoCommand;
import to.joe.j2mc.info.command.RulesCommand;
import to.joe.j2mc.info.command.WorldGuardHelpCommand;

public class J2MC_Info extends JavaPlugin {

    public List<String> rules;
    public List<String> worldguardLines;
    public List<String> repeatingBroadcasts;
    public List<String> motd;

    @Override
    public void onDisable() {
        this.getLogger().info("Info module disabled");
    }

    @Override
    public void onEnable() {
        this.getConfig().options().copyDefaults(true);
        this.readData();
        this.getCommand("rules").setExecutor(new RulesCommand(this));
        this.getCommand("reloadinfo").setExecutor(new ReloadInfoCommand(this));
        this.getCommand("worldguardhelp").setExecutor(new WorldGuardHelpCommand(this));

        this.getServer().getScheduler().scheduleAsyncDelayedTask(this, new Runnable() {
            int currentLine = 0;

            @Override
            public void run() {
                for (final Player plr : J2MC_Manager.getVisibility().getOnlinePlayers(null)) {
                    plr.sendMessage(J2MC_Info.this.repeatingBroadcasts.get(this.currentLine));
                    if (this.currentLine == (J2MC_Info.this.repeatingBroadcasts.size() - 1)) {
                        this.currentLine = 0;
                    } else {
                        this.currentLine++;
                    }
                }
            }
        }, 4800);

        this.getLogger().info("Info module enabled");
    }

    public void readData() {
        this.rules = Arrays.asList(this.getConfig().getString("rules").split("\n"));
        if (this.rules == null) {
            this.shutDownEverything();
        }
        this.repeatingBroadcasts = Arrays.asList(this.getConfig().getString("repeatmessages").split("\n"));
        this.worldguardLines = Arrays.asList(this.getConfig().getString("worldguardhelp").split("\n"));
        this.motd = Arrays.asList(this.getConfig().getString("motd").split("\n"));
    }

    public void shutDownEverything() {
        this.getLogger().severe("Config file is derp. I repeat, config is derp.");
        this.getServer().getPluginManager().disablePlugin(this);
    }

}

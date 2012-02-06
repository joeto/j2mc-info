package to.joe.j2mc.info;

import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import to.joe.j2mc.info.command.HelpCommand;
import to.joe.j2mc.info.command.IntroCommand;
import to.joe.j2mc.info.command.ReloadInfoCommand;
import to.joe.j2mc.info.command.RulesCommand;

public class J2MC_Info extends JavaPlugin{
	
	public List<String> rules;
	public List<String> HelpLines;
	public List<String> IntroLines;
	
	public void onEnable(){
		this.getConfig().options().copyDefaults(true);
		this.ReadData();
		this.getCommand("help").setExecutor(new HelpCommand(this));
		this.getCommand("intro").setExecutor(new IntroCommand(this));
		this.getCommand("rules").setExecutor(new RulesCommand(this));
		this.getCommand("reloadinfo").setExecutor(new ReloadInfoCommand(this));
		this.getLogger().info("Info module enabled");
	}
	
	public void onDisable(){
		this.getLogger().info("Info module disabled");
	}
	
	public void ReadData(){
		this.rules = this.getConfig().getStringList("rules");
		if(rules == null){
			this.ShutDownEverything();
		}
		this.HelpLines = this.getConfig().getStringList("help");
		if(HelpLines == null){
			this.ShutDownEverything();
		}
		this.IntroLines = this.getConfig().getStringList("intro");
		if(IntroLines == null){
			this.ShutDownEverything();
		}
	}
	
	public void ShutDownEverything(){
		this.getLogger().severe("Config file is derp, I repeat config is derp.");
		this.getServer().getPluginManager().disablePlugin(this);
	}
	
}

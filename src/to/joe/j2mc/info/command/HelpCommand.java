package to.joe.j2mc.info.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import to.joe.j2mc.core.command.MasterCommand;
import to.joe.j2mc.info.J2MC_Info;

public class HelpCommand extends MasterCommand{

	J2MC_Info plugin;
	public HelpCommand(J2MC_Info Info){
		super(Info);
		this.plugin = Info;
	}
	
    @Override
    public void exec(CommandSender sender, String commandName, String[] args, Player player, boolean isPlayer) {
    	for(String message : plugin.HelpLines){
    		sender.sendMessage(ChatColor.RED + message);
    	}
    }
	
}

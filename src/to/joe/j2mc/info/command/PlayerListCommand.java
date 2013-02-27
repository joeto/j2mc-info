package to.joe.j2mc.info.command;

import java.util.HashSet;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import to.joe.j2mc.core.J2MC_Manager;
import to.joe.j2mc.core.command.MasterCommand;
import to.joe.j2mc.info.J2MC_Info;

public class PlayerListCommand extends MasterCommand<J2MC_Info> {

    public PlayerListCommand(J2MC_Info Info) {
        super(Info);
    }

    @Override
    public void exec(CommandSender sender, String commandName, String[] args, Player player, boolean isPlayer) {
        final List<Player> online = J2MC_Manager.getVisibility().getOnlinePlayers(player);
        final boolean detail = isPlayer && player.hasPermission("j2mc.core.admin");
        final StringBuilder sb = new StringBuilder();

        sender.sendMessage(ChatColor.AQUA + "Players (" + online.size() + "/" + this.plugin.getServer().getMaxPlayers() + "):");

        if (online.isEmpty()) {
            sender.sendMessage(ChatColor.RED + "No one is online :(");
            return;
        }

        for (final Player user : online) {
            if (detail) {
                String name = user.getName();
                final HashSet<Character> flags = J2MC_Manager.getPermissions().getFlags(name);

                if (flags.isEmpty()) {
                    sb.append(ChatColor.GREEN + name + ", ");
                } else {
                    for (final Character flag : flags) {
                        if (J2MC_Manager.getVisibility().isVanished(user)) { // Vanished
                            name = ChatColor.AQUA + name;
                        } else if (flag == 's') { // Senior
                            name = ChatColor.DARK_RED + name;
                        } else if (flag == 'a') { // Admin
                            name = ChatColor.RED + name;
                        } else if (flag == 'd') { // Donator
                            name = ChatColor.GOLD + name;
                        } else if (flag == 't') { // Trusted
                            name = ChatColor.DARK_GREEN + name;
                        } else { // Normal
                            name = ChatColor.GREEN + name;
                        }

                        // Trusted
                        if ((flag == 't') && !name.contains(ChatColor.DARK_GREEN.toString())) {
                            name += ChatColor.DARK_GREEN + "[T]";
                        }

                        // Muted
                        if (flag == 'M') {
                            name += ChatColor.YELLOW + "[M]";
                        }

                        // NSA
                        if (flag == 'N') {
                            name += ChatColor.RED + "\u00ab\u00bb";
                        }

                        // Teleport Banned
                        if (flag == 'T') {
                            name += ChatColor.STRIKETHROUGH + "[TP]" + ChatColor.RESET;
                        }

                        // Harassed
                        if (flag == 'H') {
                            name += ChatColor.AQUA + "[H]";
                        }
                    }

                    name += ChatColor.WHITE + ", ";
                    sb.append(name);
                }
            } else {
                sb.append(user.getDisplayName() + ", ");
            }

            sb.setLength(sb.length() - 2);
            sender.sendMessage(sb.toString());
        }
    }
}

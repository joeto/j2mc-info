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
            StringBuilder nameBuilder = new StringBuilder();
            if (detail) {
                String name = user.getName();
                final HashSet<Character> flags = J2MC_Manager.getPermissions().getFlags(name);

                if (flags.isEmpty()) {
                    nameBuilder.append(ChatColor.GREEN).append(name);
                } else {
                    boolean markedTrusted = false;
                    if (J2MC_Manager.getVisibility().isVanished(user)) { // Vanished
                        nameBuilder.append(ChatColor.AQUA);
                    } else if (flags.contains('s')) { // Senior
                        nameBuilder.append(ChatColor.DARK_RED);
                    } else if (flags.contains('a')) { // Admin
                        nameBuilder.append(ChatColor.RED);
                    } else if (flags.contains('d')) { // Donator
                        nameBuilder.append(ChatColor.GOLD);
                    } else if (flags.contains('t')) { // Trusted
                        nameBuilder.append(ChatColor.DARK_GREEN);
                        markedTrusted = true;
                    } else { // Normal
                        nameBuilder.append(ChatColor.GREEN);
                    }
                    nameBuilder.append(name);

                    // Trusted
                    if ((flags.contains('t')) && !markedTrusted) {
                        nameBuilder.append(ChatColor.DARK_GREEN).append("[T]");
                    }

                    // Muted
                    if (flags.contains('M')) {
                        nameBuilder.append(ChatColor.YELLOW).append("[M]");
                    }

                    // NSA
                    if (flags.contains('N')) {
                        nameBuilder.append(ChatColor.RED).append("\u00ab\u00bb");
                    }

                    // Teleport Banned
                    if (flags.contains('T')) {
                        nameBuilder.append(ChatColor.GRAY).append(ChatColor.STRIKETHROUGH).append("[TP]").append(ChatColor.RESET);
                    }

                    // Harassed
                    if (flags.contains('H')) {
                        nameBuilder.append(ChatColor.AQUA).append("[H]");
                    }

                }
            } else {
                nameBuilder.append(user.getDisplayName());
            }
            nameBuilder.append(ChatColor.RESET).append(", ");
            sb.append(nameBuilder.toString());
        }
        sb.setLength(sb.length() - 2);
        sender.sendMessage(sb.toString());
    }
}

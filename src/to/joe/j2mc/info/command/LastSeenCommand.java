package to.joe.j2mc.info.command;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import to.joe.j2mc.core.command.MasterCommand;
import to.joe.j2mc.info.J2MC_Info;

public class LastSeenCommand extends MasterCommand {

    J2MC_Info plugin;

    public LastSeenCommand(J2MC_Info instance) {
        super(instance);
        this.plugin = instance;
    }

    @Override
    public void exec(CommandSender sender, String commandName, String[] args, Player player, boolean isPlayer) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /lastseen <playername>");
            return;
        }
        Player possiblematch = plugin.getServer().getPlayerExact(args[0]);
        if (possiblematch != null && player.canSee(possiblematch)) {
            sender.sendMessage(ChatColor.AQUA + possiblematch.getName() + ChatColor.RED + " is online right now");
            return;
        }
        OfflinePlayer offlineplayer = plugin.getServer().getOfflinePlayer(args[0]);
        long start = offlineplayer.getLastPlayed() / 1000;
        if (start == 0) {
            sender.sendMessage(ChatColor.RED + "That user has never played on this server");
            return;
        }
        long end = System.currentTimeMillis() / 1000;
        
        StringBuilder sb = new StringBuilder();
        long diffInSeconds = (end - start) / 1000;

        long sec = (diffInSeconds >= 60 ? diffInSeconds % 60 : diffInSeconds);
        long min = (diffInSeconds = (diffInSeconds / 60)) >= 60 ? diffInSeconds % 60 : diffInSeconds;
        long hrs = (diffInSeconds = (diffInSeconds / 60)) >= 24 ? diffInSeconds % 24 : diffInSeconds;
        long days = (diffInSeconds = (diffInSeconds / 24)) >= 30 ? diffInSeconds % 30 : diffInSeconds;
        long months = (diffInSeconds = (diffInSeconds / 30)) >= 12 ? diffInSeconds % 12 : diffInSeconds;
        long years = (diffInSeconds = (diffInSeconds / 12));

        if (years > 0) {
            if (years == 1) {
                sb.append("a year");
            } else {
                sb.append(years + " years");
            }
            if (years <= 6 && months > 0) {
                if (months == 1) {
                    sb.append(" and a month");
                } else {
                    sb.append(" and " + months + " months");
                }
            }
        } else if (months > 0) {
            if (months == 1) {
                sb.append("a month");
            } else {
                sb.append(months + " months");
            }
            if (months <= 6 && days > 0) {
                if (days == 1) {
                    sb.append(" and a day");
                } else {
                    sb.append(" and " + days + " days");
                }
            }
        } else if (days > 0) {
            if (days == 1) {
                sb.append("a day");
            } else {
                sb.append(days + " days");
            }
            if (days <= 3 && hrs > 0) {
                if (hrs == 1) {
                    sb.append(" and an hour");
                } else {
                    sb.append(" and " + hrs + " hours");
                }
            }
        } else if (hrs > 0) {
            if (hrs == 1) {
                sb.append("an hour");
            } else {
                sb.append(hrs + " hours");
            }
            if (min > 1) {
                sb.append(" and " + min + " minutes");
            }
        } else if (min > 0) {
            if (min == 1) {
                sb.append("a minute");
            } else {
                sb.append(min + " minutes");
            }
            if (sec > 1) {
                sb.append(" and " + sec + " seconds");
            }
        } else {
            if (sec <= 1) {
                sb.append("about a second");
            } else {
                sb.append("about " + sec + " seconds");
            }
        }

        sb.append(" ago");

        String message = ChatColor.AQUA + offlineplayer.getName() + ChatColor.RED + " was last seen " + sb.toString();
        sender.sendMessage(message);
    }

}

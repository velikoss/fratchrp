package me.awesomemi.FratchRP.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class name implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player pl = (Player) sender;
            if (args.length <= 1) return false;
            else {
                pl.setCustomName(args[0] + " " +  args[1]);
                pl.setPlayerListName(args[0] + " " +  args[1]);
                pl.sendMessage(pl.getCustomName());
                pl.setMaxHealth(20);
                pl.setHealth(20);
            }
        }
        return false;
    }
}

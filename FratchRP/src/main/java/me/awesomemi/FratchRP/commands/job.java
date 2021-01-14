package me.awesomemi.FratchRP.commands;

import me.awesomemi.FratchRP.FratchRP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class job implements CommandExecutor {
    private FratchRP plugin;

    public job(FratchRP pl) {
        plugin = pl;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //1. Крафтер
        //2. Шахтер
        //3. Охотник
        //4. Фермер
        //5. Дровосек
        //6. Врач
        //7. Строитель
        //8. Мент
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length == 2){
                Player target = Bukkit.getPlayerExact(args[1]);
                String a = args[0];
                if(p.getCustomName().contains("]")){
                    p.setCustomName(ChatColor.RESET + p.getCustomName().substring(0, p.getCustomName().length()-6));
                }
                target.setCustomName(p.getCustomName() + plugin.getConfig().getString(a.toUpperCase().substring(0,0)) +  " [" + a.toUpperCase().substring(0,0) + "]" + ChatColor.WHITE);
                target.setPlayerListName(target.getCustomName());
            }else{
                p.sendMessage("Использование: /job Профессия Игрок");
                p.sendMessage("Список профессий:\n" +
                        "Крафтер\n" +
                        "Шахтер\n" +
                        "Охотник\n" +
                        "Фермер\n" +
                        "Дровосек\n" +
                        "Врач\n" +
                        "Строитель\n" +
                        "Полицейский");
            }
        }else{
            System.out.println("Чтобы использовать эту комманду, ты должен быть в игре!");
        }
        return false;
    }
}

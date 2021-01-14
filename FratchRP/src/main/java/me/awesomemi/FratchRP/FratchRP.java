package me.awesomemi.FratchRP;
import me.awesomemi.FratchRP.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.util.Objects;

import static org.bukkit.entity.EntityType.*;

public final class FratchRP extends JavaPlugin implements Listener {
    private Scoreboard board;
    FileConfiguration config = getConfig();
    FileConfiguration data = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "data.yml"));
    boolean warningfarmer = true;
    int count = 0;
    @Override
    public void onEnable() {
        config.options().copyDefaults(true);
        saveConfig();
        board = Bukkit.getScoreboardManager().getNewScoreboard();
        board.registerNewTeam("HideTags");
        Team team = board.getTeam("HideTags");
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
        team.setCanSeeFriendlyInvisibles(false);
        Bukkit.getPluginManager().registerEvents(this, this);
        getCommand("name").setExecutor(new name());
        getCommand("unname").setExecutor(new unname());
        this.getCommand("job").setExecutor(new job(this));
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();
        board.getTeam("HideTags").addEntry(p.getName());
        p.setScoreboard(board);
        p.setPlayerListName(p.getCustomName());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if(e.getMessage().charAt(0) == '.'){
            e.setFormat(ChatColor.ITALIC + p.getCustomName() + " шепчет:" + ChatColor.ITALIC + e.getMessage().replace('.',' ') );
            Location loc = e.getPlayer().getLocation();
            e.getRecipients().removeIf(player -> player.getLocation().distance(loc) > 5);
        }
        else if(e.getMessage().charAt(0) == '@'){
            e.setFormat(ChatColor.GOLD + "ОБЪЯВЛЕНИЕ:"  + ChatColor.WHITE +  e.getMessage().replace('@',' ')  + " [" + p.getCustomName() + "] ");
        }
        else if(e.getMessage().charAt(0) == '!'){
            e.setFormat(ChatColor.BOLD + p.getCustomName()+ " кричит:" + ChatColor.BOLD +e.getMessage().replace('!',' ').toUpperCase() );
            Location loc = e.getPlayer().getLocation();
            e.getRecipients().removeIf(player -> player.getLocation().distance(loc) > 50);
        }
        else{
            e.setFormat(ChatColor.WHITE + p.getCustomName() + " говорит: " + e.getMessage() );
            Location loc = e.getPlayer().getLocation();
            e.getRecipients().removeIf(player -> player.getLocation().distance(loc) > 20);
        }
    }
    @EventHandler
    public void Farmer(BlockBreakEvent e) throws InterruptedException {
        Player p = e.getPlayer();
        if(e.getBlock().getType() == Material.WHEAT  || e.getBlock().getType() == Material.WHEAT_SEEDS || e.getBlock().getType() == Material.WHEAT || e.getBlock().getType() == Material.PUMPKIN || e.getBlock().getType() == Material.MELON || e.getBlock().getType() == Material.BEETROOT || e.getBlock().getType() == Material.BEETROOT_SEEDS){
            if(p.getCustomName().contains("[Ф]")){
            }
             else {
                e.setCancelled(true);
                    if(warningfarmer){
                        p.sendMessage("Вы не фермер!");
                        warningfarmer = false;
                    }else{
                        count++;
                        if(count == 20){
                            count=0;
                            warningfarmer = true;
                        }
                    }
             }
        }
    }
    @EventHandler
    public void Wood(BlockBreakEvent e){
        Player p = e.getPlayer();
        if(e.getBlock().getType() == Material.OAK_LOG  || e.getBlock().getType() == Material.DARK_OAK_LOG || e.getBlock().getType() == Material.ACACIA_LOG|| e.getBlock().getType() == Material.BIRCH_LOG || e.getBlock().getType() == Material.JUNGLE_LOG || e.getBlock().getType() == Material.SPRUCE_LOG || e.getBlock().getType() == Material.STRIPPED_OAK_LOG  || e.getBlock().getType() == Material.STRIPPED_DARK_OAK_LOG || e.getBlock().getType() == Material.STRIPPED_ACACIA_LOG|| e.getBlock().getType() == Material.STRIPPED_BIRCH_LOG || e.getBlock().getType() == Material.STRIPPED_JUNGLE_LOG || e.getBlock().getType() == Material.STRIPPED_SPRUCE_LOG){
            if(p.getCustomName().contains("[Д]")){
            }
            else {
                e.setCancelled(true);
                p.sendMessage("Вы не дровосек!");
            }
        }
    }
    @EventHandler
    public void Hunter(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player && ( e.getEntityType().equals(CAVE_SPIDER) || e.getEntityType().equals(CREEPER) || e.getEntityType().equals(SKELETON) || e.getEntityType().equals(SPIDER) || e.getEntityType().equals(ZOMBIE) || e.getEntityType().equals(ZOMBIE_VILLAGER) || e.getEntityType().equals(SLIME) || e.getEntityType().equals(ENDERMAN) || e.getEntityType().equals(GHAST))){
            Player p = (Player) e.getDamager();
            if(p.getCustomName().contains("[О]")){
            }
            else {
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void Miner(BlockBreakEvent e){
        Player p = e.getPlayer();
        if(!p.getCustomName().contains("[Ш]") && (e.getBlock().getType().equals(Material.STONE)||e.getBlock().getType().equals(Material.COAL_ORE)||e.getBlock().getType().equals(Material.DIAMOND_ORE)||e.getBlock().getType().equals(Material.GOLD_ORE)||e.getBlock().getType().equals(Material.EMERALD_ORE)||e.getBlock().getType().equals(Material.IRON_ORE)||e.getBlock().getType().equals(Material.GOLD_ORE)||e.getBlock().getType().equals(Material.LAPIS_ORE)||e.getBlock().getType().equals(Material.REDSTONE_ORE))){
            e.setCancelled(true);
            p.sendMessage("Вы не шахтёр!");
        }else{

        }
    }
    @EventHandler
    public void Crafter(CraftItemEvent e){
        Player p = (Player) e.getWhoClicked();
        if(p.getCustomName().contains("[К]")){
        }
        else {
            e.setCancelled(true);
            p.sendMessage("Вы не крафтер!");
        }
    }
    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent e) {
        if (!e.getRightClicked().getType().equals(EntityType.PLAYER)) return;
        else if(e.getPlayer().getAllowFlight()) e.getPlayer().sendActionBar(e.getRightClicked().getName());
        else e.getPlayer().sendActionBar(Objects.requireNonNull(e.getRightClicked().getCustomName())); // д
    }
    @EventHandler
    public void onLay(){

    }
}


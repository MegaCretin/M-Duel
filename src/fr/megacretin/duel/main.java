package fr.megacretin.duel;

import fr.megacretin.arena.ArenaManager;
import fr.megacretin.duel.arenas.ArenaListener;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class main extends JavaPlugin {
    private Map<Player, Player> players = new HashMap();
    private ArenaManager arenaManager = new ArenaManager();
    public static boolean isStarted = false;

    public main() {
    }

    public void onEnable() {
        this.getCommand("duel").setExecutor(this);
        this.getServer().getPluginManager().registerEvents(new ArenaListener(this), this);
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }

    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("duel") && sender instanceof Player) {
            Player player = (Player)sender;
            if (args.length == 0) {
                player.sendMessage("§6§lDUEL §8» §f§lInfo : §r§7Défiez-vos amis avec : §e/duel <player>");
                return true;
            }

            if (args.length == 1) {
                String targetName = args[0];
                Player target;
                if (args[0].equalsIgnoreCase("accept")) {
                    if (this.players.containsKey(player) && !isStarted) {
                        player.sendMessage("§6§lDUEL §8» §fPrépare toi ! Le duel va commencer !");
                        target = (Player)this.players.get(player);
                        target.sendMessage("§6§lDUEL §8» §fPrépare toi ! Le duel va commencer !");
                        this.players.remove(player);
                        player.setAllowFlight(false);
                        target.setAllowFlight(false);
                        ArenaManager.addArenaPlayer(player);
                        ArenaManager.addArenaPlayer(target);
                        player.teleport(new Location(Bukkit.getWorld("void"), 89.5, 101D, 115.5, -180.0F, 0.0F));
                        target.teleport(new Location(Bukkit.getWorld("void"), 89.5, 101D, 103.5, 0.0F, 0.0F));
                        isStarted = true;
                    } else {
                        player.sendMessage("§6§lDUEL §8» §cUn combat est déjà en cours !!");
                    }

                    this.players.remove(player);
                } else if (args[0].equalsIgnoreCase("deny")) {
                    if (this.players.containsKey(player)) {
                        player.sendMessage("§6§lDUEL §8» §cVous avez refusé le duel");
                        target = (Player)this.players.get(player);
                        target.sendMessage("§6§lDUEL §8» §cLe joueur §4" + player.getName() + "§c a refusé le duel !");
                        this.players.remove(player);
                    }
                } else if (Bukkit.getPlayer(targetName) != null) {
                    target = Bukkit.getPlayer(targetName);
                    if (this.players.containsKey(target)) {
                        player.sendMessage("§6§lDUEL §8» §cAttention, ce joueur a déjà une demande de défi en cours");
                        return true;
                    }

                    if(target.equals(player)){
                        player.sendMessage("§6§lDUEL §8» §cVous ne pouvez pas vous défier");
                        return true;
                    }

                    if (isStarted) {
                        player.sendMessage("§6§lDUEL §8» §cUn duel est déjà en cours");
                    }
                    else {
                        this.players.put(target, player);
                        player.sendMessage("§6§lDUEL §8» §7Vous venez de défier §a" + targetName);
                        TextComponent demande = new TextComponent("§6§lDUEL §8» §7Vous êtes défié par §a" + player.getName() + "\n \n");
                        TextComponent accept = new TextComponent("§a1. Accepter le duel\n");
                        TextComponent deny = new TextComponent("§c2. Refuser le duel");
                        demande.setHoverEvent((HoverEvent)null);
                        demande.setClickEvent((ClickEvent)null);
                        accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder("§aAccepter le duel contre " + player.getName())).create()));
                        accept.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, "/duel accept"));
                        deny.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, "/duel deny"));
                        deny.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder("§aRefuser le duel contre " + player.getName())).create()));
                        demande.addExtra(accept);
                        accept.addExtra(deny);
                        target.spigot().sendMessage(demande);
                    }


                } else {
                    player.sendMessage("§6§lDUEL §8» §cLe joueur n'est pas connecté");
                }

                return true;
            }
        }

        return false;
    }

    public boolean isStarded() {
        return isStarted;
    }

    public ArenaManager getArenaManager() {
        return this.arenaManager;
    }

    public void setArenaManager(ArenaManager arenaManager) {
        this.arenaManager = arenaManager;
    }
}


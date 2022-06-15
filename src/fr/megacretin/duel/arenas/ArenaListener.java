package fr.megacretin.duel.arenas;

import fr.megacretin.arena.ArenaManager;
import fr.megacretin.duel.main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ArenaListener implements Listener {
    private main duel;

    public ArenaListener(main main) {
        this.duel = main;
    }

    @EventHandler
    public void onKill(PlayerDeathEvent event) {
        System.out.println("Event kill detect");
        if (event.getEntity().getKiller() instanceof Player) {
            Player victim = event.getEntity();
            Player killer = victim.getKiller();
            System.out.println("au dessus");
            if (ArenaManager.checkPlayerInList()) {
                Location spawn = new Location(Bukkit.getWorld("void"), 89.5, 107D, 109.5, 0.0F, 0.0F);
                victim.teleport(spawn);
                killer.teleport(spawn);
                killer.sendMessage("§6§lDUEL §8» §aTu as gagné ton duel");
                victim.sendMessage("§6§lDUEL §8» §cTu as perdu le duel");
                ArenaManager.clearList();
                main.isStarted = false;
            }
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player leaver = event.getPlayer();
        Location spawn = new Location(Bukkit.getWorld("void"),  89.5, 107D, 109.5, 0.0F, 0.0F);
        if (ArenaManager.checkContainPlayer(leaver)) {
            ArenaManager.removePlayer(leaver);
            leaver.teleport(spawn);

            for(int i = 0; i < ArenaManager.getList().size(); ++i) {
                Player player = (Player)ArenaManager.getList().get(i);
                player.teleport(spawn);
                main.isStarted = false;
                player.sendMessage("§6§lDUEL §8» §aTu as gagné car ton adversaire à abandonné !!");
                ArenaManager.removePlayer(player);
            }

        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        if (ArenaManager.getList().contains(p) && (!p.isOp() || e.getMessage().equalsIgnoreCase("/spawn") || e.getMessage().equalsIgnoreCase("/feed") || e.getMessage().equalsIgnoreCase("/heal") || e.getMessage().contains("/home") || e.getMessage().equalsIgnoreCase("/ec") || e.getMessage().contains("/is warp") || e.getMessage().equalsIgnoreCase("/spawn") || e.getMessage().equalsIgnoreCase("/is") || e.getMessage().equalsIgnoreCase("/is go") || e.getMessage().equalsIgnoreCase("/shop") || e.getMessage().equalsIgnoreCase("/tpa") || e.getMessage().equalsIgnoreCase("/tpyes") || e.getMessage().equalsIgnoreCase("/tpaccept") || e.getMessage().equalsIgnoreCase("/back") || e.getMessage().equalsIgnoreCase("/hub") || e.getMessage().equalsIgnoreCase("/lobby") || e.getMessage().equalsIgnoreCase("/vote") || e.getMessage().equalsIgnoreCase("/fly") || e.getMessage().contains("cmi") || e.getMessage().contains("CMI") || e.getMessage().contains("Cmi") || e.getMessage().contains("CMi") || e.getMessage().contains("cMi") || e.getMessage().contains("cMI") || e.getMessage().contains("CmI"))) {
            e.setCancelled(true);
        }

    }
}

package fr.megacretin.arena;


import org.bukkit.entity.Player;
import java.util.ArrayList;

public class ArenaManager {
    static ArrayList<Player> arena = new ArrayList();

    public ArenaManager() {
    }

    public static void addArenaPlayer(Player p) {
        arena.add(p);
        System.out.println(arena);
    }

    public static boolean checkPlayerInList() {
        return !arena.isEmpty();
    }

    public static ArrayList<Player> getList() {
        return arena;
    }

    public static boolean checkContainPlayer(Player p) {
        return arena.contains(p);
    }

    public static void clearList() {
        arena.clear();
    }

    public static void removePlayer(Player p) {
        arena.remove(p);
    }
}

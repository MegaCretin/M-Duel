package fr.megacretin.arena;

public class Arena {
    public static boolean isStarted = false;

    public Arena() {
    }

    public void setStarted() {
        isStarted = true;
    }

    public boolean isStarded() {
        return isStarted;
    }
}

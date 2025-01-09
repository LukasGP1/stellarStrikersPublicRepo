package de.lulkas_.stellarstrikers.level.time;

public class GameTimer {
    private int ticks = 0;
    private int seconds = 0;

    public void tick() {
        ticks++;
        seconds = ((int) (ticks / 2000));
    }

    public int getTicks() {
        return ticks;
    }

    public void reset() {
        ticks = 0;
        seconds = 0;
    }

    public float getAccurateSeconds() {
        return ((float) ticks) / 2000;
    }
}

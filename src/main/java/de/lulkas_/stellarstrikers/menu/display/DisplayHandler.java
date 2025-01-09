package de.lulkas_.stellarstrikers.menu.display;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DisplayHandler {
    public List<Display> displays = new ArrayList<>();
    private int xShifted;
    private float yScale;

    protected void addDisplay(Display display) {
        displays.add(display);
    }

    public void tick() {
        for(int i = 0; i < displays.size(); i++) {
            displays.get(i).setxShifted(xShifted);
            displays.get(i).setyScale(yScale);
            displays.get(i).tick();
        }
    }

    public void setxShifted(int xShifted) {
        this.xShifted = xShifted;
    }

    public void setyScale(float yScale) {
        this.yScale = yScale;
    }

    public Graphics draw(Graphics g) {
        for(int i = 0; i < displays.size(); i++) {
            g = displays.get(i).draw(g);
        }
        return g;
    }
}

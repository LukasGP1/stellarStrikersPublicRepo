package de.lulkas_.stellarstrikers.menu.display;

import java.util.ArrayList;
import java.util.List;

public class DisplayHandler {
    public List<Display> displays = new ArrayList<>();

    protected void addDisplay(Display display) {
        displays.add(display);
    }

    public void tick() {
        for(int i = 0; i < displays.size(); i++) {
            displays.get(i).tick();
        }
    }
}

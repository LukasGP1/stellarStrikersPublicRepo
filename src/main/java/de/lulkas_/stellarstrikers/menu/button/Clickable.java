package de.lulkas_.stellarstrikers.menu.button;

import java.awt.*;

public interface Clickable {
    Rectangle getClickingArea();
    Runnable getWhenCLicked();
    void tick();
}

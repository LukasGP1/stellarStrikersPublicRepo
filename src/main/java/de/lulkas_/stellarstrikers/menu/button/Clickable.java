package de.lulkas_.stellarstrikers.menu.button;

import java.awt.*;

public interface Clickable {
    Rectangle getClickingArea();
    Runnable getWhenCLicked();
    void setXShifted(int value);
    void setYScale(float value);
    void tick();
    Graphics draw(Graphics g);
}

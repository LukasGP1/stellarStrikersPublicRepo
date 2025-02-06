package de.lulkas_.stellarstrikers.inputs;

import de.lulkas_.stellarstrikers.GameObjectHandler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInputs implements MouseListener, MouseMotionListener {
    private GameObjectHandler gameObjectHandler;

    public MouseInputs(GameObjectHandler gameObjectHandler) {
        this.gameObjectHandler = gameObjectHandler;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.gameObjectHandler.lastClick = e;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}

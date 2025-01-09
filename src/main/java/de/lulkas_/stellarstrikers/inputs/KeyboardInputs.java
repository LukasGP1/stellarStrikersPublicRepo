package de.lulkas_.stellarstrikers.inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputs implements KeyListener {

    private boolean aPressed = false;
    private boolean dPressed = false;
    private boolean spacePressed = false;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_A) {
            aPressed = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_D) {
            dPressed = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_A) {
            aPressed = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_D) {
            dPressed = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
    }

    public boolean isaPressed() {
        return aPressed;
    }

    public boolean isdPressed() {
        return dPressed;
    }

    public boolean isSpacePressed() {
        return spacePressed;
    }
}

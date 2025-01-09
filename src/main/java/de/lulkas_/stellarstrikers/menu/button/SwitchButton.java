package de.lulkas_.stellarstrikers.menu.button;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class SwitchButton implements Clickable {
    private final Image img1;
    private final Image img2;
    private boolean state;
    private final Rectangle originalClickingArea;
    private final Runnable whenClicked;
    private int xShifted;
    private float yScale;

    public SwitchButton(Rectangle originalClickingArea, Runnable whenClicked, String path1, String path2, boolean state) {
        this.originalClickingArea = originalClickingArea;
        this.whenClicked = whenClicked;
        this.img1 = importImage(path1);
        this.img2 = importImage(path2);
        this.state = state;
    }

    @Override
    public Rectangle getClickingArea() {
        Rectangle toReturn = new Rectangle(originalClickingArea);
        toReturn.setBounds(((int) (xShifted + originalClickingArea.x * yScale)), ((int) (originalClickingArea.y * yScale)), ((int) (originalClickingArea.width * yScale)), ((int) (originalClickingArea.height * yScale)));
        return toReturn;
    }

    @Override
    public Runnable getWhenCLicked() {
        return whenClicked;
    }

    @Override
    public void setXShifted(int value) {
        xShifted = value;
    }

    @Override
    public void setYScale(float value) {
        yScale = value;
    }

    @Override
    public void tick() {

    }

    @Override
    public Graphics draw(Graphics g) {
        if(state) {
            g.drawImage(img1.getScaledInstance(getClickingArea().width, getClickingArea().height, Image.SCALE_DEFAULT), getClickingArea().x, getClickingArea().y, null);
        } else {
            g.drawImage(img2.getScaledInstance(getClickingArea().width, getClickingArea().height, Image.SCALE_DEFAULT), getClickingArea().x, getClickingArea().y, null);
        }

        return g;
    }

    public void changeState() {
        state = !state;
    }

    protected Image importImage(String path) {
        InputStream is = getClass().getResourceAsStream(path);
        try {
            return ImageIO.read(is).getScaledInstance(this.originalClickingArea.width, this.originalClickingArea.height, Image.SCALE_DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

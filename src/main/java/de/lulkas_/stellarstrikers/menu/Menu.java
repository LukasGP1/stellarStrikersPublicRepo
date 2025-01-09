package de.lulkas_.stellarstrikers.menu;

import de.lulkas_.stellarstrikers.GamePanel;
import de.lulkas_.stellarstrikers.Main;
import de.lulkas_.stellarstrikers.menu.button.Clickable;
import de.lulkas_.stellarstrikers.menu.display.DisplayHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public abstract class Menu {
    protected Image img;
    private final String path;
    protected List<Clickable> clickables = new ArrayList<>();
    private boolean processedLastClick = true;
    private MouseEvent lastProcessedClick = null;
    protected GamePanel gamePanel;
    public DisplayHandler displayHandler;
    public int xShifted;
    public float yScale;

    protected void importImage() {
        InputStream is = getClass().getResourceAsStream(this.path);
        try {
            img = ImageIO.read(is).getScaledInstance(Main.game.window.getWidth(), Main.game.window.getHeight(), Image.SCALE_DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void addButton(Clickable clickable) {
        clickables.add(clickable);
    }

    public Menu(String path, GamePanel gamePanel) {
        this.path = path;
        this.gamePanel = gamePanel;
        this.displayHandler = createDisplayHandler();
        gamePanel.lastClick = null;
        importImage();
    }

    protected abstract DisplayHandler createDisplayHandler();

    public Graphics draw(Graphics g) {
        g.drawImage(img.getScaledInstance(Main.game.window.getHeight(), Main.game.window.getHeight(), Image.SCALE_DEFAULT), xShifted, 0, null);
        g = displayHandler.draw(g);

        for(Clickable clickable : clickables) {
            g = clickable.draw(g);
        }

        return g;
    }

    public void tick() {
        xShifted = (Main.game.window.getWidth() - 960) / 2;
        yScale = Main.game.window.getHeight() / 960f;
        displayHandler.setxShifted(xShifted);
        displayHandler.setyScale(yScale);

        for(int i = 0; i < clickables.size(); i++) {
            clickables.get(i).setXShifted(xShifted);
            clickables.get(i).setYScale(yScale);
        }

        if(gamePanel.lastClick != this.lastProcessedClick) {
            processedLastClick = false;
        }

        if(!processedLastClick) {
            processClick(gamePanel.lastClick);
            this.lastProcessedClick = gamePanel.lastClick;
            this.processedLastClick = true;
        }

        displayHandler.tick();

        for(Clickable clickable : clickables) {
            clickable.tick();
        }
    }

    private void processClick(MouseEvent click) {
        if(click.getButton() == MouseEvent.BUTTON1) {
            for (int i = 0; i < clickables.size(); i++) {
                if(clickables.get(i).getClickingArea().contains(click.getPoint())) {
                    clickables.get(i).getWhenCLicked().run();
                }
            }
        }
    }
}

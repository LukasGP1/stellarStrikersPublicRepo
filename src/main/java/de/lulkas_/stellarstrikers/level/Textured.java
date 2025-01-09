package de.lulkas_.stellarstrikers.level;

import de.lulkas_.stellarstrikers.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public abstract class Textured extends Entity {
    protected Image img;
    private final String path;
    protected final GamePanel gamePanel;

    protected Image importImage(String path) {
        InputStream is = getClass().getResourceAsStream(path);
        try {
            return ImageIO.read(is).getScaledInstance(this.width, this.height, Image.SCALE_DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Textured(float startX, float startY, String path, int width, int height, int health, GamePanel gamePanel) {
        super(width, height, startX, startY, health, gamePanel);
        this.path = path;
        this.gamePanel = gamePanel;
        img = importImage(this.path);
    }

    public Graphics draw(Graphics g) {
        g.drawImage(img, ((int) x), ((int) y), null);
        return g;
    }
}

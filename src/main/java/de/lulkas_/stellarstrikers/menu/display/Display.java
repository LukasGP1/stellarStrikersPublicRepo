package de.lulkas_.stellarstrikers.menu.display;

import de.lulkas_.stellarstrikers.util.CoordConversion;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Display {
    private final Color color;
    protected final int fontSize;
    protected int gameX, gameY;
    protected int screenX, screenY;
    protected int gameWidth, gameHeight;
    protected int screenWidth, screenHeight;
    protected String text;

    public Display(Color color, int fontSize, int gameX, int gameY, String text, int gameWidth, int gameHeight) {
        this.color = color;
        this.fontSize = fontSize;
        this.gameX = gameX;
        this.gameY = gameY;
        this.text = text;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
    }

    public Graphics draw(Graphics g) {
        g.drawImage(getTextAsImage(text).getScaledInstance(screenWidth, screenHeight, Image.SCALE_DEFAULT), screenX, screenY, null);
        return g;
    }

    public void tick() {
        float[] screenCoords = CoordConversion.gameToScreen(new Float[]{((float) gameX), ((float) gameY)});
        screenX = ((int) screenCoords[0]);
        screenY = ((int) screenCoords[1]);

        float[] screenSize = CoordConversion.gameToScreen(new Float[]{((float) gameWidth), ((float) gameHeight)});
        screenWidth = ((int) screenSize[0]);
        screenHeight = ((int) screenSize[1]);
    }

    protected Image getTextAsImage(String text) {
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        Font font = new Font("Arial", Font.PLAIN, ((int) (this.fontSize)));
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int width = fm.stringWidth(text) + 100;
        int height = fm.getHeight();
        g2d.dispose();

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setFont(font);
        fm = g2d.getFontMetrics();
        g2d.setColor(this.color);
        g2d.drawString(text, 0, fm.getAscent());
        g2d.dispose();

        return img;
    }
}

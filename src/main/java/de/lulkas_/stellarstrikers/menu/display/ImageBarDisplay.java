package de.lulkas_.stellarstrikers.menu.display;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;

public class ImageBarDisplay extends Display {
    private final Supplier<Integer> getCount;
    private final Image image;

    public ImageBarDisplay(int size, int x, int y, String path, Supplier<Integer> getCount) {
        super(new Color(0, 0, 0), size, x, y, "");
        this.getCount = getCount;
        this.image = importImage(path);
    }

    @Override
    public Graphics draw(Graphics g) {
        for(int i = 0; i < getCount.get(); i++) {
            g.drawImage(image, x + size * i, y, null);
        }

        return g;
    }

    private Image importImage(String path) {
        InputStream is = getClass().getResourceAsStream(path);
        try {
            return ImageIO.read(is).getScaledInstance(this.size, this.size, Image.SCALE_DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

package de.lulkas_.stellarstrikers.menu.display;

import java.awt.*;
import java.util.function.Supplier;

public class IntDisplay extends Display {
    private final String name1, name2;
    private final Supplier<Integer> toDraw;
    private final int numerals;

    public IntDisplay(String name1, int x, int y, int size, Color color, String name2, Supplier<Integer> toDraw, int numerals, int gameWidth, int gameHeight) {
        super(color, size, x, y, "", gameWidth, gameHeight);
        this.name1 = name1;
        this.name2 = name2;
        this.toDraw = toDraw;
        this.numerals = numerals;
    }

    public Graphics draw(Graphics g) {
        Integer value = toDraw.get();
        String valueWithSpaces = value.toString();

        if(valueWithSpaces.length() > numerals) {
            StringBuilder builder = new StringBuilder();
            for(int i = 0; i < numerals; i++) {
                builder.append("9");
            }
            valueWithSpaces = builder.toString();
        }

        if(valueWithSpaces.length() < numerals) {
            StringBuilder builder = new StringBuilder(valueWithSpaces);
            while(builder.length() < numerals) {
                builder.insert(0, " ");
            }
            valueWithSpaces = builder.toString();
        }

        this.text = name1 + valueWithSpaces  + name2;
        return super.draw(g);
    }
}

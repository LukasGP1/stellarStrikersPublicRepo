package de.lulkas_.stellarstrikers.menu.display;

import java.awt.*;
import java.util.function.Supplier;

public class ThreeIntDisplay extends Display {
    private final String name1, name2, name3, name4;
    private final Supplier<Integer> toDraw1, toDraw2, toDraw3;
    private final int numerals;

    public ThreeIntDisplay(Color color, int size, int x, int y, String name1, Supplier<Integer> toDraw1, String name2, Supplier<Integer> toDraw2, String name3, Supplier<Integer> toDraw3, String name4, int numerals, int gameWidth, int gameHeight) {
        super(color, size, x, y, "", gameWidth, gameHeight);
        this.name1 = name1;
        this.name2 = name2;
        this.name3 = name3;
        this.name4 = name4;
        this.toDraw1 = toDraw1;
        this.toDraw2 = toDraw2;
        this.toDraw3 = toDraw3;
        this.numerals = numerals;
    }

    private String cutTo(int numerals, String string) {
        String toReturn = string;

        if(string.length() > numerals) {
            StringBuilder builder = new StringBuilder();
            for(int i = 0; i < numerals; i++) {
                builder.append("9");
            }
            toReturn = builder.toString();
        }

        if(string.length() < numerals)  {
            StringBuilder builder = new StringBuilder(string);
            while(builder.length() < numerals) {
                builder.insert(0, " ");
            }
            toReturn = builder.toString();
        }

        return toReturn;
    }

    public Graphics draw(Graphics g) {
        Integer value1 = toDraw1.get();
        String valueWithSpaces1 = cutTo(numerals, value1.toString());
        Integer value2 = toDraw2.get();
        String valueWithSpaces2 = cutTo(numerals, value2.toString());
        Integer value3 = toDraw3.get();
        String valueWithSpaces3 = cutTo(numerals, value3.toString());

        this.text = name1 + valueWithSpaces1 + name2 + valueWithSpaces2 + name3 + valueWithSpaces3 + name4;
        return super.draw(g);
    }
}

package de.lulkas_.stellarstrikers.menu.menus.credits;

import de.lulkas_.stellarstrikers.menu.display.Display;
import de.lulkas_.stellarstrikers.menu.display.DisplayHandler;

import java.awt.*;

public class CreditsDisplayHandler extends DisplayHandler {
    public CreditsDisplayHandler() {
        addDisplay(new Display(Color.WHITE, 30, 100, 0, "Spaceship Textures by Fearless Designs", 650, 50));
        addDisplay(new Display(Color.WHITE, 30, 100, 50, "Level Up sound by RibhavAgrawal", 400, 50));
        addDisplay(new Display(Color.WHITE, 30, 100, 100, "Shooting sound by Driken5482", 400, 50));
        addDisplay(new Display(Color.WHITE, 30, 100, 150, "Clicking sound from mixkit", 400, 50));
        addDisplay(new Display(Color.WHITE, 30, 100, 200, "Error sound from mixkit", 400, 50));
        addDisplay(new Display(Color.WHITE, 30, 100, 250, "Library for easier use of the gamejolt api by Ashley Gwinnell", 650, 50));
        addDisplay(new Display(Color.WHITE, 30, 100, 300, "Breaking sound from pixabay by freesound_community", 650, 50));
        addDisplay(new Display(Color.WHITE, 30, 100, 350, "Ding sound from pixabay by u_31vnwfmzt6",  650, 50));
        addDisplay(new Display(Color.WHITE, 30, 100, 400, "win sound effect from mixkit ", 450, 50));
        addDisplay(new Display(Color.WHITE, 30, 100, 450, "lose sound part 1 from mixkit ", 450, 50));
        addDisplay(new Display(Color.WHITE, 30, 100, 500, "lose sound part 2 from mixkit ", 450, 50));
        addDisplay(new Display(Color.WHITE, 30, 100, 550, "Links to everything in the credits file in the game directory", 650, 50));
    }
}

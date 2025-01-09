package de.lulkas_.stellarstrikers.menu.menus.credits;

import de.lulkas_.stellarstrikers.menu.display.Display;
import de.lulkas_.stellarstrikers.menu.display.DisplayHandler;

import java.awt.*;

public class CreditsDisplayHandler extends DisplayHandler {
    public CreditsDisplayHandler() {
        addDisplay(new Display(Color.WHITE, 30, 100, 0, "Spaceship Textures by Fearless Designs"));
        addDisplay(new Display(Color.WHITE, 30, 100, 50, "Level Up sound by RibhavAgrawal"));
        addDisplay(new Display(Color.WHITE, 30, 100, 100, "Shooting sound by Driken5482"));
        addDisplay(new Display(Color.WHITE, 30, 100, 150, "Clicking sound from mixkit"));
        addDisplay(new Display(Color.WHITE, 30, 100, 200, "Error sound from mixkit"));
        addDisplay(new Display(Color.WHITE, 30, 100, 250, "Library for easier use of the gamejolt api by Ashley Gwinnell"));
        addDisplay(new Display(Color.WHITE, 30, 100, 300, "Breaking sound from pixabay by freesound_community"));
        addDisplay(new Display(Color.WHITE, 30, 100, 350, "Ding sound from pixabay by u_31vnwfmzt6"));
        addDisplay(new Display(Color.WHITE, 30, 100, 400, "win sound effect from mixkit "));
        addDisplay(new Display(Color.WHITE, 30, 100, 450, "lose sound part 1 from mixkit "));
        addDisplay(new Display(Color.WHITE, 30, 100, 500, "lose sound part 2 from mixkit "));
        addDisplay(new Display(Color.WHITE, 30, 100, 550, "Links to everything in the credits file in the game directory"));
    }
}

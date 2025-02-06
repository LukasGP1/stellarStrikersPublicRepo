package de.lulkas_.stellarstrikers.menu;

import de.lulkas_.stellarstrikers.GameObjectHandler;
import de.lulkas_.stellarstrikers.menu.button.Clickable;
import de.lulkas_.stellarstrikers.menu.display.DisplayHandler;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class Menu {
    public List<Clickable> clickables = new ArrayList<>();
    private boolean processedLastClick = true;
    private MouseEvent lastProcessedClick = null;
    protected GameObjectHandler gameObjectHandler;
    public DisplayHandler displayHandler;

    protected void addButton(Clickable clickable) {
        clickables.add(clickable);
    }

    public Menu(GameObjectHandler gameObjectHandler) {
        this.gameObjectHandler = gameObjectHandler;
        this.displayHandler = createDisplayHandler();
        gameObjectHandler.lastClick = null;
    }

    protected abstract DisplayHandler createDisplayHandler();

    public void tick() {
        if(gameObjectHandler.lastClick != this.lastProcessedClick) {
            processedLastClick = false;
        }

        if(!processedLastClick) {
            processClick(gameObjectHandler.lastClick);
            this.lastProcessedClick = gameObjectHandler.lastClick;
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

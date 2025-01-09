package de.lulkas_.stellarstrikers;

import de.lulkas_.stellarstrikers.attributes.*;
import de.lulkas_.stellarstrikers.inputs.KeyboardInputs;
import de.lulkas_.stellarstrikers.inputs.MouseInputs;
import de.lulkas_.stellarstrikers.level.enemys.EnemyWaveHandler;
import de.lulkas_.stellarstrikers.level.player.Player;
import de.lulkas_.stellarstrikers.level.powerUp.PowerUpHandler;
import de.lulkas_.stellarstrikers.level.time.GameTimer;
import de.lulkas_.stellarstrikers.menu.menus.credits.CreditsMenu;
import de.lulkas_.stellarstrikers.menu.menus.game.GameMenu;
import de.lulkas_.stellarstrikers.menu.menus.lost.LostMenu;
import de.lulkas_.stellarstrikers.menu.menus.main.MainMenu;
import de.lulkas_.stellarstrikers.menu.menus.options.OptionsMenu;
import de.lulkas_.stellarstrikers.menu.menus.skill.SkillMenu;
import de.lulkas_.stellarstrikers.menu.menus.skin.SkinDisplay;
import de.lulkas_.stellarstrikers.menu.menus.skin.SkinMenu;
import de.lulkas_.stellarstrikers.menu.menus.won.WonMenu;
import de.lulkas_.stellarstrikers.save.SaveReader;
import de.lulkas_.stellarstrikers.save.SaveWiper;
import de.lulkas_.stellarstrikers.save.SaveWriter;
import de.lulkas_.stellarstrikers.sound.MusicHandler;
import de.lulkas_.stellarstrikers.sound.SoundHandler;
import org.gamejolt.GameJoltAPI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;

public class GamePanel extends JPanel {
    public MouseEvent lastClick = null;
    private final MouseInputs mouseInputs;
    private final KeyboardInputs keyboardInputs;
    public Player player;
    public GameState gameState = GameState.FIRST_ENTER_MAIN_MENU;
    public EnemyWaveHandler enemyWaveHandler;
    public MainMenu mainMenu;
    public GameMenu gameMenu;
    private WonMenu wonMenu;
    private LostMenu lostMenu;
    public SaveWriter saveWriter;
    public GameTimer gameTimer;
    public PlayerAttributeHandler playerAttributeHandler;
    private SkillMenu skillMenu;
    public PlayerSkillHandler playerSkillHandler;
    public SaveReader saveReader;
    public final GameJoltAPI api;
    public SaveWiper saveWiper;
    private Thread apiThread;
    private CreditsMenu creditsMenu;
    public PlayerStatisticsHandler playerStatisticsHandler;
    private SkinMenu skinMenu;
    public SkinDisplay skinDisplay;
    public PlayerPowerUpHandler playerPowerUpHandler;
    public PowerUpHandler powerUpHandler;
    private MusicHandler musicHandler;
    private OptionsMenu optionsMenu;
    public PlayerOptionsHandler playerOptionsHandler;

    public GamePanel(GameJoltAPI api) {
        this.api = api;
        mouseInputs = new MouseInputs(this);
        keyboardInputs = new KeyboardInputs();
        setPanelSize();
        addKeyListener(keyboardInputs);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
        this.saveWriter = new SaveWriter( this, this.api);
        this.gameTimer = new GameTimer();
        this.saveReader = new SaveReader(0, this.api);
        this.saveWiper = new SaveWiper(api, this);
        this.playerAttributeHandler = saveReader.getPlayerAttributeHandler(this);
        this.playerPowerUpHandler = new PlayerPowerUpHandler();
        this.playerSkillHandler = saveReader.getPlayerSkillHandler(this);
        this.playerOptionsHandler = saveReader.getPLayerOptionsHandler();
        this.playerStatisticsHandler = new PlayerStatisticsHandler(api);
        this.musicHandler = new MusicHandler("/sounds/music.wav", 3000, -10f, this);
        musicHandler.start();
    }

    private void createMainMenuObjects() {
        this.mainMenu = new MainMenu(this);
        playerAttributeHandler.levelingCooldown = 200;
    }

    private void createWonMenuObjects() {
        this.wonMenu = new WonMenu(this);
        this.playerAttributeHandler.score += enemyWaveHandler.getSingleGameScore();
        this.playerStatisticsHandler.winGame();
        SoundHandler.playSound("/sounds/menu/win.wav", -5f, this);
    }

    private void createSkillMenuObjects() {
        this.skillMenu = new SkillMenu(this);
    }

    private void createLostMenuObjects() {
        this.lostMenu = new LostMenu(this);
        SoundHandler.playSound("/sounds/menu/lose.wav", -5f, this);
    }

    private void createCreditsMenuObjects() {
        creditsMenu = new CreditsMenu(this);
    }

    private void createOptionsMenuObjects() {
        optionsMenu = new OptionsMenu(this);
    }

    private void createSkinMenuObjects() {
        skinDisplay = new SkinDisplay(448, 220, List.of("/textures/player/1.png", "/textures/player/2.png", "/textures/player/3.png", "/textures/player/4.png", "/textures/player/5.png", "/textures/player/6.png", "/textures/player/7.png", "/textures/player/8.png", "/textures/player/9.png", "/textures/player/10.png", "/textures/player/11.png", "/textures/player/12.png", "/textures/player/13.png", "/textures/player/14.png", "/textures/player/15.png", "/textures/player/16.png", "/textures/player/17.png", "/textures/player/18.png", "/textures/player/19.png"), playerAttributeHandler.getSkinDisplayed(), "/textures/menu/icon/lock.png", this);
        skinMenu = new SkinMenu(this);
    }

    private void createGameObjects() {
        this.player = new Player(3.0f * (Main.game.window.getWidth() / 960), this, Main.game.window.getWidth() / 2, 750, false, playerSkillHandler, playerAttributeHandler.getSkinDisplayed());
        this.enemyWaveHandler = new EnemyWaveHandler(0, this, player, playerAttributeHandler.getLevel() + 2);
        this.gameMenu = new GameMenu(this);
        this.gameMenu.tick();
        this.player.setY(750 * this.gameMenu.yScale);
        this.powerUpHandler = new PowerUpHandler(this);
        this.gameTimer.reset();
    }


    private void setPanelSize() {
        Dimension size = new Dimension(960, 960);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(gameState == GameState.MAIN_MENU) {
            g = mainMenu.draw(g);
        }

        if(gameState == GameState.WON_MENU) {
            g = wonMenu.draw(g);
        }

        if(gameState == GameState.LOST_MENU) {
            g = lostMenu.draw(g);
        }

        if(gameState == GameState.SKILL_MENU) {
            g = skillMenu.draw(g);
        }

        if(gameState == GameState.CREDITS_MENU) {
            g = creditsMenu.draw(g);
        }

        if(gameState == GameState.SKIN_MENU) {
            g = skinMenu.draw(g);
            g = skinDisplay.draw(g);
        }

        if(gameState == GameState.OPTIONS_MENU) {
            g = optionsMenu.draw(g);
        }

        if(gameState == GameState.PLAYING) {
            g = enemyWaveHandler.draw(g);
            g = player.draw(g);
            g = powerUpHandler.draw(g);
            g = gameMenu.draw(g);
        }
    }

    public void tick() {
        playerPowerUpHandler.tick();
        musicHandler.tick();

        if(gameState == GameState.ENTER_MAIN_MENU) {
            createMainMenuObjects();
            musicHandler.start();
            gameState = GameState.MAIN_MENU;
            saveWriter.save();
        }

        if(gameState == GameState.FIRST_ENTER_MAIN_MENU) {
            createMainMenuObjects();
            gameState = GameState.MAIN_MENU;
        }

        if(gameState == GameState.MAIN_MENU) {
            mainMenu.tick();
            playerAttributeHandler.tick();
        }

        if(gameState == GameState.ENTER_WON_MENU) {
            createWonMenuObjects();
            musicHandler.stop();
            gameState = GameState.WON_MENU;
            apiThread = new Thread(() -> api.achieveTrophy(253101));
            apiThread.start();
        }

        if(gameState == GameState.WON_MENU) {
            wonMenu.tick();
        }

        if(gameState == GameState.ENTER_LOST_MENU) {
            createLostMenuObjects();
            musicHandler.stop();
            gameState = GameState.LOST_MENU;
        }

        if(gameState == GameState.LOST_MENU) {
            lostMenu.tick();
        }

        if(gameState == GameState.ENTER_SKILL_MENU) {
            createSkillMenuObjects();
            gameState = GameState.SKILL_MENU;
        }

        if(gameState == GameState.SKILL_MENU) {
            skillMenu.tick();
        }

        if(gameState == GameState.ENTER_CREDITS_MENU) {
            createCreditsMenuObjects();
            gameState = GameState.CREDITS_MENU;
        }

        if(gameState == GameState.CREDITS_MENU) {
            creditsMenu.tick();
        }

        if(gameState == GameState.ENTER_SKIN_MENU) {
            createSkinMenuObjects();
            gameState = GameState.SKIN_MENU;
        }

        if(gameState == GameState.SKIN_MENU) {
            skinMenu.tick();
        }

        if(gameState == GameState.ENTER_OPTIONS_MENU) {
            createOptionsMenuObjects();
            gameState = GameState.OPTIONS_MENU;
        }

        if(gameState == GameState.OPTIONS_MENU) {
            optionsMenu.tick();
        }

        if(gameState == GameState.STARTING_GAME) {
            createGameObjects();
            playerPowerUpHandler.resetPowerUps();
            gameState = GameState.PLAYING;
        }

        if(gameState == GameState.PLAYING) {
            player.tick();
            enemyWaveHandler.tick();
            powerUpHandler.tick();
            gameTimer.tick();
            gameMenu.tick();
        }
    }

    public KeyboardInputs getKeyboardInputs() {
        return keyboardInputs;
    }

    public MouseInputs getMouseInputs() {
        return mouseInputs;
    }

    public enum GameState {
        PLAYING,
        WON_MENU,
        LOST_MENU,
        MAIN_MENU,
        STARTING_GAME,
        ENTER_MAIN_MENU,
        FIRST_ENTER_MAIN_MENU,
        ENTER_WON_MENU,
        ENTER_LOST_MENU,
        ENTER_SKILL_MENU,
        SKILL_MENU,
        CREDITS_MENU,
        ENTER_CREDITS_MENU,
        SKIN_MENU,
        ENTER_SKIN_MENU,
        ENTER_OPTIONS_MENU,
        OPTIONS_MENU
    }
}

package de.lulkas_.stellarstrikers;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.FPSAnimator;
import de.lulkas_.stellarstrikers.rendering.shader.EventListener;
import org.gamejolt.GameJoltAPI;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    public GameObjectHandler gameObjectHandler;
    public GLJPanel shaderPanel;
    public Dimension size;
    public GLProfile profile;
    public final int FPS_START = 120;

    public GameWindow(GameJoltAPI api) {
        GLProfile.initSingleton();
        profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        gameObjectHandler = new GameObjectHandler(api);
        gameObjectHandler.setBounds(0, 0, 1000, 1000);
        gameObjectHandler.setOpaque(false);
        gameObjectHandler.setFocusable(true);
        gameObjectHandler.requestFocusInWindow();

        shaderPanel = new GLJPanel(capabilities);
        shaderPanel.addGLEventListener(new EventListener(gameObjectHandler));

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        layeredPane.add(shaderPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(gameObjectHandler, JLayeredPane.PALETTE_LAYER);

        this.setSize(1280, 720);
        this.getContentPane().add(layeredPane);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Stellar Strikers");
        this.setIconImage(Main.icon);

        Animator animator = new Animator(shaderPanel);
        animator.start();

        this.setVisible(true);
    }

    public void tick() {
        size = this.getSize();
        gameObjectHandler.setSize(size);
        shaderPanel.setSize(size);
    }
}

package de.lulkas_.stellarstrikers;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import de.lulkas_.stellarstrikers.shader.BackgroundShaderPanel;
import org.gamejolt.GameJoltAPI;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    public GamePanel gamePanel;
    public GLJPanel shaderPanel;
    public Dimension size;

    public GameWindow(GameJoltAPI api) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel = new GamePanel(api);
        gamePanel.setBounds(0, 0, 1000, 1000);
        gamePanel.setOpaque(false);
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();

        GLProfile.initSingleton();
        GLProfile profile = GLProfile.get(GLProfile.GL2ES1);
        GLCapabilities capabilities = new GLCapabilities(profile);
        shaderPanel = new GLJPanel(capabilities);
        shaderPanel.addGLEventListener(new BackgroundShaderPanel("background.frag", "background.vert", gamePanel));
        shaderPanel.setBounds(0, 0, 960, 960);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        layeredPane.add(shaderPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(gamePanel, JLayeredPane.PALETTE_LAYER);

        this.setSize(1280, 720);
        this.getContentPane().add(layeredPane);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setTitle("Stellar Strikers");
        this.setIconImage(Main.icon);
        this.setVisible(true);
    }

    public void tick() {
        size = this.getSize();
        gamePanel.setSize(size);
        shaderPanel.setSize(size);
    }
}

package de.lulkas_.stellarstrikers.shader;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import de.lulkas_.stellarstrikers.GamePanel;
import de.lulkas_.stellarstrikers.Main;
import de.lulkas_.stellarstrikers.level.enemys.EnemyWaveHandler;

import java.io.InputStream;
import java.util.Scanner;

public class BackgroundShaderPanel implements GLEventListener {
    private final String fragmentCode;
    private final String vertexCode;
    private int shaderProgram;
    private long startTime;
    private final GamePanel gamePanel;

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        int vertexShader = gl.glCreateShader(GL2.GL_VERTEX_SHADER);
        int fragmentShader = gl.glCreateShader(GL2.GL_FRAGMENT_SHADER);

        gl.glShaderSource(vertexShader, 1, new String[]{vertexCode}, null);
        gl.glShaderSource(fragmentShader, 1, new String[]{fragmentCode}, null);

        gl.glCompileShader(vertexShader);
        gl.glCompileShader(fragmentShader);

        shaderProgram = gl.glCreateProgram();
        gl.glAttachShader(shaderProgram, vertexShader);
        gl.glAttachShader(shaderProgram, fragmentShader);
        gl.glLinkProgram(shaderProgram);
        gl.glUseProgram(shaderProgram);

        int[] linkStatus = new int[1];
        gl.glGetProgramiv(shaderProgram, GL2.GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] == 0) {
            System.err.println("Fehler beim Verlinken des Shader-Programms");
            System.exit(1);
        }

        startTime = System.currentTimeMillis();
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

        float elapsedTime = (System.currentTimeMillis() - startTime) / 1000f;
        int timeLocation = gl.glGetUniformLocation(shaderProgram, "u_time");
        gl.glUniform1f(timeLocation, elapsedTime);

        int spawnTicksLocation = gl.glGetUniformLocation(shaderProgram, "spawnTicks");
        if(gamePanel.enemyWaveHandler != null && gamePanel.gameState == GamePanel.GameState.PLAYING) {
            if(((int) (gamePanel.enemyWaveHandler.wave / 5)) * 5 == gamePanel.enemyWaveHandler.wave) {
                if(gamePanel.enemyWaveHandler.waveState == EnemyWaveHandler.WaveState.SPAWNING) {
                    gl.glUniform1i(spawnTicksLocation, gamePanel.enemyWaveHandler.spawningTicks);
                    System.out.println(gamePanel.enemyWaveHandler.spawningTicks);
                } else {
                    gl.glUniform1i(spawnTicksLocation, 0);
                    System.out.println(0);
                }
            } else if (((int) ((gamePanel.enemyWaveHandler.wave - 1) / 5)) * 5 == gamePanel.enemyWaveHandler.wave - 1 && gamePanel.enemyWaveHandler.wave != 1) {
                if(gamePanel.enemyWaveHandler.waveState == EnemyWaveHandler.WaveState.SPAWNING) {
                    gl.glUniform1i(spawnTicksLocation, 120 - gamePanel.enemyWaveHandler.spawningTicks);
                    System.out.println(gamePanel.enemyWaveHandler.spawningTicks);
                } else {
                    gl.glUniform1i(spawnTicksLocation, 120);
                    System.out.println(120);
                }
            } else  {
                gl.glUniform1i(spawnTicksLocation, 120);
                System.out.println(120);
            }
        } else {
            gl.glUniform1i(spawnTicksLocation, 120);
            System.out.println(120);
        }

        int resolutionLocation = gl.glGetUniformLocation(shaderProgram, "resolution");
        gl.glUniform2f(resolutionLocation, Main.game.window.shaderPanel.getWidth(), Main.game.window.shaderPanel.getWidth());

        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glVertex2f(1f, 1f);
        gl.glVertex2f(1f, -1f);
        gl.glVertex2f(-1f, 1f);
        gl.glEnd();
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glVertex2f(-1f, -1f);
        gl.glVertex2f(1f, -1f);
        gl.glVertex2f(-1f, 1f);
        gl.glEnd();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, width, height);
    }

    public BackgroundShaderPanel(String fragmentShaderName, String vertexShaderName, GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        InputStream inputStream = getClass().getResourceAsStream("/shader/" + fragmentShaderName);
        assert inputStream != null;
        Scanner scanner = new Scanner(inputStream);
        StringBuilder code = new StringBuilder();
        while(scanner.hasNext()) {
            code.append(scanner.nextLine());
            code.append(System.lineSeparator());
        }
        this.fragmentCode = code.toString();

        inputStream = getClass().getResourceAsStream("/shader/" + vertexShaderName);
        assert inputStream != null;
        scanner = new Scanner(inputStream);
        code = new StringBuilder();
        while(scanner.hasNext()) {
            code.append(scanner.nextLine());
            code.append(System.lineSeparator());
        }
        this.vertexCode = code.toString();
    }
}

package de.lulkas_.stellarstrikers.rendering.shader;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import de.lulkas_.stellarstrikers.GameObjectHandler;
import de.lulkas_.stellarstrikers.Main;
import de.lulkas_.stellarstrikers.rendering.AtlasAssembler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.FloatBuffer;

public class EventListener implements GLEventListener {
    private final GameObjectHandler gameObjectHandler;
    private boolean initialized = false;
    private int shaderProgram;
    private int[] vao;
    private int[] vbo;
    private float[] vertices = new float[]{
            1f,  1f, 0f,
            1f, -1f, 0f,
            -1f, -1f, 0f,
            -1f,  1f, 0f,
    };
    private long lastPrint = -1;
    private int frames = 0;
    private int fps = 0;

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glClearColor(0, 0, 0, 1);

        try {
            if(Main.GENERATE_RESOURCES) {
                shaderProgram = ShaderHandler.createShaderProgram(gl, "assets/shader/main.vert", "assets/shader/main.frag", AtlasAssembler.assemble(), "assets/shader/");
            } else {
                shaderProgram = ShaderHandler.createShaderProgram(gl, "generated/shader/main.vert", "generated/shader/main.frag", AtlasAssembler.assemble(), "");
            }
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        vao = new int[1];
        gl.glGenVertexArrays(1, vao, 0);
        gl.glBindVertexArray(vao[0]);

        vbo = new int[1];
        gl.glGenBuffers(1, vbo, 0);

        VertexAttributeHandler.addVertexAttribute(3, GL.GL_FLOAT, false);

        UniformHandler.addUniforms(gameObjectHandler);

        TextureHandler.addTexture(gl, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, "/generated/atlas.png");
        TextureHandler.setUniforms(gl, shaderProgram);

        initialized = true;
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        if(initialized) {
            GL2 gl = drawable.getGL().getGL2();
            gl.glClear(GL.GL_COLOR_BUFFER_BIT);

            gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo[0]);
            gl.glBufferData(GL.GL_ARRAY_BUFFER, (long) vertices.length * Float.BYTES, FloatBuffer.wrap(vertices), GL.GL_STATIC_DRAW);

            VertexAttributeHandler.setAttributes(gl);

            TextureHandler.bindTextures(gl);

            gl.glUseProgram(shaderProgram);
            UniformHandler.setUniforms(gl, shaderProgram);
            gl.glDrawArrays(GL2.GL_QUADS, 0, 4);

            if(lastPrint == -1) {
                lastPrint = System.nanoTime();
            } else {
                long now = System.nanoTime();
                long elapsed = now - lastPrint;
                frames++;
                if(elapsed >= 500_000_000L) {
                    fps = ((int) (frames / (elapsed / 1_000_000_000.0)));
                    frames = 0;
                    lastPrint = now;
                }
            }
        }
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

    public EventListener(GameObjectHandler gameObjectHandler) {
        this.gameObjectHandler = gameObjectHandler;
    }

    public int getFps() {
        return fps;
    }
}

package de.lulkas_.stellarstrikers.rendering.shader;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
import de.lulkas_.stellarstrikers.Main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextureHandler {
    private static List<int[]> textures = new ArrayList<>();

    public static void addTexture(GL2 gl, int sourceFormat, int sourceType, String filepath) {
        int[] texture = new int[1];

        TextureData textureData;

        try {
            BufferedImage image;
            if(Main.GENERATE_RESOURCES) {
                image = ImageIO.read(new File(TextureHandler.class.getResource(filepath).getPath().replace("target/classes", "src/main/resources")));
            } else {
                image = ImageIO.read(new File("atlas.png"));
            }
            textureData = AWTTextureIO.newTextureData(Main.gameLoop.window.profile, image, true);
        } catch (IOException e) {
            throw new RuntimeException("Could not load image file");
        }

        gl.glGenTextures(1, texture, 0);
        gl.glBindTexture(GL.GL_TEXTURE_2D, texture[0]);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
        gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, textureData.getWidth(), textureData.getHeight(), 0, sourceFormat, sourceType, textureData.getBuffer());
        gl.glGenerateMipmap(GL.GL_TEXTURE_2D);

        textures.add(texture);
    }

    public static void setUniforms(GL2 gl, int shaderProgram) {
        gl.glUseProgram(shaderProgram);
        for(int i = 0; i < textures.size(); i++) {
            gl.glUniform1i(gl.glGetUniformLocation(shaderProgram, "tex" + i), i);
        }
    }

    public static void bindTextures(GL2 gl) {
        for(int i = 0; i < textures.size(); i++) {
            gl.glActiveTexture(GL.GL_TEXTURE0 + i);
            gl.glBindTexture(GL.GL_TEXTURE_2D, textures.get(i)[0]);
        }
    }

    public static int[] getTexture(int index) {
        return textures.get(index);
    }
}

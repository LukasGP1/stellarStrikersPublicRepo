package de.lulkas_.stellarstrikers.rendering.shader;

import com.jogamp.opengl.GL2;

import java.util.ArrayList;
import java.util.List;

public class VertexAttributeHandler {
    private static List<VertexAttribute> attributes = new ArrayList<>();

    public static void addVertexAttribute(int size, int type, boolean normalized) {
        VertexAttribute attribute = new VertexAttribute(size, type, normalized, getBytes());
        attributes.add(attribute);
    }

    public static void setAttributes(GL2 gl) {
        for(int i = 0; i < attributes.size(); i++) {
            VertexAttribute attribute = attributes.get(i);
            gl.glVertexAttribPointer(i, attribute.size(), attribute.type(), attribute.normalized(), getBytes(), attribute.pointerBufferOffset());
            gl.glEnableVertexAttribArray(i);
        }
    }

    private static int getBytes() {
        int toReturn = 0;
        for(VertexAttribute attribute : attributes) {
            toReturn += attribute.getBytes();
        }
        return toReturn;
    }
}

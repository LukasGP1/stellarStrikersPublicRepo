package de.lulkas_.stellarstrikers.rendering.shader;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import de.lulkas_.stellarstrikers.Main;
import de.lulkas_.stellarstrikers.rendering.AtlasTexture;
import de.lulkas_.stellarstrikers.rendering.ShaderCompileException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ShaderHandler {
    public static int createShaderProgram(GL2 gl, String vertexPath, String fragmentPath, List<AtlasTexture> textures, String directoryPath) throws IOException, URISyntaxException {
        int shaderProgram = gl.glCreateProgram();
        int vertexShader = createShader(gl, vertexPath, GL2.GL_VERTEX_SHADER, textures, directoryPath);
        int fragmentShader = createShader(gl, fragmentPath, GL2.GL_FRAGMENT_SHADER, textures, directoryPath);
        gl.glAttachShader(shaderProgram, vertexShader);
        gl.glAttachShader(shaderProgram, fragmentShader);
        gl.glLinkProgram(shaderProgram);

        int[] linkStatus = new int[1];
        gl.glGetProgramiv(shaderProgram, GL2.GL_LINK_STATUS, linkStatus, 0);
        if(linkStatus[0] == GL.GL_FALSE) {
            ByteBuffer log = ByteBuffer.allocate(1024);
            gl.glGetProgramInfoLog(shaderProgram, 1024, null, log);
            System.err.println("Linking of shader program with vertex shader " + vertexPath + " and fragment shader " + fragmentPath + " failed: " + new String(log.array()));
        }

        return shaderProgram;
    }

    private static int createShader(GL2 gl, String filePath, int type, List<AtlasTexture> textures, String directoryPath) throws IOException, URISyntaxException {
        String source = getShaderFileSourceCode(filePath);

        String sourceCode;
        if(Main.GENERATE_RESOURCES) {
            sourceCode = addIncludes(source, directoryPath);
            sourceCode = compileAtlasAccess(sourceCode, textures);
            String path = "/" + filePath.replace("assets", "generated");
            String absPath = ShaderHandler.class.getResource(path).toURI().getPath();
            absPath = absPath.replace("target/classes", "src/main/resources");
            File file = new File(absPath);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(sourceCode);
            fileWriter.close();
        } else {
            InputStream stream = ShaderHandler.class.getResourceAsStream("/" + filePath);
            sourceCode = new String(stream.readAllBytes());
        }


        int shader = gl.glCreateShader(type);
        gl.glShaderSource(shader, 1, new String[]{sourceCode}, null);
        gl.glCompileShader(shader);

        int[] compileStatus = new int[1];
        gl.glGetShaderiv(shader, GL2.GL_COMPILE_STATUS, compileStatus, 0);
        if(compileStatus[0] == GL.GL_FALSE) {
            ByteBuffer log = ByteBuffer.allocate(1024);
            gl.glGetShaderInfoLog(shader, 1024, null, log);
            System.err.println("Compilation of shader with filepath " + filePath + " failed: " + new String(log.array()));
        }

        return shader;
    }

    private static String compileAtlasAccess(String code, List<AtlasTexture> textures) {
        Scanner codeScanner = new Scanner(code);
        Scanner lineScanner;
        String line;
        while(codeScanner.hasNext()) {
            line = codeScanner.nextLine();
            if(line.startsWith("#atlas(\"")){
                lineScanner = new Scanner(line);
                lineScanner.useDelimiter("");
                for(int i = 0; i < 8; i++) {
                    lineScanner.next();
                }

                StringBuilder pathBuilder = new StringBuilder();
                String token = "";
                while(!Objects.equals(token, "\"")) {
                    pathBuilder.append(token);
                    token = lineScanner.next();
                }
                String path = pathBuilder.toString().replace("target\\classes", "src\\main\\resources");
                if(!Objects.equals(lineScanner.next(), ",")) {
                    throw new ShaderCompileException();
                }

                StringBuilder texCoord = new StringBuilder();
                token = "";
                lineScanner.next();
                while(!Objects.equals(token, "\"")) {
                    texCoord.append(token);
                    token = lineScanner.next();
                }
                if(!Objects.equals(lineScanner.next(), ",")) {
                    throw new ShaderCompileException();
                }

                StringBuilder before = new StringBuilder();
                token = "";
                lineScanner.next();
                while(!Objects.equals(token, "\"")) {
                    before.append(token);
                    token = lineScanner.next();
                }
                if(!Objects.equals(lineScanner.next(), ")")) {
                    throw new ShaderCompileException();
                }

                AtlasTexture texture = null;
                for(AtlasTexture textureInList : textures) {
                    if(Objects.equals(textureInList.path(), path)) {
                        texture = textureInList;
                        break;
                    }
                }
                if(texture == null)  {
                    throw new ShaderCompileException(path);
                }

                StringBuilder toReplace = new StringBuilder();
                toReplace.append(before);
                toReplace.append("textureFromAtlas(");
                toReplace.append(texCoord);
                toReplace.append(", vec2(");
                toReplace.append(texture.x());
                toReplace.append(",");
                toReplace.append(texture.y());
                toReplace.append("), vec2(");
                toReplace.append(texture.width());
                toReplace.append(",");
                toReplace.append(texture.height());
                toReplace.append("));");

                code = code.replace(line, toReplace);
            }
        }

        return code;
    }

    private static String getShaderFileSourceCode(String filePath) {
        try {
            InputStream stream = ShaderHandler.class.getResourceAsStream("/" + filePath);
            return new String(stream.readAllBytes());
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't read file at path: " + filePath);
        }
    }

    private static String addIncludes(String initialSource, String shaderDirectoryPath) {
        Scanner scanner = new Scanner(initialSource);
        String toReturn = initialSource;
        while(scanner.hasNext()) {
            String line = scanner.nextLine();
            if(line.startsWith("#include \"")) {
                String mentionedSourceName = line.substring(10, line.length() - 1);
                String mentionedSourcePath = shaderDirectoryPath + mentionedSourceName;
                String mentionedSource = addIncludes(getShaderFileSourceCode(mentionedSourcePath), shaderDirectoryPath);
                toReturn = toReturn.replace(line, mentionedSource);
            }
        }
        return toReturn;
    }
}

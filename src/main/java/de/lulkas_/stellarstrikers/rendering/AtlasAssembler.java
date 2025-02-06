package de.lulkas_.stellarstrikers.rendering;

import de.lulkas_.stellarstrikers.Main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class AtlasAssembler {
    private static List<BufferedImage> images = new ArrayList<>();
    private static List<String> paths = new ArrayList<>();
    private static List<AtlasTexture> textures = new ArrayList<>();
    public static final int ATLAS_WIDTH = 10000;
    public static final int ATLAS_HEIGHT = 10000;
    public static final int ATLAS_ROW_HEIGHT = 1000;

    public static List<AtlasTexture> assemble() throws IOException, URISyntaxException {
        if(Main.GENERATE_RESOURCES) {
            String folderPath = AtlasAssembler.class.getResource("/assets/textures").toURI().getPath();
            folderPath = folderPath.replace("target/classes", "src/main/resources");
            findPngFiles(new File(folderPath), paths);
            for(String path : paths) {
                images.add(importImage(path));
            }
            BufferedImage atlas = new BufferedImage(ATLAS_WIDTH, ATLAS_HEIGHT, BufferedImage.TYPE_INT_ARGB);

            atlas = writeImages(atlas, images);

            String path = AtlasAssembler.class.getResource("/generated/atlas.png").toURI().getPath();
            path = path.replace("target/classes", "src/main/resources");
            File atlasFile = new File(path);
            ImageIO.write(atlas, "png", atlasFile);

            return textures;
        } else {
            return new ArrayList<>();
        }
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        findPngFiles(new File(AtlasAssembler.class.getResource("/assets/textures").toURI()), paths);
        for(String path : paths) {
            images.add(importImage(path));
        }
        BufferedImage atlas = new BufferedImage(ATLAS_WIDTH, ATLAS_HEIGHT, BufferedImage.TYPE_INT_ARGB);

        atlas = writeImages(atlas, images);

        String path = AtlasAssembler.class.getResource("/generated/atlas.png").toURI().getPath();
        path = path.replace("target/classes", "src/main/resources");
        File atlasFile = new File(path);
        ImageIO.write(atlas, "png", atlasFile);
    }

    private static BufferedImage writeImages(BufferedImage atlas, List<BufferedImage> images) {
        int x = 0;
        int y = 0;
        for(BufferedImage image : images) {
            if(x + image.getWidth() > ATLAS_WIDTH) {
                x = 0;
                y += ATLAS_ROW_HEIGHT;
            }
            if(y + image.getHeight() > ATLAS_HEIGHT) {
                throw new AtlasSizeException();
            }
            AtlasTexture texture = new AtlasTexture(paths.get(images.indexOf(image)), x / ((float) ATLAS_WIDTH), y / ((float) ATLAS_HEIGHT), image.getWidth() / ((float) ATLAS_WIDTH), image.getHeight() / ((float) ATLAS_HEIGHT));
            textures.add(texture);
            System.out.println(texture);
            atlas = writeImage(atlas, image, x, y);
            x += image.getWidth();
        }
        return atlas;
    }

    private static BufferedImage writeImage(BufferedImage atlas, BufferedImage toWrite, int startX, int startY) throws ArrayIndexOutOfBoundsException {
        for(int x = 0; x < toWrite.getWidth(); x++) {
            for(int y = 0; y < toWrite.getHeight(); y++) {
                atlas.setRGB(x + startX, y + startY, toWrite.getRGB(x, y));
            }
        }
        return atlas;
    }

    public static void findPngFiles(File folder, List<String> pngPaths) {
        if (folder.isDirectory()) {
            for (File file : folder.listFiles()) {
                if (file.isDirectory()) {
                    findPngFiles(file, pngPaths);
                } else if (file.isFile() && file.getName().toLowerCase().endsWith(".png")) {
                    pngPaths.add(file.getAbsolutePath());
                }
            }
        }
    }

    private static BufferedImage importImage(String path) throws FileNotFoundException {
        InputStream is = new FileInputStream(new File(path));
        try {
            return ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

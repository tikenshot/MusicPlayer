package net.tmkspace.musicplayer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import static net.tmkspace.musicplayer.MusicPlayer.getInstance;

public class Util {

    public static int[][] getIconAsArray(String fileName) {
        try {
            URL resource = getInstance().getClass().getClassLoader().getResource(fileName);

            BufferedImage bufferedImage = ImageIO.read(resource.openStream());
            if (bufferedImage.getWidth() != 16 && bufferedImage.getHeight() != 16) return null;
            int[][] image = new int[16][16];
            for (int x = 0; x < bufferedImage.getWidth(); x++) {
                for (int y = 0; y < bufferedImage.getHeight(); y++) {
                    image[x][y] = bufferedImage.getRGB(x, y);
                }
            }
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

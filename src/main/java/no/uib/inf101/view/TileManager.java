package no.uib.inf101.view;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class TileManager {
    private BufferedImage tileImage;

    public TileManager() {
        loadTileImage();
    }

    private void loadTileImage() {
        try (InputStream is = getClass().getResourceAsStream("/tiles/stone.png")) {
            if (is == null) {
                throw new IllegalArgumentException("Tile image not found!");
            }
            tileImage = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load tile image", e);
        }
    }

    public void drawTiles(Graphics g, int width, int height) {
        if (tileImage != null) {
            int tileWidth = tileImage.getWidth();
            int tileHeight = tileImage.getHeight();
            for (int y = 0; y < height; y += tileHeight) {
                for (int x = 0; x < width; x += tileWidth) {
                    g.drawImage(tileImage, x, y, null);
                }
            }
        }
    }
}

package game.view;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * A class for managing the tile image used for the background.
 * The tile image is repeated to fill the entire background.
 * The tile image is loaded from the resources folder.
 */
public class TileManager {

    private BufferedImage tileImage;

    /**
     * Constructor for the TileManager class. Loads the tile image.
     */
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

    /**
     * Draws the tile image to fill the entire background.
     * Used in the paintComponent method in the GameView class
     * to draw the background of the game.
     * 
     * @param g The graphics object to draw on
     * @param width The width of the area to draw on
     * @param height The height of the area to draw on
     */
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

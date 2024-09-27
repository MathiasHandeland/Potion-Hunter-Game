package game.model;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;

/**
 * Class representing a potion in the game.
 */
public class Potion implements IPotion {
    
    private int x, y; 
    private int solidArea = 32; 
    private BufferedImage potion_sprite;
    
    /**
     * Constructor for the potion.
     * @param x x-coordinate of the potion
     * @param y y-coordinate of the potion
     */
    public Potion(int x, int y) {
        this.x = x;
        this.y = y;
        loadSprite();
    }
    
    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getSolidArea() {
        return solidArea;
    }

    @Override
    public void removePotion() {
        x = -1;
        y = -1;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, solidArea, solidArea);
    }

    @Override
    public BufferedImage getCurrentSprite() {
        return potion_sprite;
    }

    private void loadSprite() {
        try {
            potion_sprite = ImageIO.read(getClass().getResource("/potion/purple_potion.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }  
    }
}

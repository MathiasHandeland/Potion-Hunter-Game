package no.uib.inf101.model;

import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Class representing a wizard character in the game.
 * The wizard can move around the game board and has a sprite for each direction.
 */
public class Wizard implements IWizard {
    private int x, y;
    private int speed = 4;
    private String direction = "down"; // Default direction
    private BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    private BufferedImage currentSprite;
    private int spriteCounter = 0; // The sprite counter is used to change sprite every 10 frames, initially set to 0 so that the sprite is changed immediately
    private int spriteNum = 1; // The sprite number is used to alternate between two sprites for each direction to create an animation effect
    private Rectangle solidArea; // Represents the position and size of the wizard on the game board

    private final int boardWidth, boardHeight; // Size of the game board, should be the same as the size of the game window and not change during the game

    private int wizardLives;

    /**
     * Constructor for the wizard.
     * The wizard is placed on the game board at the given x and y coordinates.
     * @param startX x-coordinate of the wizard
     * @param startY y-coordinate of the wizard
     * @param boardWidth width of the game board
     * @param boardHeight height of the game board
     */
    public Wizard(int startX, int startY, int boardWidth, int boardHeight) {
        this.x = startX; 
        this.y = startY;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;

        this.wizardLives = 3; // The wizard starts with 3 lives

        solidArea = new Rectangle(0, 0, 48, 48); // Size of the wizard
        loadSprites(); // Load the sprites
        currentSprite = down1; // Default sprite
    }

    // reset wizard lives
    public void resetWizardLives() {
        wizardLives = 3;
    }

    // legg inn i IWizard
    public int getWizardLives() {
        return wizardLives;
    }

    public void decreaseWizardLives() {
        wizardLives -= 1;
    }

    @Override
    public BufferedImage getCurrentSprite() {
        return currentSprite;
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
    public Rectangle getSolidArea() {
        return solidArea;
    }

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, solidArea.width, solidArea.height);
    }
    
    @Override
    public void loadSprites() {
        
        try {
            up1 = ImageIO.read(getClass().getResource("/player/wizard_up_1.png"));
            up2 = ImageIO.read(getClass().getResource("/player/wizard_up_2.png"));
            down1 = ImageIO.read(getClass().getResource("/player/wizard_down_1.png"));
            down2 = ImageIO.read(getClass().getResource("/player/wizard_down_2.png"));
            left1 = ImageIO.read(getClass().getResource("/player/wizard_left_1.png"));
            left2 = ImageIO.read(getClass().getResource("/player/wizard_left_2.png"));
            right1 = ImageIO.read(getClass().getResource("/player/wizard_right_1.png"));
            right2 = ImageIO.read(getClass().getResource("/player/wizard_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }  
    }  

    @Override
    public void update(boolean upPressed, boolean downPressed, boolean leftPressed, boolean rightPressed) {
        
        if (upPressed || downPressed || leftPressed || rightPressed) {
            if (upPressed) {
                direction = "up";
                y -= speed; 
            } else if (downPressed) {
                direction = "down";
                y += speed;
            } else if (leftPressed) {
                direction = "left";
                x -= speed;
            } else if (rightPressed) {
                direction = "right";
                x += speed;
            }

            stopFromMovingOutside();

            // Update sprite animation, by changing sprite every 10 frames
            spriteCounter++; 
            if (spriteCounter > 10) { 
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }

            // Update current sprite based on direction and sprite number
            switch (direction) {
                case "up":
                    currentSprite = (spriteNum == 1) ? up1 : up2;
                    break;
                case "down":
                    currentSprite = (spriteNum == 1) ? down1 : down2;
                    break;
                case "left":
                    currentSprite = (spriteNum == 1) ? left1 : left2;
                    break;
                case "right":
                    currentSprite = (spriteNum == 1) ? right1 : right2;
                    break;
            }
        }
    }

    // Helper method to stop the wizard from moving outside the game board
    private void stopFromMovingOutside() {
        if (x < 0) x = 0;
        if (x > boardWidth - solidArea.width) x = boardWidth - solidArea.width;
        if (y < 0) y = 0;
        if (y > boardHeight - solidArea.height) y = boardHeight - solidArea.height;
    }
}
package no.uib.inf101.model;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * Class representing an enemy character in the game.
 * The enemy moves around the game board and tries to catch the wizard.
 */
public class Enemy implements IEnemy {
    
    private int x, y;
    private BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    private BufferedImage currentSprite;
    private Rectangle solidArea; 
    private int speed = 1; 
    private int spriteCounter = 0; // Counter used in sprite animation so that it changes every 10 frames
    private int spriteNum = 1;     // Alternate between two sprites for animation. Initial sprite is 1
    private String direction = "down"; // Default direction. The enemy starts moving down

    private boolean isPaused = false; // Indicates if the enemy is paused
    private long pauseStartTime; // Represents the time when the enemy was paused
    private final long PAUSE_DURATION = 400; // Duration to pause the enemy in milliseconds

    private int boardWidth;
    private int boardHeight;

    /**
     * Constructor for the enemy character
     * The enemy character is created at the specified position
     * @param startX 
     * @param startY
     * @param boardWidth
     * @param boardHeight
     */
    public Enemy(int startX, int startY, int boardWidth, int boardHeight) {
        this.x = startX;
        this.y = startY;
        this.boardWidth = boardWidth; 
        this.boardHeight = boardHeight; 
        this.solidArea = new Rectangle(0, 0, 48, 48); // Set enemy solidArea to 48x48
        loadSprites();
        this.currentSprite = down1; // Default sprite is down1
    }

    // The sprites for the enemy character are gathered from the youtuber RyiSnow at: https://drive.google.com/drive/folders/1UThk24Kl7zb0w0bHPTdcuy2iZnqPFa4a
    private void loadSprites() {
        try {
            up1 = ImageIO.read(getClass().getResource("/npc/oldman_up_1.png"));
            up2 = ImageIO.read(getClass().getResource("/npc/oldman_up_2.png"));
            down1 = ImageIO.read(getClass().getResource("/npc/oldman_down_1.png"));
            down2 = ImageIO.read(getClass().getResource("/npc/oldman_down_2.png"));
            left1 = ImageIO.read(getClass().getResource("/npc/oldman_left_1.png"));
            left2 = ImageIO.read(getClass().getResource("/npc/oldman_left_2.png"));
            right1 = ImageIO.read(getClass().getResource("/npc/oldman_right_1.png"));
            right2 = ImageIO.read(getClass().getResource("/npc/oldman_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(int wizardX, int wizardY) {
        
        if (isPaused) {
            // Check if the pause duration has elapsed
            if (System.currentTimeMillis() - pauseStartTime > PAUSE_DURATION) {
                isPaused = false; // Resume chasing the wizard
            } else {
                return; // Don't update position if still paused
            }
        }
        // Normal chasing logic
        if (x < wizardX) {
            x += speed;
            direction = "right"; // Moving right
        }
        if (x > wizardX) {
            x -= speed;
            direction = "left";  // Moving left
        }
        if (y < wizardY) {
            y += speed;
            direction = "down";  // Moving down
        }
        if (y > wizardY) {
            y -= speed;
            direction = "up";    // Moving up
        }

        // Sprite animation logic
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

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(int i) {
        this.speed = i;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, solidArea.width, solidArea.height);
    }

    @Override
    public BufferedImage getCurrentSprite() {
        return currentSprite;
    }

    @Override
    public void setPaused(boolean paused) {
        this.isPaused = paused;
        if (paused) {
            this.pauseStartTime = System.currentTimeMillis(); // Record the start time
        }
    }

    @Override
    public void reverseDirection() {
        switch (direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
        
        // Move the enemy away from the wizard immediately
        switch (direction) {
            case "up":
                y -= solidArea.height;
                break;
            case "down":
                y += solidArea.height;
                break;
            case "left":
                x -= solidArea.width;
                break;
            case "right":
                x += solidArea.width;
                break;
        }
    
        stopFromMovingOutside(); // Ensure it stays within bounds
    }
    
    private void stopFromMovingOutside() {
        if (x < 0) x = 0;
        if (x > boardWidth - solidArea.width) x = boardWidth - solidArea.width;
        if (y < 0) y = 0;
        if (y > boardHeight - solidArea.height) y = boardHeight - solidArea.height;
    }
}

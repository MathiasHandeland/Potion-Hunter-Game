package no.uib.inf101.model;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Enemy {
    private int x, y;
    private BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    private BufferedImage currentSprite;
    private Rectangle solidArea;  // Size and bounds of the enemy
    private int speed = 1; // NPC speed
    private int spriteCounter = 0; // Counter for sprite animation
    private int spriteNum = 1;     // Alternate between two sprites for animation
    private String direction = "down"; // Default direction

    private boolean isPaused = false; // Flag to indicate if the enemy is paused
    private long pauseStartTime; // Start time for pause
    private final long PAUSE_DURATION = 200; // Duration to pause in milliseconds


    // Add these fields for the game board dimensions
    private int boardWidth;
    private int boardHeight;

    // Modify the constructor to take in boardWidth and boardHeight
    public Enemy(int startX, int startY, int boardWidth, int boardHeight) {
        this.x = startX;
        this.y = startY;
        this.boardWidth = boardWidth;  // Store the board width
        this.boardHeight = boardHeight;  // Store the board height
        this.solidArea = new Rectangle(0, 0, 48, 48); // Set enemy solidArea to 48x48
        loadSprites();
        this.currentSprite = down1; // Set the default sprite
    }

    // Load NPC sprites
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

    // Update enemy position and sprite based on wizard's position
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int i) {
        this.speed = i;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, solidArea.width, solidArea.height);
    }

    public BufferedImage getCurrentSprite() {
        return currentSprite;
    }

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
    
    
    // Helper method to prevent the enemy from going outside the game area
    private void stopFromMovingOutside() {
        if (x < 0) x = 0;
        if (x > boardWidth - solidArea.width) x = boardWidth - solidArea.width;
        if (y < 0) y = 0;
        if (y > boardHeight - solidArea.height) y = boardHeight - solidArea.height;
    }

    public void setPaused(boolean paused) {
        this.isPaused = paused;
        if (paused) {
            this.pauseStartTime = System.currentTimeMillis(); // Record the start time
        }
    }
}

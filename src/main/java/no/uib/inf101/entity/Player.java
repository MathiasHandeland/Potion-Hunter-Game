package no.uib.inf101.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import no.uib.inf101.main.GameController;
import no.uib.inf101.main.GamePanel;

public class Player extends Entity {

    GamePanel gamePanel;
    GameController controller;

    public Player(GamePanel gamePanel, GameController controller) {
        super(gamePanel);
        this.gamePanel = gamePanel;
        this.controller = controller;

        // TODO endre disse
        solidArea = new Rectangle();
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 48;
        solidArea.height = 48;

        setDefaultValues();
        getPlayerImage();

        loadCharacterImage();
    }

    public void setDefaultValues() {
        // set default player position in the middle of the screen
        x = 336;
        y = 240;
        speed = 4;
        direction = "down"; // default direction
    }

    public void getPlayerImage() {
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
    
    public void update() {

        if (controller.upPressed || controller.downPressed || controller.leftPressed || controller.rightPressed) {

            if (controller.upPressed == true) {
                direction = "up";
            }
            else if (controller.downPressed == true) {
                direction = "down";
            }
            else if (controller.leftPressed == true) {
                direction = "left";
            }
            else if (controller.rightPressed == true) {
                direction = "right";
            }

            // TODO Check for collision with game walls
            stopPlayerFromMovingOutsideScreen();

            // CHECK TILE COLLISION
            collisionOn = false;
            gamePanel.collisionChecker.checkTile(this);

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (collisionOn == false) {
                
                switch (direction) {
                case "up":
                    y -= speed;
                    break;
                case "down":
                    y += speed;
                    break;
                case "left":
                    x -= speed;
                    break;
                case "right":
                    x += speed;
                    break;
                }
            }
  
            spriteCounter++;
            if (spriteCounter > 10) { // player image changes every 10 frames
                if (spriteNum == 1) {
                    spriteNum = 2;
                }
                else if (spriteNum == 2){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }            
        }        
    }

    private void stopPlayerFromMovingOutsideScreen() {

        // stop player from moving outside the screen
        if (x < 0) {
            x = 0;
        }
        if (x > gamePanel.getWidth() - gamePanel.tileSize) {
            x = gamePanel.getWidth() - gamePanel.tileSize;
        }
        if (y < 0) {
            y = 0;
        }
        if (y > gamePanel.getHeight() - gamePanel.tileSize) {
            y = gamePanel.getHeight() - gamePanel.tileSize;
        }
    }

    public void loadCharacterImage() {

        try {

            character = ImageIO.read(getClass().getResource("/player2/character.png"));

            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        // draw the character
        g2.drawImage(character, 440, 194, gamePanel.tileSize, gamePanel.tileSize, null);

        
        // draw the wizard
        BufferedImage image = null; // this is the image that will be drawn, it is null by default

        switch (direction) {
        case "up":
            if (spriteNum == 1) {
                image = up1;
            }
            if (spriteNum == 2) {
                image = up2;
            }
            break;
        case "down":
            if (spriteNum == 1) {
                image = down1;
            }
            if (spriteNum == 2) {
                image = down2;
            }
            break;
        case "left":
            if (spriteNum == 1) {
                image = left1;
            }
            if (spriteNum == 2) {
                image = left2;
            }
            break;
        case "right":
            if (spriteNum == 1) {
                image = right1;
            }
            if (spriteNum == 2) {
                image = right2;
            }
            break;
        }
        g2.drawImage(image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}

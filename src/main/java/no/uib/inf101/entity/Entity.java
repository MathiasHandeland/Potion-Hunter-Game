package no.uib.inf101.entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import no.uib.inf101.main.GamePanel;

public class Entity {
    
    public int x, y;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea;
    public boolean collisionOn = false;

    // lagt inn
   public BufferedImage character;



    GamePanel gamePanel;

    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
   
}

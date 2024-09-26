package no.uib.inf101.model;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Interface representing a potion in the game.
 * Potions can be picked up by the wizard by moving overr the potions
 * The wizard will then gain a point which is added to the score 
 * The potions should be placed randomly on the game board
 * and disappear when picked up by the wizard.
 * The visual representation of a potion is the purple_potion.png
 * image in the resources folder.
 */
public interface IPotion {
    
    /**
     * Gets the x-coordinate of the potion.
     * Used in GameView to draw the potion on the game board.
     *
     * @return the x-coordinate of the potion.
     */
    public int getX();

    /**
     * Gets the y-coordinate of the potion.
     * Used in GameView to draw the potion on the game board.
     * 
     * @return the y-coordinate of the potion.
     */
    public int getY();

    /**
     * Gets the solid area of the potion used for collision detection.
     * Used in GameView to draw the potion on the game board.
     *
     * @return the solid area of the potion.
     */
    public int getSolidArea();

    /**
     * Gets the bounds of the potion.
     * The bounds are used for collision detection with the wizard.
     * Used in the GameModel class to check for collisions with the wizard.
     *
     * @return the bounds of the potion.
     */
    public Rectangle getBounds();

    /**
     * Gets the current sprite image of the potion.
     * The sprite image is the visual representation of the potion.
     * Used in GameView to draw the potion on the game board.
     *
     * @return the current sprite image of the potion.
     */
    public BufferedImage getCurrentSprite();
    
    /**
     * Removes the potion from the game board.
     * It removes the potion by setting the x and y coordinates to -1.
     * This will make the potion disappear from the game board because 
     * the game board will not draw objects with negative coordinates.
     * Used in the GameModel class to remove the potion when the wizard 
     * picks it up (in the checkPotionCollision method).
     */
    public void removePotion();
}

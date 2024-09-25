package no.uib.inf101.model;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Interface for the Enemy class
 * The Enemy class represents an enemy character in the game.
 * The enemy moves around the game board and tries to catch the wizard.
 */
public interface IEnemy {
    
    /**
     * Get the x position of the enemy
     * @return The x position of the enemy
     */
    int getX();

    /**
     * Get the y position of the enemy
     * @return The y position of the enemy
     */
    int getY();

    /**
     * Get the speed of the enemy
     * @return The speed of the enemy
     */
    int getSpeed();

    /**
     * Set the speed of the enemy
     * @param i The new speed of the enemy
     */
    void setSpeed(int i);

    /**
     * Get the bounding box of the enemy
     * The bounding box is used to check for collisions with other objects
     * @return The bounding box of the enemy
     */
    Rectangle getBounds();

    /**
     * Get the current sprite of the enemy
     * The sprite is the image that is drawn on the game board
     * Used to animate the enemy in the GameView class
     * @return The current sprite of the enemy
     */
    BufferedImage getCurrentSprite();

    /**
     * Reverse the direction of the enemy
     * The enemy will move in the opposite direction after this method is called
     */
    void reverseDirection();

    /**
     * Update the position of the enemy
     * The enemy will move towards the wizard and try to catch it
     * @param wizardX The x position of the wizard
     * @param wizardY The y position of the wizard
     */
    void update(int wizardX, int wizardY);
}

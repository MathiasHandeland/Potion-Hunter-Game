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
     * 
     * @return The x position of the enemy
     */
    public int getX();

    /**
     * Get the y position of the enemy
     * 
     * @return The y position of the enemy
     */
    public int getY();

    /**
     * Get the speed of the enemy
     * 
     * @return The speed of the enemy
     */
    public int getSpeed();

    /**
     * Set the speed of the enemy
     * 
     * @param newSpeed The new speed of the enemy
     */
    public void setSpeed(int newSpeed);

    /**
     * Get the bounding box of the enemy
     * The bounding box is used to check for collisions with other objects
     * 
     * @return The bounding box of the enemy
     */
    public Rectangle getBounds();

    /**
     * Get the current sprite of the enemy
     * The sprite is the image that is drawn on the game board
     * Used to animate the enemy movement in the GameView class
     * 
     * @return The current sprite of the enemy
     */
    public BufferedImage getCurrentSprite();

    /**
     * Reverse the direction of the enemy
     * The enemy will move in the opposite direction after this method is called
     * Used in the GameModel class to move the enemy away from the wizard
     * when a collision is detected to give the player a chance to escape
     */
    public void reverseDirection();

    /**
     * Update the position of the enemy
     * The enemy will move around the game board based on the wizard's position
     * to make it look like the enemy is chasing the wizard because the enemy
     * follows the wizard's movements
     * Used in the GameModel class to update the enemy's position every frame
     * based on the wizard's position and user input
     * 
     * @param wizardX The x position of the wizard
     * @param wizardY The y position of the wizard
     */
    public void update(int wizardX, int wizardY);

    /**
     * Set the paused state of the enemy
     * The enemy will stop moving when the game is paused
     * Used in the GameModel class to pause the enemy's movement
     * when a collision is detected or when the player pauses the game
     * 
     * @param paused The paused state of the enemy
     */
    public void setPaused(boolean paused);
}

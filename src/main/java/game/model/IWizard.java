package game.model;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Interface representing a wizard character in the game.
 * The interface defines the methods that a wizard character should have.
 * The wizard can move around the game board and has a sprite for each direction.
 */
public interface IWizard {

    /**
     * Gets the x-coordinate of the wizard.
     *
     * @return the x-coordinate of the wizard.
     */
    public int getX();

    /**
     * Gets the y-coordinate of the wizard.
     *
     * @return the y-coordinate of the wizard.
     */
    public int getY();

    /**
     * Gets the current sprite image of the wizard.
     * The sprite image depends on the direction the wizard is moving.
     * This method is used in the GameView class to draw the wizard.
     * It is called every time the screen is repainted so
     * that the wizard appears to be moving.
     *
     * @return the current sprite image of the wizard.
     */
    public BufferedImage getCurrentSprite();

    /**
     * Gets the solid area of the wizard. 
     * The solid area is a rectangle representing the position and size 
     * of the wizard on the game board. 
     * Used in GameView to draw the wizard with the correct size.
     *
     * @return the solid area of the wizard.
     */
    public Rectangle getSolidArea();

    /**
     * Sets the speed of the wizard.
     * The speed determines how fast the wizard moves on the game board.
     * Used in the updateSpeeds method in the GameModel class 
     * to update the speed of the wizard based on the score.
     *
     * @param speed the speed of the wizard.
     */
    public void setSpeed(int speed);

    /**
     * Gets the speed of the wizard.
     * The speed determines how fast the wizard moves on the game board.
     *
     * @return the speed of the wizard.
     */
    public int getSpeed();

    /**
     * Gets the bounding box of the wizard.
     * The bounding box is used for collision detection with other objects.
     *
     * @return the bounding box of the wizard.
     */
    public Rectangle getBounds();

    /**
     * Updates the position of the wizard based on the user input.
     * The wizard can move up, down, left, or right on the game board.
     * This method updates the sprite of the wizard based on the 
     * direction it is moving and changes sprite every 10 frames.
     * This method uses the helper method stopFromMovingOutside 
     * to prevent the wizard from moving outside the game board.
     * Used in the update method in the GameModel class to update the
     * position of the wizard based on the user input and in the GameController
     * class to handle the user input.
     *
     * @param upPressed    true if the up key is pressed, false otherwise.
     * @param downPressed  true if the down key is pressed, false otherwise.
     * @param leftPressed  true if the left key is pressed, false otherwise.
     * @param rightPressed true if the right key is pressed, false otherwise.
     */
    public void update(boolean upPressed, boolean downPressed, boolean leftPressed, boolean rightPressed);

    /**
     * Gets the amount of lives the wizard has.
     * @return the amount of lives the wizard has.
     */
    public int getWizardLives();

    /**
     * Resets the amount of lives the wizard has 
     * to the initial value of 3.
     */
    public void resetWizardLives();

    /**
     * Decreases the amount of lives the wizard has by 1.
     */
    public void decreaseWizardLives();

}
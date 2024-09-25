package no.uib.inf101.model;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Interface representing a wizard character in the game.
 */
public interface IWizard {

    /**
     * Gets the x-coordinate of the wizard.
     *
     * @return the x-coordinate of the wizard.
     */
    int getX();

    /**
     * Gets the y-coordinate of the wizard.
     *
     * @return the y-coordinate of the wizard.
     */
    int getY();

    /**
     * Gets the current sprite image of the wizard.
     * Used for drawing the wizard on the screen.
     * The sprite image depends on the direction the wizard is moving.
     * This method is used in the GameView class to draw the wizard.
     * It is called every time the screen is repainted.
     *
     * @return the current sprite image of the wizard.
     */
    BufferedImage getCurrentSprite();

    /**
     * Gets the solid area of the wizard used for collision detection.
     * The solid area is a rectangle representing the position and size 
     * of the wizard on the game board.
     *
     * @return the solid area of the wizard.
     */
    Rectangle getSolidArea();

    /*
     * Loads the sprites for the wizard character.
     * The sprites are loaded from the resources folder.
     */
    void loadSprites();

    /**
     * Sets the speed of the wizard.
     * The speed determines how fast the wizard moves on the game board.
     *
     * @param speed the speed of the wizard.
     */
    void setSpeed(int speed);

    /**
     * Gets the speed of the wizard.
     * The speed determines how fast the wizard moves on the game board.
     *
     * @return the speed of the wizard.
     */
    int getSpeed();

    /**
     * Gets the bounding box of the wizard.
     * The bounding box is used for collision detection with other objects.
     *
     * @return the bounding box of the wizard.
     */
    Rectangle getBounds();

    /**
     * Updates the position of the wizard based on the user input.
     * The wizard can move up, down, left, or right on the game board.
     * The wizard should not be able to move outside the game board.
     *
     * @param upPressed    true if the up key is pressed, false otherwise.
     * @param downPressed  true if the down key is pressed, false otherwise.
     * @param leftPressed  true if the left key is pressed, false otherwise.
     * @param rightPressed true if the right key is pressed, false otherwise.
     */
    void update(boolean upPressed, boolean downPressed, boolean leftPressed, boolean rightPressed);
}
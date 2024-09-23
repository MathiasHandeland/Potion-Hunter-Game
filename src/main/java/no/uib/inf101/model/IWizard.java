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
}
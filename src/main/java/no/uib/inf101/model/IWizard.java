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
     *
     * @return the current sprite image of the wizard.
     */
    BufferedImage getCurrentSprite();

    /**
     * Gets the solid area of the wizard used for collision detection.
     *
     * @return the solid area of the wizard.
     */
    Rectangle getSolidArea();
}

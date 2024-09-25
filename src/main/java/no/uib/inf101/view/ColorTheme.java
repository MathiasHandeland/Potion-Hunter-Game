package no.uib.inf101.view;

import java.awt.Color;

/**
 * The ColorTheme interface contains the methods that the view needs,
 * to know the different colors of the game. The view needs to know 
 * the colors of the background for the Start screen, pause screen and
 * Game over screen, and also the color of the text in the different gamestates 
 */
public interface ColorTheme {

    /**
     * Method to get a transparent background color.
     * This color is used for the Gamestate WELCOME_SCREEN and 
     * GAME_OVER and is used to make the text more readable because
     * it is a transparent color placed on top of the game
     * @return a new Color with the RGB values 0,0,0 and an alpha value
     * of 128. The alfa value is used to make the color transparent.
     */
    public Color getTransparentBackgroundColor();

    /**
     * Method to get the color of the text in the gamestate.
     * This color is used for all gamestates.
     * @return the Color WHITE.
     */
    public Color getGamestateTxtColor();
}
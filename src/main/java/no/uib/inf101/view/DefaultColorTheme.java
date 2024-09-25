package no.uib.inf101.view;

import java.awt.Color;

/**
 * The DefaultColorTheme class implements the ColorTheme interface.
 * The DefaultColorTheme class contains the default colors of the game.
 * This class contains the implementation of the methods in the 
 * ColorTheme interface.  
 * The DefaultColorTheme class is used by the GameView class to get the
 * different colors used in the game and display them on the screen.
 */
public class DefaultColorTheme implements ColorTheme {

    /**
     * Method to get a transparent background color.
     * This color is used for the Gamestate WELCOME_SCREEN and 
     * GAME_OVER and is used to make the text more readable because
     * it is a transparent color placed on top of the game
     * @return a new Color with the RGB values 0,0,0 and an alpha value
     * of 128. The alfa value is used to make the color transparent.
     */
    @Override
    public Color getTransparentBackgroundColor() {
        return new Color(0, 0, 0, 128);
    }

    /**
     * Method to get the color of the text in the gamestate.
     * This color is used for all gamestates.
     * @return the Color WHITE.
     */
    @Override
    public Color getGamestateTxtColor() {
        return Color.WHITE;
    }
    
}

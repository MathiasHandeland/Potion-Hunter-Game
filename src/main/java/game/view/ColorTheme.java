package game.view;

import java.awt.Color;

/**
 * The ColorTheme interface contains the methods that the view needs,
 * to know the different colors of the game. The view needs to know 
 * the colors of the background for the Start screen, Pause screen and
 * GameOver screen, and also the color of the text in the different gamestates 
 */
public interface ColorTheme {

    /**
     * This color is used for the Gamestate START_SCREEN, 
     * PAUSED_GAME and GAME_OVER. 
     * 
     * @return a new Color with the RGB values 0,0,0 and an alpha value
     * of 128. The alfa value is used to make the color transparent.
     */
    public Color getBackgroundColor();

    /**
     * Method to get the color of the text in the gamestate.
     * This color is used for all gamestates.
     * @return the Color WHITE.
     */
    public Color getGamestateTxtColor();
}
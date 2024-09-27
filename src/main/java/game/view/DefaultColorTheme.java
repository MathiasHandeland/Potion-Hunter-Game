package game.view;

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

    @Override
    public Color getBackgroundColor() {
        return new Color(0, 0, 0, 128);
    }

    @Override
    public Color getGamestateTxtColor() {
        return Color.WHITE;
    }  
}

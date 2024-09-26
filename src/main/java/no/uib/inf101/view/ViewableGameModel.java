package no.uib.inf101.view;

import no.uib.inf101.model.Enemy;
import no.uib.inf101.model.GameState;
import no.uib.inf101.model.Potion;
import no.uib.inf101.model.Wizard;
import java.util.List;

/**
 * The ViewableGameModel interface contains the methods from the model
 * that the view needs to draw the game. The view gets the information
 * it needs from the model through the methods in this interface.
 * The view needs to know the state of the game and the position of the
 * wizard in order to draw the game. 
 * The view also needs to know the position of the potions and the score
 * and the enemy in order to draw the game.
 */
public interface ViewableGameModel {
    
    /**
     * Get the current game state.
     * 
     * @return the current game state
     */
    GameState getGameState();

    /**
     * Get the wizard object.
     * 
     * @return the wizard object
     */
    Wizard getWizard();

    /**
     * Get the potion object.
     * 
     * @return the potion object
     */
    public List<Potion> getPotions();

    /**
     * Get the enemy object.
     * 
     * @return the enemy object
     */
    Enemy getEnemy();
    
    /**
     * Get the score.
     * 
     * @return the score
     */
    int getScore();

    /**
     * Get the width of the game board.
     * 
     * @return the width of the game board
     */
    int getBoardWidth();

    /**
     * Get the height of the game board.
     *
     * @return the height of the game board
     */
    int getBoardHeight();
}

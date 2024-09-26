package no.uib.inf101.controller;

import no.uib.inf101.model.GameState;
import no.uib.inf101.model.Wizard;

/**
 * The ControlableGameModel interface contains the methods from the 
 * model that the controller needs to control the game. The controller
 * gets the information it needs from the model through the methods in
 * this interface. The controller needs to know the state of the game
 * and the position of the wizard in order to update the game state
 * The controller needs to be able to reset the game, get the game state
 * and get the wizard and set the game state to be able to control the game.
 */
public interface ControlableGameModel {

    /**
     * Resets the game to its initial state.
     * That is, sets the game state to the start screen, 
     * resets the wizard's position, spawns a new potion,
     * sets the enemy to null, and sets the score to 0.
     */
    void resetGame();

    /**
     * Gets the current game state.
     * @return the current game state
     */
    GameState getGameState();

    /**
     * Gets the wizard object.
     * @return the wizard object
     */

    Wizard getWizard();

    /**
     * Sets the game state to the given state.
     * @param state the state to set the game to
     */
    void setGameState(GameState state);
}

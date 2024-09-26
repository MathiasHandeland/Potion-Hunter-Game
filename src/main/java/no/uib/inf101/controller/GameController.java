package no.uib.inf101.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import no.uib.inf101.model.GameState;

/**
 * This class is responsible for handling user input and updating the game state
 * accordingly. It listens for key presses and releases, and updates the wizard's
 * position based on the user's input.
 */
public class GameController extends KeyAdapter {
    
    private ControlableGameModel gameModel; 
    public boolean upPressed, downPressed, leftPressed, rightPressed; 

    /**
     * Constructor for the GameController class.
     * @param gameModel The game model that the controller will interact with.
     */
    public GameController(ControlableGameModel gameModel) {
        this.gameModel = gameModel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> upPressed = true;
            case KeyEvent.VK_DOWN -> downPressed = true;
            case KeyEvent.VK_LEFT -> leftPressed = true;
            case KeyEvent.VK_RIGHT -> rightPressed = true;

            case KeyEvent.VK_SPACE -> handleSpacePress();
            case KeyEvent.VK_P -> handlePausePress();
            case KeyEvent.VK_R -> handleRestartPress();
        }

        // If the game is active, update the wizard's position
        if (gameModel.getGameState() == GameState.ACTIVE_GAME) {
            gameModel.getWizard().update(upPressed, downPressed, leftPressed, rightPressed);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> upPressed = false;
            case KeyEvent.VK_DOWN -> downPressed = false;
            case KeyEvent.VK_LEFT -> leftPressed = false;
            case KeyEvent.VK_RIGHT -> rightPressed = false;
        }
        if (gameModel.getGameState() == GameState.ACTIVE_GAME) {
            gameModel.getWizard().update(upPressed, downPressed, leftPressed, rightPressed);
        }
    }

    private void handleSpacePress() {
        GameState currentState = gameModel.getGameState();
        if (currentState == GameState.START_SCREEN || currentState == GameState.PAUSED_GAME) {
            // Start the game or unpause it
            gameModel.setGameState(GameState.ACTIVE_GAME);
        }
    }

    private void handlePausePress() {
        GameState currentState = gameModel.getGameState();
        if (currentState == GameState.ACTIVE_GAME) {
            // Pause the game
            gameModel.setGameState(GameState.PAUSED_GAME);
        }
    }

    private void handleRestartPress() {
        if (gameModel.getGameState() == GameState.GAME_OVER) {
            // Reset the game state and game logic
            gameModel.resetGame();  // This method will reset all game elements
            gameModel.setGameState(GameState.ACTIVE_GAME); // Set the game state to active
        }
    }
}
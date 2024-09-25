package no.uib.inf101.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import no.uib.inf101.model.GameModel;

/**
 * Controller class for the game. Handles input from the user and updates the
 * game state accordingly.
 */
public class GameController extends KeyAdapter {
    private final GameModel gameModel;

    public boolean upPressed, downPressed, leftPressed, rightPressed;

    public GameController(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> upPressed = true;
            case KeyEvent.VK_DOWN -> downPressed = true;
            case KeyEvent.VK_LEFT -> leftPressed = true;
            case KeyEvent.VK_RIGHT -> rightPressed = true;
        }
        gameModel.getWizard().update(upPressed, downPressed, leftPressed, rightPressed);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> upPressed = false;
            case KeyEvent.VK_DOWN -> downPressed = false;
            case KeyEvent.VK_LEFT -> leftPressed = false;
            case KeyEvent.VK_RIGHT -> rightPressed = false;
        }
        gameModel.getWizard().update(upPressed, downPressed, leftPressed, rightPressed);
    }

    public void updateGame() {
        gameModel.update(downPressed, downPressed, downPressed, downPressed); // Use the GameModel's update logic to advance the game state
    }
}

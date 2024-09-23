package no.uib.inf101.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import no.uib.inf101.model.Wizard;

/**
 * Controller class for the game. Handles input from the user and updates the
 * game state accordingly.
 */
public class GameController extends KeyAdapter {
    private final Wizard wizard;

    public boolean upPressed, downPressed, leftPressed, rightPressed;

    public GameController(Wizard wizard) {
        this.wizard = wizard;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> upPressed = true;
            case KeyEvent.VK_DOWN -> downPressed = true;
            case KeyEvent.VK_LEFT -> leftPressed = true;
            case KeyEvent.VK_RIGHT -> rightPressed = true;
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
    }

    public void update() {
        wizard.update(upPressed, downPressed, leftPressed, rightPressed);
    }
}


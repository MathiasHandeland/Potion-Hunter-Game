package no.uib.inf101;

import javax.swing.*;

import no.uib.inf101.view.GameView;
import no.uib.inf101.controller.GameController;
import no.uib.inf101.model.Enemy;
import no.uib.inf101.model.GameModel;
import no.uib.inf101.model.Potion;
import no.uib.inf101.model.Wizard;
import java.util.Random;

/**
 * Main class for the game. Sets up the game window and starts the game loop.
 * The game loop updates the game state and redraws the game view at a fixed 
 * rate which gives the illusion of movement.
 * The game loop runs in a separate thread to keep the game responsive.
 */
public class GameMain {
    private JFrame window;
    private GameView gameView;
    private GameController controller;
    private GameModel gameModel;

    private static final int BOARD_WIDTH = 800;
    private static final int BOARD_HEIGHT = 600;

    public static void main(String[] args) {
        GameMain game = new GameMain();
        game.setupGame();
    }

    private void setupGame() {
        // Initialize game model
        gameModel = new GameModel();

        // Create the main game window
        window = new JFrame("Wizard Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        // Create the game panel and controller
        gameView = new GameView(gameModel, BOARD_WIDTH, BOARD_HEIGHT);
        controller = new GameController(gameModel);

        // Add the controller to the panel
        gameView.addKeyListener(controller);
        gameView.setFocusable(true);

        // Set up the window
        window.add(gameView);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // Start the game loop
        gameLoop();
    }

    private void gameLoop() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000.0 / 60.0;  // 60 ticks per second
        double delta = 0;

        // Continue the game only while the wizard has lives left
        while (gameModel.getWizard().getWizardLives() > 0) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;

            if (delta >= 1) {
                gameModel.update(controller.upPressed, controller.downPressed, controller.leftPressed, controller.rightPressed);

                // Update the game view with the latest state
                gameView.updateView();

                // Repaint the screen
                gameView.repaint();
                delta--;
            }

            // Optionally add a delay for frame rate control
            try {
                Thread.sleep(1); // Sleep for a short duration
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // End the game when the loop stops
        endGame();
    }

    private void endGame() {
        JOptionPane.showMessageDialog(window, "Game Over! You have lost all lives.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);  // Exit the game
    }
}
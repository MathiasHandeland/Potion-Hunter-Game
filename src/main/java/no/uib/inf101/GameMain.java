package no.uib.inf101;

import javax.swing.*;

import no.uib.inf101.view.GameView;
import no.uib.inf101.controller.GameController;
import no.uib.inf101.model.Potion;
import no.uib.inf101.model.Wizard;
import java.util.List;
import java.util.ArrayList;
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
    private Wizard wizard;
    private Potion potion;
    private int score; // Stores the score of the player, which is the amount of potions picked up


    public static void main(String[] args) {
        GameMain game = new GameMain();
        game.setupGame();
    }

    private void setupGame() {
        // Create the main game window
        window = new JFrame("Wizard Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        
        // Create the game panel and controller
        int boardWidth = 800;
        int boardHeight = 600;
        // Create a new wizard object
        wizard = new Wizard(336, 240, boardWidth, boardHeight);
        
        // Create potion objects
        // Create a single potion at a random position
        spawnNewPotion(boardWidth, boardHeight);

        // Initialize score to 0
        score = 0;

        gameView = new GameView(wizard, potion, score);
        controller = new GameController(wizard);

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
        double nsPerTick = 1000000000.0 / 60.0; // 60 ticks per second
        double delta = 0;

        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;

            if (delta >= 1) {
                controller.update(); // Update wizard position
                gameView.updateView(score); // Update the view
                checkPotionCollision(); // check for potion collision
                delta--; // Decrease delta to keep the frame rate stable
            }

            // Optionally add a delay for frame rate control
            try {
                Thread.sleep(1); // Sleep for a short duration
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * Method to check collision with potion and update score
     * If the wizard collides with a potion object on the game board
     * the potion is removed and a new potion is spawned at a random location
     */
    private void checkPotionCollision() {
        if (wizard.getBounds().intersects(potion.getBounds())) {
            potion.remove(); // Remove the potion from the screen
            score++; // Increase score

            // Spawn a new potion at a random location
            spawnNewPotion(800, 600); 
            gameView.updatePotion(potion); // Update the potion on the screen
        }
    }

    private void spawnNewPotion(int boardWidth, int boardHeight) {
        Random random = new Random();
        int potionX = random.nextInt(boardWidth);
        int potionY = random.nextInt(boardHeight);
        potion = new Potion(potionX, potionY); // Create a new potion at random location
    }
}


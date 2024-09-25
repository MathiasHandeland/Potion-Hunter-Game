package no.uib.inf101;

import javax.swing.*;

import no.uib.inf101.view.GameView;
import no.uib.inf101.controller.GameController;
import no.uib.inf101.model.Enemy;
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
    private Wizard wizard;
    private Potion potion;
    private Enemy enemy;
    private int score; // Stores the score of the player, which is the amount of potions picked up
    
    private int wizardLives = 3; // Number of lives the wizard has
    
    // Constants for game screen dimensions.
    private static final int BOARD_WIDTH = 800;
    private static final int BOARD_HEIGHT = 600;

    public static void main(String[] args) {
        GameMain game = new GameMain();
        game.setupGame();
    }

    private void setupGame() {
        // Create the main game window
        window = new JFrame("Wizard Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
                
        // Create a new wizard object
        wizard = new Wizard(336, 240, BOARD_WIDTH, BOARD_HEIGHT);
        
        // Create a single potion at a random position
        spawnNewPotion(BOARD_WIDTH, BOARD_HEIGHT);

        // Initialize score to 0
        score = 0;

        // Create the game panel and controller
        gameView = new GameView(wizard, potion, enemy, score, wizardLives, BOARD_WIDTH, BOARD_HEIGHT);
        controller = new GameController(wizard); // skal også ta in view

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
    
        // Continue the game only while the wizard has lives left
        while (wizardLives > 0) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
    
            if (delta >= 1) {
                controller.update(); // Update wizard position
                gameView.updateView(score, wizardLives); // Update the view
    
                checkPotionCollision(); // Check for potion collision
                if (enemy != null) {
                    enemy.update(wizard.getX(), wizard.getY()); // Update enemy position
                    checkEnemyCollision(); // Check for enemy collision
                    updateEnemySpeed(); // Update enemy speed
                }
    
                gameView.repaint(); // Repaint the screen
                delta--; // Decrease delta to keep the frame rate stable
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

    private void updateEnemySpeed() {
        // If the player has collected 10 potions, increase the enemy speed and the wizard speed
        if (score == 10) {
            // Increase enemy speed by 1
            enemy.setSpeed(2);
            wizard.setSpeed(5);
        }
        if (score == 20) {
            // Increase enemy speed by 1
            enemy.setSpeed(3);
            wizard.setSpeed(6);
        }
        if (score == 25) {
            // Increase enemy speed by 1
            enemy.setSpeed(4);
            wizard.setSpeed(8);
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

            // Spawn a new potion at a random location on the board            
            spawnNewPotion(BOARD_WIDTH, BOARD_HEIGHT); 
            gameView.updatePotion(potion); // Update the potion on the screen

            // Check if the player has collected 5 potions
            if (score == 3 && enemy == null) {
                enemy = new Enemy(100, 100, BOARD_WIDTH, BOARD_HEIGHT); // Spawn the NPC at a specific location
                gameView.updateNPC(enemy); // Update the NPC on the screen
            }
        }
    }

    private void spawnNewPotion(int boardWidth, int boardHeight) {
        Random random = new Random();
        int potionSize = 32; // Size of the potion. Subtracting this from the board size ensures the whole potion is fully visible
        int potionX = random.nextInt(boardWidth - potionSize); 
        int potionY = random.nextInt(boardHeight - potionSize); 

        potion = new Potion(potionX, potionY); // Create a new potion at random location
    }

    private void checkEnemyCollision() {
        // If the enemy exists and the wizard collides with it
        if (enemy != null && wizard.getBounds().intersects(enemy.getBounds())) {
            wizardLives--;  // Decrement wizard's lives
    
            // Turn the enemy to the opposite direction
            enemy.reverseDirection(); 
            enemy.setPaused(true); // Pause the enemy for a short duration
        }
    }
    

    private void endGame() {
        System.out.println("Game Over! The wizard has lost all lives.");
        JOptionPane.showMessageDialog(window, "Game Over! You have lost all lives.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0); // Exit the game
    }
}
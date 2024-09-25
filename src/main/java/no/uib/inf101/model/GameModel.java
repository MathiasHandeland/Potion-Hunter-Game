package no.uib.inf101.model;

import java.util.Random;

/**
 * GameModel class stores the game state and handles game logic.
 * Keeps instances of the wizard, potion, enemy, and other game objects.
 * Manages the score and the wizard's lives.
 */
public class GameModel {
    private Wizard wizard;
    private Potion potion;
    private Enemy enemy;
    private int score;       // Player's score

    private static final int BOARD_WIDTH = 800;
    private static final int BOARD_HEIGHT = 600;

    public GameModel() {
        // Initialize the wizard at a specific location
        this.wizard = new Wizard(336, 240, BOARD_WIDTH, BOARD_HEIGHT);

        // Spawn the first potion at a random position
        spawnNewPotion();

        // Initialize enemy to null until the player collects potions
        this.enemy = null;

        // Initialize score to 0 because the player has not collected any potions yet
        this.score = 0;
    }

    // Updates the game state (wizard, enemy, potion, and score)
    public void update(boolean upPressed, boolean downPressed, boolean leftPressed, boolean rightPressed) {
        // Update wizard's movement based on input
        wizard.update(upPressed, downPressed, leftPressed, rightPressed);

        // Check for collision between wizard and potion
        checkPotionCollision();

        // Update enemy movement if it exists
        if (enemy != null) {
            enemy.update(wizard.getX(), wizard.getY());
            checkEnemyCollision();  // Check collision between wizard and enemy
        }

        // Update enemy and wizard speed based on score
        updateSpeeds();
    }

    // Method to check collision with the potion and update the score
    private void checkPotionCollision() {
        if (wizard.getBounds().intersects(potion.getBounds())) {
            potion.remove();   // Remove the potion
            score++;           // Increase the score

            // Spawn a new potion at a random location
            spawnNewPotion();

            // After collecting 3 potions, spawn the enemy
            if (score == 3 && enemy == null) {
                enemy = new Enemy(100, 100, BOARD_WIDTH, BOARD_HEIGHT);
            }
        }
    }

    // Method to check collision between wizard and enemy
    private void checkEnemyCollision() {
        if (wizard.getBounds().intersects(enemy.getBounds())) {
            // Decrease wizard's lives
            wizard.decreaseWizardLives();
            enemy.reverseDirection();   // Make the enemy reverse direction
            enemy.setPaused(true);  // Pause the enemy briefly
        }
    }

    // Method to spawn a new potion at a random location
    private void spawnNewPotion() {
        Random random = new Random();
        int potionX = random.nextInt(BOARD_WIDTH - 32);  // Potion's width
        int potionY = random.nextInt(BOARD_HEIGHT - 32); // Potion's height
        potion = new Potion(potionX, potionY);
    }

    // Method to update speeds based on score
    private void updateSpeeds() {
        if (score == 10) {
            enemy.setSpeed(2);
            wizard.setSpeed(5);
        }
        if (score == 20) {
            enemy.setSpeed(3);
            wizard.setSpeed(6);
        }
        if (score == 25) {
            enemy.setSpeed(4);
            wizard.setSpeed(8);
        }
    }

    // Getters for game elements used in the view
    public Wizard getWizard() {
        return wizard;
    }

    public Potion getPotion() {
        return potion;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public int getScore() {
        return score;
    }
}




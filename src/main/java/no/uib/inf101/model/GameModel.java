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
    private GameState gameState;

    private static final int BOARD_WIDTH = 800;
    private static final int BOARD_HEIGHT = 600;

    public GameModel() {
        
        // Initialize the game state
        gameState = GameState.START_SCREEN; // Set the initial game state to the start screen

        // Initialize the wizard at a specific location
        this.wizard = new Wizard(336, 240, BOARD_WIDTH, BOARD_HEIGHT);

        // Spawn the first potion at a random position
        spawnNewPotion();

        // Initialize enemy to null until the player collects potions
        this.enemy = null;

        // Initialize score to 0 because the player has not collected any potions yet
        this.score = 0;
    }

    public void resetGame() {
        // Reset wizard's position and lives
        this.wizard = new Wizard(336, 240, BOARD_WIDTH, BOARD_HEIGHT);
        this.wizard.resetWizardLives(); // Reset lives
    
        // Reset the potion
        spawnNewPotion();
    
        // Reset the enemy to null (not present at the start)
        this.enemy = null;
    
        // Reset the score
        this.score = 0;
    
        // Set the game state back to START_SCREEN
        setGameState(GameState.START_SCREEN); // Initially set to START_SCREEN; it will change to ACTIVE_GAME on restart
    }
    
    
    

    // get the current game state
    public GameState getGameState() {
        return gameState;
    }

    // set the game state
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
            //ikke bruk for tror eg
    // reset the game state to the start screen
    public void resetGameState() {
        this.gameState = GameState.START_SCREEN;
    }

    // method to get the board width
    public int getBoardWidth() {
        return BOARD_WIDTH;
    }

    // method to get the board height
    public int getBoardHeight() {
        return BOARD_HEIGHT;
    }

    // Updates the game state based on current input
    public void update(boolean upPressed, boolean downPressed, boolean leftPressed, boolean rightPressed) {
        if (gameState == GameState.ACTIVE_GAME) {
            wizard.update(upPressed, downPressed, leftPressed, rightPressed);
            checkPotionCollision();
            if (enemy != null) {
                enemy.update(wizard.getX(), wizard.getY());
                checkEnemyCollision();
            }
            updateSpeeds();
        }
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
            
            // Check if the wizard has no lives left
            if (wizard.getWizardLives() <= 0) {
                setGameState(GameState.GAME_OVER);
            }
            
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




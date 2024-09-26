package no.uib.inf101.model;

import java.util.Random;

import no.uib.inf101.controller.ControlableGameModel;
import no.uib.inf101.view.ViewableGameModel;

/**
 * GameModel class stores the game state and handles game logic.
 * Keeps instances of the wizard, potion, enemy, and other game objects.
 * Manages the score and the wizard's lives.
 */
public class GameModel implements ControlableGameModel, ViewableGameModel {
    private Wizard wizard;
    private Potion potion;
    private Enemy enemy;
    private int score;      
    private GameState gameState;

    private static final int BOARD_WIDTH = 800;
    private static final int BOARD_HEIGHT = 600;

    /**
     * Constructor for the GameModel class.
     * Initializes the game state, wizard, potion, enemy, and score.
     */
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

    /**
     * Updates the game state based on the user input.
     * Updates the wizard's position and checks for collisions with the potion and enemy.
     * Updates the enemy's position and checks for collisions with the wizard.
     * Updates the game speed based on the score.
     * @param upPressed 
     * @param downPressed
     * @param leftPressed
     * @param rightPressed
     */
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

    @Override
    public Wizard getWizard() {
        return wizard;
    }

    @Override
    public Potion getPotion() {
        return potion;
    }

    @Override
    public Enemy getEnemy() {
        return enemy;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public int getBoardWidth() {
        return BOARD_WIDTH;
    }

    @Override
    public int getBoardHeight() {
        return BOARD_HEIGHT;
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }

    @Override
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
  
    @Override
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
}

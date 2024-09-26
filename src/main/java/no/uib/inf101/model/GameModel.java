package no.uib.inf101.model;

import java.util.Random;

import no.uib.inf101.controller.ControlableGameModel;
import no.uib.inf101.view.ViewableGameModel;
import java.util.List;
import java.util.ArrayList;

/**
 * GameModel class stores the game state and handle the game logic.
 * Keeps instances of the wizard, potion, enemy, and other game objects.
 * Manages the score and the wizard's lives.
 * Implements the ControlableGameModel and ViewableGameModel interfaces so
 * that the GameView and GameController can interact with the game model and
 * only access the necessary methods and fields they need.
 */
public class GameModel implements ControlableGameModel, ViewableGameModel {
    
    private Wizard wizard;
    private List<Potion> potions;
    private Enemy enemy;
    private int score;      
    private GameState gameState;

    private long lastPotionSpawnTime = System.currentTimeMillis();
    private final long potionSpawnInterval = 10000; // 10 seconds

    private static final int BOARD_WIDTH = 800;
    private static final int BOARD_HEIGHT = 600;

    /**
     * Constructor for the GameModel class.
     * Initializes the game state, wizard, potion, enemy, and score.
     */
    public GameModel() {
        
        gameState = GameState.START_SCREEN; 
        this.wizard = new Wizard(336, 240, BOARD_WIDTH, BOARD_HEIGHT);
        this.potions = new ArrayList<>();
        spawnNewPotion();
        this.enemy = null; // Initially set to null because the enemy is not present at the start
        this.score = 0;
    }

    /**
     * Updates the game state based on the user input.
     * Updates the wizard's position and checks for collisions with the potion and enemy.
     * Updates the enemy's position and checks for collisions with the wizard.
     * Updates the wizards speed based on the score/amount of potions collected.
     * Used in GameMain in the game loop to update the game based on game state.
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
            updateWizardSpeed();
        }
    }

    private void checkPotionCollision() {
        for (Potion potion : potions) {
            if (wizard.getBounds().intersects(potion.getBounds())) {
                potion.removePotion();
                score++;
                potions.remove(potion); // Remove the collected potion
                spawnNewPotion(); // Spawn a new potion at a random location
                break; // Exit the loop after collecting a potion
            }
        }
    
        // Spawn a new potion every 5 potions collected
        spawnExtraPotion();
    
        // After collecting 3 potions, spawn the enemy
        spawnEnemy();
    }   
    
    private void spawnEnemy() {
        if (score == 3 && enemy == null) {
            enemy = new Enemy(100, 100, BOARD_WIDTH, BOARD_HEIGHT);
        }
    }

    private void spawnExtraPotion() {
        if (System.currentTimeMillis() - lastPotionSpawnTime >= potionSpawnInterval && score > 0) {
            spawnNewPotion();
            lastPotionSpawnTime = System.currentTimeMillis();
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
        int potionX = random.nextInt(BOARD_WIDTH - 32);  
        int potionY = random.nextInt(BOARD_HEIGHT - 32); 
        potions.add(new Potion(potionX, potionY)); // Add new potion to the list
    }

    private void updateWizardSpeed() {
        switch (score) {
            case 10:
                enemy.setSpeed(2);
                wizard.setSpeed(5);
                break;
            case 20:
                enemy.setSpeed(3);
                wizard.setSpeed(6);
                break;
            case 25:
                enemy.setSpeed(4);
                wizard.setSpeed(8);
                break;
            case 40:
                enemy.setSpeed(5);
                wizard.setSpeed(10);
                break;
            case 50:
                enemy.setSpeed(6);
                wizard.setSpeed(12);
                break;
            default:
            
                break;
        }
    }
    

    @Override
    public Wizard getWizard() {
        return wizard;
    }

    @Override
    public List<Potion> getPotions() {
        return potions;
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
        this.potions.clear();
        spawnNewPotion();
    
        // Reset the enemy to null (not present at the start)
        this.enemy = null;
    
        // Reset the score
        this.score = 0;
    
        // Set the game state back to START_SCREEN
        setGameState(GameState.START_SCREEN); // Initially set to START_SCREEN; it will change to ACTIVE_GAME on restart
    }
}

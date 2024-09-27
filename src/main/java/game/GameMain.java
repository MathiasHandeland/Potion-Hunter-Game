package game;

import javax.swing.*;

import game.view.GameView;
import game.controller.GameController;
import game.model.GameModel;
import game.model.GameState;

/**
 * Main class for the game. Sets up the game window and starts the game loop.
 * The game loop updates the game state and redraws the game view at a fixed 
 * rate which gives the illusion of movement.
 * The game loop runs in a separate thread to keep the game responsive.
 */
public class GameMain {
    
    public static final String GAME_TITLE = "THE POTION HUNTER";

    private GameModel gameModel;
    private GameView gameView;
    private GameController gameController;
    private JFrame window;
    
    public static void main(String[] args) {
        GameMain game = new GameMain();
        game.setupGame();
    }

    private void setupGame() {
        // Initialize the game model, view, and controller
        gameModel = new GameModel();
        gameView = new GameView(gameModel, gameModel.getBoardWidth(), gameModel.getBoardHeight());
        gameController = new GameController(gameModel);
        
        // Create the main game window
        window = new JFrame(GAME_TITLE);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        // Set up the window
        window.add(gameView);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // Add the controller to the panel so it can listen for key events from the user
        gameView.addKeyListener(gameController);
        gameView.setFocusable(true);

        // Start the game loop in a new thread
        gameLoop();
    }

    // kommenter hele denne metoden
    private void gameLoop() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000.0 / 60.0;  // 60 ticks per second
        double delta = 0;
    
        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
    
            if (delta >= 1) {
                // Handle different game states
                GameState currentState = gameModel.getGameState();
                
                switch (currentState) {
                    case START_SCREEN -> gameView.paintStartScreen(window.getGraphics());
                    case ACTIVE_GAME -> {
                        gameModel.update(gameController.upPressed, gameController.downPressed, 
                                         gameController.leftPressed, gameController.rightPressed);
                        gameView.repaint();  // Update and repaint the game view
                    }
                    case PAUSED_GAME -> gameView.paintPauseScreen(window.getGraphics());
                    case GAME_OVER -> {
                        gameView.paintGameOverScreen(window.getGraphics());
                        // Keep the loop running to listen for restart input
                    }
                }
                delta--;
            }
        }
    }
}    
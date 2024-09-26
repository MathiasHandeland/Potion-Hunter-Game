package no.uib.inf101.view;

import java.awt.*;
import javax.swing.*;

import no.uib.inf101.model.GameState;
import no.uib.inf101.model.Potion;

/**
 * The view class for the game. Draws the game state on the screen.
 */
public class GameView extends JPanel {
    
    private ViewableGameModel gameModel;
    private TileManager tileManager; // final?
    private Image heartImage; // The heart image to represent the lives is gathered from the internet at: https://opengameart.org/content/heart-pixel-art
    private ColorTheme colortheme; 

    /**
     * Constructor for the GameView class. Initializes the game model and the tile manager.
     * 
     * @param gameModel The game model to draw on the screen
     * @param boardWidth The width of the game board
     * @param boardHeight The height of the game board
     */
    public GameView(ViewableGameModel gameModel, int boardWidth, int boardHeight) {
        
        this.gameModel = gameModel;
        this.tileManager = new TileManager(); // The tile manager is used to draw the background
        this.colortheme = new DefaultColorTheme();

        // Load the heart image to represent the lives
        this.heartImage = new ImageIcon(getClass().getResource("/heart/heart.png")).getImage();

        this.setPreferredSize(new Dimension(boardWidth, boardHeight));      
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        GameState gameState = gameModel.getGameState();

        // Draw the game state based on the current game state
        switch (gameState) {
            case START_SCREEN -> paintStartScreen(g);
            case ACTIVE_GAME -> paintActiveGame(g);
            case PAUSED_GAME -> paintPauseScreen(g);
            case GAME_OVER -> paintGameOverScreen(g);
        }
    }
    
    private void paintActiveGame(Graphics g) {
    // Draw the active game screen
    tileManager.drawTiles(g, getWidth(), getHeight());

    // Draw the wizard
    g.drawImage(gameModel.getWizard().getCurrentSprite(), gameModel.getWizard().getX(), gameModel.getWizard().getY(), 
        gameModel.getWizard().getSolidArea().width, gameModel.getWizard().getSolidArea().height, null);

    // Draw all potions
    for (Potion potion : gameModel.getPotions()) {
        if (potion.getX() != -1 && potion.getY() != -1) {
            g.drawImage(potion.getCurrentSprite(), potion.getX(), potion.getY(), 
                potion.getSolidArea(), potion.getSolidArea(), null);
        }
    }

    // Draw the enemy if it exists
    if (gameModel.getEnemy() != null) {
        g.drawImage(gameModel.getEnemy().getCurrentSprite(), gameModel.getEnemy().getX(), gameModel.getEnemy().getY(), 
            gameModel.getEnemy().getBounds().width, gameModel.getEnemy().getBounds().height, null);
    }

    // Draw score and lives
    drawScore((Graphics2D) g, gameModel.getScore());
    drawLives((Graphics2D) g, gameModel.getWizard().getWizardLives());
}


    private void drawScore(Graphics2D g2d, int score) {
        // Enable anti-aliasing for smooth text
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Font settings
        Font scoreFont = new Font("Verdana", Font.BOLD, 25); 
        g2d.setFont(scoreFont);
        // Score text to display
        String scoreText = "Potions: " + score;
        // Gradient color for the text (light-to-dark)
        GradientPaint gradient = new GradientPaint(0, 0, Color.MAGENTA, 100, 0, Color.CYAN, true);
        g2d.setPaint(gradient);
        // Shadow effect - Draw slightly offset black shadow
        g2d.setColor(Color.BLACK);
        g2d.drawString(scoreText, 12, 42);  // Shadow offset by (2, 2)
        // Draw the actual score text with gradient on top of the shadow
        g2d.setPaint(gradient);
        g2d.drawString(scoreText, 10, 40);  // Main score text
    }

    private void drawLives(Graphics2D g2d, int wizardLives) {
        int heartSize = 30; // Size of the heart icon
        int spacing = 10;   // Spacing between heart icons
        int xPosition = getWidth() - (wizardLives * (heartSize + spacing)) - 20; // Start drawing hearts on the top-right corner
        int yPosition = 20; // Y position of the hearts

        for (int i = 0; i < wizardLives; i++) {
            g2d.drawImage(heartImage, xPosition + i * (heartSize + spacing), yPosition, heartSize, heartSize, null);
        }
    }

    public void paintStartScreen(Graphics g) {
        g.setColor(colortheme.getBackgroundColor());
        g.fillRect(0, 0, gameModel.getBoardWidth(), gameModel.getBoardHeight());

        g.setColor(colortheme.getGamestateTxtColor());
        g.setFont(new Font("Monospaced", Font.BOLD, 80));
        Inf101Graphics.drawCenteredString(g, "POTION HUNTER", gameModel.getBoardWidth() / 2,
        gameModel.getBoardHeight() / 2);

        g.setFont(new Font("Monospaced", Font.BOLD, 24));
        Inf101Graphics.drawCenteredString(g, "Press space to start", 
        gameModel.getBoardWidth() / 2, gameModel.getBoardHeight() / 2 + 100);     
    }

    public void paintGameOverScreen(Graphics g) {
        g.setColor(colortheme.getBackgroundColor());
        g.fillRect(0, 0, gameModel.getBoardWidth(), gameModel.getBoardHeight());

        g.setColor(colortheme.getGamestateTxtColor());
        g.setFont(new Font("Monospaced", Font.BOLD, 120));
        Inf101Graphics.drawCenteredString(g, "GAME OVER", gameModel.getBoardWidth() / 2,
        gameModel.getBoardHeight() / 2);

        g.setFont(new Font("Monospaced", Font.BOLD, 24));
        Inf101Graphics.drawCenteredString(g, "Press R to restart", 
        gameModel.getBoardWidth() / 2, gameModel.getBoardHeight() / 2 + 100);
    }

    public void paintPauseScreen(Graphics g) {
        g.setColor(colortheme.getBackgroundColor());
        g.fillRect(0, 0, gameModel.getBoardWidth(), gameModel.getBoardHeight());

        g.setColor(colortheme.getGamestateTxtColor());
        g.setFont(new Font("Monospaced", Font.BOLD, 150));
        Inf101Graphics.drawCenteredString(g, "PAUSED", gameModel.getBoardWidth() / 2,
        gameModel.getBoardHeight() / 2);

        g.setFont(new Font("Monospaced", Font.BOLD, 24));
        Inf101Graphics.drawCenteredString(g, "Press space to resume", 
        gameModel.getBoardWidth() / 2, gameModel.getBoardHeight() / 2 + 100);
    }
}

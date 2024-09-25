package no.uib.inf101.view;

import java.awt.*;
import javax.swing.*;

import no.uib.inf101.model.GameModel;
import no.uib.inf101.model.GameState;
import no.uib.inf101.model.Wizard;
import no.uib.inf101.model.Enemy;
import no.uib.inf101.model.Potion;

/**
 * The view class for the game. Draws the game state on the screen.
 */
public class GameView extends JPanel {
    
    private final GameModel gameModel;
    private final TileManager tileManager; 
    private Image heartImage; // Image for lives

    public GameView(GameModel gameModel, int boardWidth, int boardHeight) {
        this.gameModel = gameModel;
        this.tileManager = new TileManager(); // The tile manager is used to draw the background

        // Load the heart image to represent the lives
        this.heartImage = new ImageIcon(getClass().getResource("/heart/heart.png")).getImage();

        this.setPreferredSize(new Dimension(boardWidth, boardHeight));      
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        GameState gameState = gameModel.getGameState();

        switch (gameState) {
            case START_SCREEN -> paintStartScreen(g);
            case ACTIVE_GAME -> {
                // Draw the active game screen
                tileManager.drawTiles(g, getWidth(), getHeight());

                // Draw the wizard
                Wizard wizard = gameModel.getWizard();
                g.drawImage(wizard.getCurrentSprite(), wizard.getX(), wizard.getY(), wizard.getSolidArea().width, wizard.getSolidArea().height, null);

                // Draw the potion if it's visible
                Potion potion = gameModel.getPotion();
                if (potion.getX() != -1 && potion.getY() != -1) {
                    g.drawImage(potion.getCurrentSprite(), potion.getX(), potion.getY(), potion.getSolidArea(), potion.getSolidArea(), null);
                }

                // Draw the enemy if it exists
                Enemy enemy = gameModel.getEnemy();
                if (enemy != null) {
                    g.drawImage(enemy.getCurrentSprite(), enemy.getX(), enemy.getY(), enemy.getBounds().width, enemy.getBounds().height, null);
                }

                // Draw score and lives
                drawScore((Graphics2D) g, gameModel.getScore());
                drawLives((Graphics2D) g, gameModel.getWizard().getWizardLives());
            }
            case PAUSED_GAME -> paintPauseScreen(g);
            case GAME_OVER -> paintGameOverScreen(g);
        }
    }
    
    public void updateView() {
        repaint();
    }

    // Draw lives using heart images or icons
    private void drawLives(Graphics2D g2d, int wizardLives) {
        int heartSize = 30; // Size of the heart icon
        int spacing = 10;   // Spacing between heart icons
        int xPosition = getWidth() - (wizardLives * (heartSize + spacing)) - 20; // Start drawing hearts on the top-right corner
        int yPosition = 20; // Y position of the hearts

        for (int i = 0; i < wizardLives; i++) {
            g2d.drawImage(heartImage, xPosition + i * (heartSize + spacing), yPosition, heartSize, heartSize, null);
        }
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

    public void paintStartScreen(Graphics g) {
        // Clear the screen
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
    
        // Draw the start screen text
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("POTION HUNTER", getWidth() / 2 - 210, getHeight() / 2 - 50);
    
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Press space bar to start", getWidth() / 2 - 100, getHeight() / 2 + 10);
    }

    public void paintGameOverScreen(Graphics g) {
        // Set transparency
        g.setColor(new Color(0, 0, 0, 150)); // Black with 150 alpha
        g.fillRect(0, 0, getWidth(), getHeight());
    
        // Draw "Game Over" message
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("Game Over", getWidth() / 2 - 150, getHeight() / 2 - 50);
    
        // Draw "Press space bar to restart" message
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Press R button to restart", getWidth() / 2 - 100, getHeight() / 2 + 10);
    }

    // paint pause screen
    public void paintPauseScreen(Graphics g) {
        // Set transparency
        g.setColor(new Color(0, 0, 0, 150)); // Black with 150 alpha
        g.fillRect(0, 0, getWidth(), getHeight());
    
        // Draw "Game Paused" message
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("Game Paused", getWidth() / 2 - 150, getHeight() / 2 - 50);
    
        // Draw "Press space bar to resume" message
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Press space to resume", getWidth() / 2 - 100, getHeight() / 2 + 10);
    }
}

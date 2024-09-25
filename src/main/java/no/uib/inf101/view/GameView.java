package no.uib.inf101.view;

import java.awt.*;
import javax.swing.*;

import no.uib.inf101.model.Enemy;
import no.uib.inf101.model.Potion;
import no.uib.inf101.model.Wizard;

/**
 * The view class for the game. Draws the game state on the screen.
 */
public class GameView extends JPanel {
    
    private final Wizard wizard;
    private final TileManager tileManager; 
    private Potion potion;
    private Enemy enemy; // The enemy NPC
    private int score; // To display the score of the player

    private int wizardLives; // Add wizard lives variable
    private Image heartImage; // Image for lives

    public GameView(Wizard wizard, Potion potion, Enemy enemy, int score, int wizardLives, int boardWidth, int boardHeight) {
        this.wizard = wizard;
        this.tileManager = new TileManager(); // The tile manager is used to draw the background
        this.potion = potion;
        this.enemy = enemy;
        this.score = score; 
        this.wizardLives = wizardLives; // Initialize wizard lives

        // Load the heart image to represent the lives
        this.heartImage = new ImageIcon(getClass().getResource("/heart/heart.png")).getImage();

        this.setPreferredSize(new Dimension(boardWidth, boardHeight));      
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw the background with tiles by repeating the tile image for the entire window
        tileManager.drawTiles(g, getWidth(), getHeight());

        // Draw the current sprite of the wizard
        g.drawImage(wizard.getCurrentSprite(), wizard.getX(), wizard.getY(), wizard.getSolidArea().width, wizard.getSolidArea().height, null);

        // Draw the potion if it's visible (not removed)
        if (potion.getX() != -1 && potion.getY() != -1) {
            g.drawImage(potion.getCurrentSprite(), potion.getX(), potion.getY(), potion.getSolidArea(), potion.getSolidArea(), null);
        }

        // Draw the NPC if it exists
        if (enemy != null) {
            g.drawImage(enemy.getCurrentSprite(), enemy.getX(), enemy.getY(), enemy.getBounds().width, enemy.getBounds().height, null);
        }

        // Draw the score at the top-left corner with a aesthetic visual representation
        drawScore((Graphics2D) g); 

        // Draw the lives at the top-right corner
        drawLives((Graphics2D) g);
    }

    public void updateView(int newScore, int newLives) {
        this.score = newScore; // Update the score
        this.wizardLives = newLives; // Update the lives
        repaint();
    }

    // Draw lives using heart images or icons
    private void drawLives(Graphics2D g2d) {
        int heartSize = 30; // Size of the heart icon
        int spacing = 10;   // Spacing between heart icons
        int xPosition = getWidth() - (wizardLives * (heartSize + spacing)) - 20; // Start drawing hearts on the top-right corner
        int yPosition = 20; // Y position of the hearts

        for (int i = 0; i < wizardLives; i++) {
            g2d.drawImage(heartImage, xPosition + i * (heartSize + spacing), yPosition, heartSize, heartSize, null);
        }
    }

    // Method to update the potion's position
    public void updatePotion(Potion newPotion) {
        this.potion = newPotion; // Update the potion
        repaint(); // Repaint the screen with the new potion
    }

    // Method to update the NPC
    public void updateNPC(Enemy enemy) {
        this.enemy = enemy; // Update the NPC
        repaint(); // Repaint the screen with the new NPC
    }

    private void drawScore(Graphics2D g2d) {
        // Enable anti-aliasing for smooth text
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Font settings
        Font scoreFont = new Font("Verdana", Font.BOLD, 30); 
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
}
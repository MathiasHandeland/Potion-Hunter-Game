package no.uib.inf101.view;

import java.awt.*;
import javax.swing.*;

import no.uib.inf101.model.Potion;
import no.uib.inf101.model.Wizard;

/**
 * The view class for the game. Draws the game state on the screen.
 */
public class GameView extends JPanel {
    
    private final Wizard wizard;
    private final TileManager tileManager; 
    private Potion potion;
    private int score; // To display the score of the player

    public GameView(Wizard wizard, Potion potion, int score) {
        this.wizard = wizard;
        this.tileManager = new TileManager(); // The tile manager is used to draw the background
        this.potion = potion;
        this.score = score;

        this.setPreferredSize(new Dimension(800, 600)); // Fix med å hente fra        
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

        // Draw the score at the top-left corner with a aesthetic visual representation
        drawScore((Graphics2D) g); 
    }

    public void updateView(int newScore) {
        this.score = newScore; // Update the score
        repaint();
    }

    // Method to update the potion's position
    public void updatePotion(Potion newPotion) {
        this.potion = newPotion; // Update the potion
        repaint(); // Repaint the screen with the new potion
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
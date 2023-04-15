package no.uib.inf101.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import no.uib.inf101.entity.Player;
import no.uib.inf101.tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

    // Screen settings, displays 16 48x48 tiles on the screen
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16; 
    public final int maxScreenRow = 12;
    public final int screenWidth = maxScreenCol * tileSize; // 768 pixels
    public final int screenHeight = maxScreenRow * tileSize; // 576 pixels

    // FPS
    int FPS = 60;

    TileManager tileManager = new TileManager(this);
    GameController controller = new GameController();
    Thread gameThread;
    Player player = new Player(this, controller); // this er Gamepanel klassen

    // set character's default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true); // bette rendering performance
        this.addKeyListener(controller);
        this.setFocusable(true);
        startGameThread(); // TODO la til denne
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        // The Game Loop, delta game loop
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                delta--;
                update();
                repaint();
            }
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileManager.draw(g2);

        player.draw(g2);

        g2.dispose(); // save memory
    }
}

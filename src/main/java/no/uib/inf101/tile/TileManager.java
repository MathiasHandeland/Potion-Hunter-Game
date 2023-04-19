package no.uib.inf101.tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import no.uib.inf101.main.GamePanel;

public class TileManager {
    
    GamePanel gamePanel;
    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        tile = new Tile[10]; // hvor mange typer tiles vi har
        mapTileNum = new int[gamePanel.maxScreenCol][gamePanel.maxScreenRow];

        getTileImage();
        loadMap("/maps/map1.txt");
    }

    public void getTileImage() {

        try {

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResource("/tiles/bigrock1.png"));
            tile[0].collision = true;

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResource("/tiles/stone.png"));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {

        try {

            InputStream is = getClass().getResourceAsStream(filePath); // import text file
            BufferedReader br = new BufferedReader(new InputStreamReader(is)); // read text file
            
            int col = 0;
            int row = 0;

            // scan the text file
            while (col < gamePanel.maxScreenCol && row < gamePanel.maxScreenRow) {
                
                String line = br.readLine(); // read line by line from text file

                while (col < gamePanel.maxScreenCol) {
                    
                    String numbers[] = line.split(" "); // split line by space
                    
                    int num = Integer.parseInt(numbers[col]); // convert string to int
                
                    mapTileNum[col][row] = num; // store the number in the array mapTileNum
                    col++;
                }
                if (col == gamePanel.maxScreenCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (Exception e) {

        }
    }

    public void draw(Graphics2D g2) {

        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < gamePanel.maxScreenCol && row < gamePanel.maxScreenRow) {
                
            int tileNum = mapTileNum[col][row];

            g2.drawImage(tile[tileNum].image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
            col++;
            x += gamePanel.tileSize;

            if (col == gamePanel.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += gamePanel.tileSize;
            }
        }
    }     
}

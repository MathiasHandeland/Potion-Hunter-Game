    package no.uib.inf101.model;

    import java.awt.image.BufferedImage;
    import javax.imageio.ImageIO;
    import java.io.IOException;
    import java.awt.Rectangle;

    /**
     * Class representing a potion in the game.
     * The potions should be placed randomly on the game board
     * and disappear when picked up by the wizard.
     * The wizard should be able to pick up the potion by moving over it.
     * When the wizard picks up a potion, the potion should disappear and the wizard should gain points.
     * The representation of a potion is the purple_potion.png image in the resources folder.
     * The potion should be placed randomly on the game board and disappear when picked up by the wizard.
     */
    public class Potion implements IPotion {
        
        private int x, y;
        private int solidArea = 32; // Size of the potion
        private BufferedImage potion_sprite;
        
        public Potion(int x, int y) {
            this.x = x;
            this.y = y;
            loadSprite();
        }

        private void loadSprite() {
            // Load the image of the potion here
            try {
                potion_sprite = ImageIO.read(getClass().getResource("/potion/purple_potion.png"));

            } catch (IOException e) {
                e.printStackTrace();
            }  
        }

        public BufferedImage getCurrentSprite() {
            return potion_sprite;
        }
        
        @Override
        public int getX() {
            return x;
        }

        @Override
        public int getY() {
            return y;
        }

        @Override
        public int getSolidArea() {
            return solidArea;
        }

        @Override
        public void remove() {
            // Remove the potion from the game board
            x = -1;
            y = -1;
        }

        /*
        * Gets the bounds of the potion used for collision detection with the wizard.
        * Returns a rectangle representing their position and size on the game board.        
        */
        public Rectangle getBounds() {
            return new Rectangle(x, y, solidArea, solidArea);
        }

        public void setPosition(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

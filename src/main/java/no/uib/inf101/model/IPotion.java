package no.uib.inf101.model;

/**
 * Interface representing a potion in the game.
 * Potions can be picked up by the wizard to gain points
 * The potions should be placed randomly on the game board
 * and disappear when picked up by the wizard.
 * The wizard should be able to pick up the potion by moving over it.
 * When the wizard picks up a potion, the potion should disappear and the wizard should gain points.
 */
public interface IPotion {
    
    /**
     * Gets the x-coordinate of the potion.
     *
     * @return the x-coordinate of the potion.
     */
    int getX();

    /**
     * Gets the y-coordinate of the potion.
     *
     * @return the y-coordinate of the potion.
     */
    int getY();

    /**
     * Gets the solid area of the potion used for collision detection.
     *
     * @return the solid area of the potion.
     */
    int getSolidArea();
    
    /**
     * Removes the potion from the game board.
     * It removes the potion by setting the x and y coordinates to -1.
     * This will make the potion disappear from the game board because 
     * the game board will not draw objects with negative coordinates.
     */
    void remove();
}

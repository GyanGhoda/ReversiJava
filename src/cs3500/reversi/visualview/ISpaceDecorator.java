package cs3500.reversi.visualview;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Represents a decorator for a space.
 */
public interface ISpaceDecorator {

    /**
     * Draws the space with hints.
     *
     * @param g2d   the graphics to draw with
     * @param hints whether or not to draw hints
     * @param score the score to draw
     */
    void drawSpaceOwner(Graphics2D g2d, boolean hints, int score);

    /**
     * Gets the color of the space.
     *
     * @return the color of the space
     */
    Color getColor();

    /**
     * Gets the size of the space.
     *
     * @return the size of the space
     */
    double getSize();

    /**
     * Gets the current x position of the space.
     *
     * @return the current x position of the space
     */
    double getCurrentX();

    /**
     * Gets the current y position of the space.
     *
     * @return the current y position of the space
     */
    double getCurrentY();

    /**
     * Gets the state of the space.
     *
     * @return the state of the space
     */
    boolean getState();

    /**
     * Moves the space to the given position.
     *
     * @param startingX the starting x position
     * @param startingY the starting y position
     */
    void moveTo(double startingX, double startingY);

    /**
     * Checks if the given mouse position is contained in the space.
     *
     * @param mouseX the x position of the mouse
     * @param mouseY the y position of the mouse
     * @return whether or not the mouse position is contained in the space
     */
    boolean contains(int mouseX, int mouseY);

    /**
     * Sets the state of the space.
     *
     * @param b the state to set
     */
    void setState(boolean b);

    /**
     * Draws the fill color of the space.
     *
     * @param g2d the graphics to draw with
     */
    void drawFillColor(Graphics2D g2d);
}

package cs3500.reversi.visualview;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Represents a decorator for a space.
 */
public abstract class SpaceDecorator implements ISpaceDecorator {

    protected ISpaceDecorator decoratedSpace;

    /**
     * Constructs a new SpaceDecorator.
     *
     * @param decoratedSpace the space to decorate
     */
    public SpaceDecorator(ISpaceDecorator decoratedSpace) {
        this.decoratedSpace = decoratedSpace;
    }

    /**
     * Draws the space with hints.
     *
     * @param g2d   the graphics to draw with
     * @param hints whether or not to draw hints
     * @param score the score to draw
     */
    public void drawSpace(Graphics2D g2d, boolean hints, int score) {
        this.decoratedSpace.drawSpaceOwner(g2d, hints, score);
    }

    /**
     * Gets the color of the space.
     *
     * @return the color of the space
     */
    public Color getColor() {
        return this.decoratedSpace.getColor();
    }
}

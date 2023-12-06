package cs3500.reversi.visualview;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class SpaceDecorator implements ISpaceDecorator {
    protected ISpaceDecorator decoratedSpace;

    public SpaceDecorator(ISpaceDecorator decoratedSpace) {
        this.decoratedSpace = decoratedSpace;
    }

    public void drawSpace(Graphics2D g2d, boolean hints, int score) {
        this.decoratedSpace.drawSpaceOwner(g2d, hints, score);
    }

    public Color getColor() {
        return this.decoratedSpace.getColor();
    }
}

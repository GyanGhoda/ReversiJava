package cs3500.reversi.visualview;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class HintsDecorator extends SpaceDecorator {
    public HintsDecorator(ISpaceDecorator decoratedSpace) {
        super(decoratedSpace);
    }

    public void drawSpaceOwner(Graphics2D g2d, boolean hints, int score) {
        this.decoratedSpace.drawSpaceOwner(g2d, hints, score);
        if (hints && this.decoratedSpace.getState()) {
            FontMetrics fm = g2d.getFontMetrics();
            double centerX = this.decoratedSpace.getCurrentX() - fm.stringWidth(Integer.toString(score))
                    + this.decoratedSpace.getSize() / 2;
            double centerY = this.decoratedSpace.getCurrentY() + fm.getHeight() / 4 + this.decoratedSpace.getSize() / 2;
            g2d.setFont(new Font("Serif", Font.BOLD, (int) this.decoratedSpace.getSize() / 2));
            g2d.drawString(Integer.toString(score), (int) centerX, (int) centerY);
        }
    }

    @Override
    public double getSize() {
        return this.decoratedSpace.getSize();
    }

    @Override
    public double getCurrentX() {
        return this.decoratedSpace.getCurrentX();
    }

    @Override
    public double getCurrentY() {
        return this.decoratedSpace.getCurrentY();
    }

    @Override
    public boolean getState() {
        return this.decoratedSpace.getState();
    }
}

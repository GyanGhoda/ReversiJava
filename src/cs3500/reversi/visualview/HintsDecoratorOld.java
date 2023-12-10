//package cs3500.reversi.visualview;
//
//import java.awt.Font;
//import java.awt.FontMetrics;
//import java.awt.Graphics2D;
//
///**
// * Represents a decorator for a space that adds hints to the space.
// */
//public class HintsDecorator extends SpaceDecorator {
//    public HintsDecorator(HexagonSpace decoratedSpace) {
//        super(decoratedSpace);
//    }
//
//    /**
//     * Draws the space with hints.
//     *
//     * @param g2d   the graphics to draw with
//     * @param hints whether or not to draw hints
//     * @param score the score to draw
//     */
//    public void drawSpaceOwner(Graphics2D g2d, boolean hints, int score) {
//        this.drawSpace(g2d, hints, score);
//        if (hints && this.decoratedSpace.getState()) {
//            FontMetrics fm = g2d.getFontMetrics();
//            double centerX = this.decoratedSpace.getCurrentX() - fm.stringWidth(Integer.toString(score));
//            double centerY = this.decoratedSpace.getCurrentY() + (double) fm.getHeight() / 4;
//            g2d.setFont(new Font("Serif", Font.BOLD, (int) this.decoratedSpace.getSize() * 2 / 3));
//            g2d.drawString(Integer.toString(score), (int) centerX, (int) centerY);
//        }
//    }
//
//    @Override
//    public double getSize() {
//        return this.decoratedSpace.getSize();
//    }
//
//    @Override
//    public double getCurrentX() {
//        return this.decoratedSpace.getCurrentX();
//    }
//
//    @Override
//    public double getCurrentY() {
//        return this.decoratedSpace.getCurrentY();
//    }
//
//    @Override
//    public boolean getState() {
//        return this.decoratedSpace.getState();
//    }
//
//    @Override
//    public void moveTo(double startingX, double startingY) {
//        this.decoratedSpace.moveTo(startingX, startingY);
//    }
//
//    @Override
//    public boolean contains(int mouseX, int mouseY) {
//        return this.decoratedSpace.contains(mouseX, mouseY);
//    }
//
//    @Override
//    public void setState(boolean b) {
//        this.decoratedSpace.setState(b);
//    }
//
//    @Override
//    public void drawFillColor(Graphics2D g2d) {
//        this.decoratedSpace.drawFillColor(g2d);
//    }
//}

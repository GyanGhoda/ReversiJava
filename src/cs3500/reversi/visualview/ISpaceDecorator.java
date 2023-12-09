package cs3500.reversi.visualview;

import java.awt.Color;
import java.awt.Graphics2D;

public interface ISpaceDecorator {
    void drawSpaceOwner(Graphics2D g2d, boolean hints, int score);

    Color getColor();

    double getSize();

    double getCurrentX();

    double getCurrentY();

    boolean getState();

    void moveTo(double startingX, double startingY);

    boolean contains(int mouseX, int mouseY);

    void setState(boolean b);

    void drawFillColor(Graphics2D g2d);
}

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
}

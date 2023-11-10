package cs3500.reversi.visualview;

import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class HexagonSpace extends Path2D.Double {
    int size;
    double currentX;
    double currentY;

    public HexagonSpace(int size, double currentX, double currentY) {
        this.size = size;
        this.currentX = currentX;
        this.currentY = currentY;

        this.constructHexagon();
    }

    private void constructHexagon() {
        for (int i = 0; i < 6; i++) {
            double angle = 2 * Math.PI / 6 * i + Math.PI / 2;
            double xPoint = this.currentX + this.size * Math.cos(angle);
            double yPoint = this.currentY + this.size * Math.sin(angle);
            if (i == 0) {
                moveTo(xPoint, yPoint);
            } else {
                lineTo(xPoint, yPoint);
            }
        }

        this.closePath();
    }
}

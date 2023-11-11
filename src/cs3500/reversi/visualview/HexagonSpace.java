package cs3500.reversi.visualview;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

import cs3500.reversi.model.Cell;
import cs3500.reversi.model.CellType;

public class HexagonSpace extends Path2D.Double {
    double size;
    double currentX;
    double currentY;
    private boolean isHighlighted;
    private Cell representingCell;

    public HexagonSpace(double size, double currentX, double currentY, Cell representingCell) {
        this.size = size;
        this.currentX = currentX;
        this.currentY = currentY;
        this.isHighlighted = false;
        this.representingCell = representingCell;

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

    public Color getColor() {
        if (this.isHighlighted) {
            return Color.CYAN;
        } else {
            return Color.LIGHT_GRAY;
        }
    }

    public void setState(boolean state) {
        this.isHighlighted = state;
    }

    public boolean getState() {
        return this.isHighlighted;
    }

    public void drawSpaceOwner(Graphics2D g2d) {

        if (!representingCell.getCellType().equals(CellType.Empty)) {

            Path2D.Double circlePath = new Path2D.Double();

            // Define the radius for the small circle
            double circleRadius = this.size * 0.45;

            // Construct circle path
            circlePath.moveTo(this.currentX + circleRadius, this.currentY);

            for (int i = 0; i <= 360; i++) {
                double rad = Math.toRadians(i);
                double xCircle = this.currentX + circleRadius * Math.cos(rad);
                double yCircle = this.currentY + circleRadius * Math.sin(rad);
                circlePath.lineTo(xCircle, yCircle);
            }

            circlePath.closePath();

            // Set color and fill the circle
            if (representingCell.getCellOwner().equals("X")) {
                g2d.setColor(Color.BLACK);
            } else {
                g2d.setColor(Color.WHITE);
            }

            g2d.fill(circlePath);
        }
    }
}

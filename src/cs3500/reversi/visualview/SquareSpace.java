package cs3500.reversi.visualview;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

import cs3500.reversi.model.Cell;
import cs3500.reversi.model.CellType;

/**
 * Represents a square space in the Reversi game board.
 */
public class SquareSpace extends Path2D.Double {

    private final double size;
    private final double currentX;
    private final double currentY;
    private boolean isHighlighted;
    private final Cell representingCell;

    /**
     * Constructs a new SquareSpace with the given size, currentX, currentY, and
     * representingCell.
     *
     * @param size             The size of the square space.
     * @param currentX         The current x coordinate of the square space.
     * @param currentY         The current y coordinate of the square space.
     * @param representingCell The cell that the square space represents.
     */
    public SquareSpace(double size, double currentX, double currentY, Cell representingCell) {
        this.size = size;
        this.currentX = currentX;
        this.currentY = currentY;
        this.isHighlighted = false;
        this.representingCell = representingCell;

        this.constructSquare();
    }

    /**
     * Constructs the square.
     */
    private void constructSquare() {
        // Start at the top left corner of the square
        moveTo(currentX, currentY);

        // Draw line to the top right corner
        lineTo(currentX + size, currentY);

        // Draw line to the bottom right corner
        lineTo(currentX + size, currentY + size);

        // Draw line to the bottom left corner
        lineTo(currentX, currentY + size);

        // Close the path to complete the square
        closePath();
    }

    /**
     * Get the size of the square space.
     *
     * @return The size of the square space.
     */
    public Color getColor() {
        if (this.isHighlighted) {
            return Color.CYAN;
        } else {
            return Color.LIGHT_GRAY;
        }
    }

    /**
     * Change the highlighted state of the square space.
     * 
     * @param state The new highlighted state of the square space.
     */
    public void setState(boolean state) {
        this.isHighlighted = state;
    }

    public void drawFillColor(Graphics2D g2d) {
        g2d.setColor(this.getColor());
        g2d.fill(this);
        g2d.setColor(Color.BLACK);
        g2d.draw(this);
    }

    /**
     * Get the highlighted state of the square space.
     *
     * @return The highlighted state of the square space.
     */
    public boolean getState() {
        return this.isHighlighted;
    }

    /**
     * Draw the owner of the square space.
     * The owner is represented by a small white or black circle inside the square
     * space.
     *
     * @param g2d The graphics object.
     */
    public void drawSpaceOwner(Graphics2D g2d) {

        // if the cell type is not empty, draw the owner
        if (!representingCell.sameCellType(CellType.Empty)) {

            Path2D.Double circlePath = new Path2D.Double();

            // Define the radius for the small circle
            double circleRadius = this.size * 0.45;

            // Construct circle path
            circlePath.moveTo(this.currentX + (this.size / 2) + circleRadius, this.currentY + (this.size / 2));

            for (int i = 0; i <= 360; i++) {
                double rad = Math.toRadians(i);
                double xCircle = this.currentX + (this.size / 2) + circleRadius * Math.cos(rad);
                double yCircle = this.currentY + (this.size / 2) + circleRadius * Math.sin(rad);
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
    public double getCurrentX() {
        return this.currentX;
    }

    public double getCurrentY() {
        return this.currentY;
    }

}

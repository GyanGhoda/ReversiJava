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
    private Cell representingCell;

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

        this.constructsquare();
    }

    /**
     * Constructs the square.
     */
    private void constructsquare() {

        for (int i = 0; i < 4; i++) {
            // Calculate the angle, dividing by 4 for a square
            double angle = 2 * Math.PI / 4 * i + Math.PI / 4;
            // Calculate the x and y points
            double xPoint = this.currentX + this.size * Math.cos(angle);
            double yPoint = this.currentY + this.size * Math.sin(angle);

            if (i == 0) {
                moveTo(xPoint, yPoint); // Move to the first point
            } else {
                lineTo(xPoint, yPoint); // Draw line to next point
            }
        }

        this.closePath(); // Close the path to complete the square

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
     */
    public void setState(boolean state) {
        this.isHighlighted = state;
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
    public void drawSpaceOwner(Graphics2D g2d, boolean hints, int score) {

        if (hints && this.isHighlighted) {
            FontMetrics fm = g2d.getFontMetrics();
            double centerX = this.currentX - fm.stringWidth(Integer.toString(score));
            double centerY = this.currentY + fm.getHeight() / 4;
            g2d.setFont(new Font("Serif", Font.BOLD, (int) this.size / 2));
            g2d.drawString(Integer.toString(score), (int) centerX, (int) centerY);
        }

        // if the cell type is not empty, draw the owner
        if (!representingCell.sameCellType(CellType.Empty)) {

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

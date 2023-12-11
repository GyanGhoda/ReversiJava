package cs3500.reversi.visualview;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

import cs3500.reversi.model.Cell;
import cs3500.reversi.model.CellType;

/**
 * Represents a square space in the Reversi game board.
 */
public class SquareSpace extends ASpace {

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
    super(size, currentX, currentY, representingCell);

    this.constructSquare();
  }

  /**
   * Constructs the square.
   */
  private void constructSquare() {
    // Start at the top left corner of the square
    moveTo(this.getCurrentX(), this.getCurrentY());

    // Draw line to the top right corner
    lineTo(this.getCurrentX() + this.getSize(), this.getCurrentY());

    // Draw line to the bottom right corner
    lineTo(this.getCurrentX() + this.getSize(), this.getCurrentY() + this.getSize());

    // Draw line to the bottom left corner
    lineTo(this.getCurrentX(), this.getCurrentY() + this.getSize());

    // Close the path to complete the square
    closePath();
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
    if (!this.getRepresentingCell().sameCellType(CellType.Empty)) {

      Path2D.Double circlePath = new Path2D.Double();

      // Define the radius for the small circle
      double circleRadius = this.getSize() * 0.45;

      // Construct circle path
      circlePath.moveTo(this.getCurrentX() + (this.getSize() / 2) + circleRadius,
              this.getCurrentY() + (this.getSize() / 2));

      for (int i = 0; i <= 360; i++) {
        double rad = Math.toRadians(i);
        double xCircle = this.getCurrentX() + (this.getSize() / 2) + circleRadius * Math.cos(rad);
        double yCircle = this.getCurrentY() + (this.getSize() / 2) + circleRadius * Math.sin(rad);
        circlePath.lineTo(xCircle, yCircle);
      }

      circlePath.closePath();

      // Set color and fill the circle
      if (this.getRepresentingCell().getCellOwner().equals("X")) {
        g2d.setColor(Color.BLACK);
      } else {
        g2d.setColor(Color.WHITE);
      }

      g2d.fill(circlePath);
    }
  }
}
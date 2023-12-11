package cs3500.reversi.visualview;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

import cs3500.reversi.model.Cell;
import cs3500.reversi.model.CellType;

/**
 * Represents a hexagon space in the Reversi game board.
 */
public class HexagonSpace extends ASpace {

  /**
   * Constructs a new HexagonSpace with the given size, currentX, currentY, and
   * representingCell.
   *
   * @param size             The size of the hexagon space.
   * @param currentX         The current x coordinate of the hexagon space.
   * @param currentY         The current y coordinate of the hexagon space.
   * @param representingCell The cell that the hexagon space represents.
   */
  public HexagonSpace(double size, double currentX, double currentY, Cell representingCell) {
    super(size, currentX, currentY, representingCell);

    this.constructHexagon();
  }

  /**
   * Constructs the hexagon.
   */
  private void constructHexagon() {

    for (int i = 0; i < 6; i++) {

      double angle = 2 * Math.PI / 6 * i + Math.PI / 2;
      double xPoint = this.getCurrentX() + this.getSize() * Math.cos(angle);
      double yPoint = this.getCurrentY() + this.getSize() * Math.sin(angle);

      if (i == 0) {
        moveTo(xPoint, yPoint);
      } else {
        lineTo(xPoint, yPoint);
      }
    }

    this.closePath();
  }

  /**
   * Draw the owner of the hexagon space.
   * The owner is represented by a small white or black circle inside the hexagon
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
      circlePath.moveTo(this.getCurrentX() + circleRadius, this.getCurrentY());

      for (int i = 0; i <= 360; i++) {
        double rad = Math.toRadians(i);
        double xCircle = this.getCurrentX() + circleRadius * Math.cos(rad);
        double yCircle = this.getCurrentY() + circleRadius * Math.sin(rad);
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
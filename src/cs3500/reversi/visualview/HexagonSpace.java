package cs3500.reversi.visualview;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

import cs3500.reversi.model.Cell;
import cs3500.reversi.model.CellType;

/**
 * Represents a hexagon space in the Reversi game board.
 */
public class HexagonSpace extends Path2D.Double {

  private final double size;
  private final double currentX;
  private final double currentY;
  private boolean isHighlighted;
  private Cell representingCell;

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
    this.size = size;
    this.currentX = currentX;
    this.currentY = currentY;
    this.isHighlighted = false;
    this.representingCell = representingCell;

    this.constructHexagon();
  }

  /**
   * Constructs the hexagon.
   */
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

  /**
   * Get the size of the hexagon space.
   *
   * @return The size of the hexagon space.
   */
  public Color getColor() {
    if (this.isHighlighted) {
      return Color.CYAN;
    } else {
      return Color.LIGHT_GRAY;
    }
  }

  /**
   * Change the highlighted state of the hexagon space.
   */
  public void setState(boolean state) {
    this.isHighlighted = state;
  }

  /**
   * Get the highlighted state of the hexagon space.
   *
   * @return The highlighted state of the hexagon space.
   */
  public boolean getState() {
    return this.isHighlighted;
  }

  /**
   * Draw the owner of the hexagon space.
   * The owner is represented by a small white or black circle inside the hexagon space.
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

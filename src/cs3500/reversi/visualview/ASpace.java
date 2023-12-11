package cs3500.reversi.visualview;

import java.awt.*;
import java.awt.geom.Path2D;

import cs3500.reversi.model.Cell;

public abstract class ASpace extends Path2D.Double implements ISpace {
  private final double size;
  private final double currentX;
  private final double currentY;
  private boolean isHighlighted;
  private final Cell representingCell;

  public ASpace(double size, double currentX, double currentY, Cell representingCell) {
    this.size = size;
    this.currentX = currentX;
    this.currentY = currentY;
    this.isHighlighted = false;
    this.representingCell = representingCell;
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

  /**
   * Draws the fill color of  this hexagon space.
   *
   * @param g2d - The graphics to draw on
   */
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

  public double getCurrentX() {
    return this.currentX;
  }

  public double getCurrentY() {
    return this.currentY;
  }

  protected Cell getRepresentingCell() {
    return this.representingCell;
  }

  protected double getSize() {
    return this.size;
  }
}

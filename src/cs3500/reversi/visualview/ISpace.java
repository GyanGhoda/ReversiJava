package cs3500.reversi.visualview;

import java.awt.*;

public interface ISpace {
  /**
   * Get the color of the ISpace.
   *
   * @return The color of the ISpace.
   */
  Color getColor();

  /**
   * Change the highlighted state of the ISpace.
   */
  void setState(boolean state);

  /**
   * Get the highlighted state of the ISpace.
   *
   * @return The highlighted state of the ISpace.
   */
  boolean getState();

  /**
   * Gets the current X of this ISpace
   *
   * @return the current x coordinate as a double
   */
  double getCurrentX();

  /**
   * Gets the current Y of this ISpace
   *
   * @return the current y coordinate as a double
   */
  double getCurrentY();

  /**
   * Draw the owner of the ISpace.
   * The owner is represented by a small white or black circle inside the hexagon
   * space.
   *
   * @param g2d The graphics object.
   */
  void drawSpaceOwner(Graphics2D g2d);
}

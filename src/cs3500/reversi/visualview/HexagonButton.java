package cs3500.reversi.visualview;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Represents a hexagonal button.
 */
public class HexagonButton extends JButton {

  private Polygon hexagon; // The hexagon shape of the button
  private boolean isClicked = false; // Whether the button is clicked or not

  /**
   * Constructs a new HexagonButton with the given label.
   *
   * @param label The label of the button.
   */
  public HexagonButton(String label) {
    super(label);
    setOpaque(false); // Make the button transparent
    setContentAreaFilled(false); // Make the button content area transparent
    setBorderPainted(false); // Remove the border

    addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // Button action logic
      }
    });

    // Add a mouse listener to the button to change the color when clicked
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (isPointInHexagon(e.getPoint())) {
          isClicked = !isClicked;
          repaint();
        }
      }
    });
  }

  /**
   * Paints the button.
   *
   * @param g The graphics object.
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g); // Paint the button normally
    hexagon = createHexagon(getWidth(), getHeight()); // Create the hexagon shape

    Graphics2D g2d = (Graphics2D) g;
    if (isClicked) {
      g2d.setColor(Color.CYAN);
    } else {
      g2d.setColor(Color.BLACK);
    }
    g2d.fill(hexagon); // Fill the hexagon shape
  }

  private boolean isPointInHexagon(Point point) {
    return hexagon.contains(point);
  }

  // Creates a hexagon shape with the given width and height
  private Polygon createHexagon(int width, int height) {
    int centerX = width / 2;
    int centerY = height / 2;
    int size = Math.min(width, height) / 2;

    int[] xPoints = new int[6];
    int[] yPoints = new int[6];

    // Calculate the points of the hexagon
    for (int i = 0; i < 6; i++) {
      double angle = 2 * Math.PI / 6 * i;
      xPoints[i] = (int) (centerX + size * Math.cos(angle));
      yPoints[i] = (int) (centerY + size * Math.sin(angle));
    }

    return new Polygon(xPoints, yPoints, 6);
  }

}

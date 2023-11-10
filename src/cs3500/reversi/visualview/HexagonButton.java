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

  private int size;

  /**
   * Constructs a new HexagonButton with the given label.
   *
   * @param label The label of the button.
   */
  public HexagonButton(String label, int size) {
    super(label);
    setOpaque(false); // Make the button transparent
    setContentAreaFilled(true); // Make the button content area transparent
    setBorderPainted(true); // Remove the border

    this.size = size;

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
    hexagon = createHexagon(); // Create the hexagon shape

    Graphics2D g2d = (Graphics2D) g;

    int centerX = getWidth() / 2;
    int centerY = getHeight() / 2;

    g2d.rotate(Math.toRadians(90), centerX, centerY);

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
  private Polygon createHexagon() {
    int centerX = this.size * (3 / 2) / 2;
    int centerY = this.size / 2;

    int[] xPoints = new int[6];
    int[] yPoints = new int[6];

    // Calculate the points of the hexagon
    for (int i = 0; i < 6; i++) {
      double angle = 2 * Math.PI / 6 * i;
      xPoints[i] = (int) (centerX + (this.size / 2) * Math.cos(angle));
      yPoints[i] = (int) (centerY + (this.size / 2) * Math.sin(angle));
    }

    return new Polygon(xPoints, yPoints, 6);
  }

}

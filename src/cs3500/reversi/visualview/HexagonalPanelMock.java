package cs3500.reversi.visualview;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.JPanel;

import cs3500.reversi.controller.PlayerActionFeatures;
import cs3500.reversi.model.PositionAxial;
import cs3500.reversi.model.ReadOnlyReversiModel;

/**
 * Represents a panel of hexagonal buttons.
 */
public class HexagonalPanelMock extends JPanel implements ReversiPanel {
  private final HashMap<PositionAxial, HexagonSpace> hexagonButtons = new HashMap<>();
  private final ReadOnlyReversiModel model;
  private int width;
  private int height;
  private PlayerActionFeatures features;

  private final StringBuilder log;

  /**
   * Constructs a new HexagonalPanel with the given number of rows and columns.
   *
   * @param model  The model to render.
   * @param width  The width of the panel.
   * @param height The height of the panel.
   */
  public HexagonalPanelMock(ReadOnlyReversiModel model, int width, int height) {

    setLayout(null); // Use absolute positioning
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
    this.width = width;
    this.height = height;
    this.log = new StringBuilder();

    addMouseListener(new MouseListenerReversi());
    addKeyListener(new KeyListenerReversi());
    addComponentListener(new ComponentListenerReversi());

    // Set the panel to be focusable
    setFocusable(true);
    requestFocusInWindow();

    this.initializeHexagons();
  }

  @Override
  public void setUpFeatures(PlayerActionFeatures features) {
    this.features = features;
  }

  /**
   * Refreshes the panel.
   *
   * @param currentTurn - whether or not it is the current player's turn
   */
  @Override
  public void refresh(boolean currentTurn) {
    this.initializeHexagons();
    repaint();
  }

  @Override
  public double getCurrentX() {
    return 0;
  }

  @Override
  public double getCurrentY() {
    return 0;
  }

  @Override
  public int getScore() {
    return 0;
  }

  @Override
  public void toggleHints() {
    //Do nothing as this does not handle hints
  }

  @Override
  public void resizeComponent() {
    //Do nothing as this does not handle resize
  }

  /**
   * Initializes the hexagons in the panel.
   */
  private void initializeHexagons() {
    int distance = Math.min(this.width, this.height) / this.model.getNumRows();
    double buttonSize = (distance / Math.sqrt(3));

    // Total grid width and height
    double totalGridWidth = (this.model.getNumRows()) * buttonSize * Math.sqrt(3);
    double totalGridHeight = (this.model.getNumRows() * 1.5 + 0.5) * buttonSize;

    // Calculate offsets to center the grid
    double offsetX = (this.width - totalGridWidth) / 2;
    double offsetY = (this.height - totalGridHeight) / 2;

    int middleY = (this.model.getNumRows() - 1) / 2;
    int currentRowStartingQ = 0;
    int currentRowStartingS = middleY;
    int currentR = -middleY;

    // create the hexagons for each row
    for (int rowsMade = 0; rowsMade < this.model.getNumRows(); rowsMade += 1) {
      int currentQ = currentRowStartingQ;

      // Adjust starting positions with offsets
      double startingX = offsetX + (((buttonSize * Math.sqrt(3)) / 2) * Math.abs(currentR))
              + (((buttonSize * Math.sqrt(3))) / 2);
      double startingY = offsetY + ((buttonSize * 3) / 2) * (rowsMade + 1);

      for (int currentS = currentRowStartingS; currentS >= currentRowStartingQ; currentS -= 1) {
        // create a new position for the current hexagon
        PositionAxial posn = new PositionAxial(currentQ, currentR, currentS);

        // create a new hexagon button
        HexagonSpace hexagon = new HexagonSpace(buttonSize, startingX, startingY,
                this.model.getCellAt(posn));

        // create empty cell and add it to the board at the current poisiton
        hexagonButtons.put(posn, hexagon);
        hexagon.moveTo(startingX, startingY);

        startingX += Math.sqrt(3) * buttonSize;
        currentQ += 1;
      }

      if (rowsMade < middleY) {
        currentRowStartingQ -= 1;
      } else {
        currentRowStartingS -= 1;
      }
      currentR += 1;
    }
  }

  /**
   * Paints the panel.
   *
   * @param g The graphics object.
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;

    g2d.setColor(Color.darkGray);
    g2d.fill(new Rectangle(this.width, this.height));

    // Draw the hexagons
    for (HexagonSpace hexagon : hexagonButtons.values()) {
      g2d.setColor(hexagon.getColor());
      g2d.fill(hexagon);
      g2d.setColor(Color.BLACK);
      g2d.draw(hexagon);

      hexagon.drawSpaceOwner(g2d);
    }
  }

  /**
   * Updates the panel according to mouse actions.
   */
  private class MouseListenerReversi implements MouseListener {

    /**
     * Handles mouse clicks.
     *
     * @param e The mouse event.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
      mouseClickUpdateView(e.getX(), e.getY());
    }

    // Other mouse event methods...
    @Override
    public void mousePressed(MouseEvent e) {
      // TODO Auto-generated method stub
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      // TODO Auto-generated method stub
    }

    @Override
    public void mouseEntered(MouseEvent e) {
      // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent e) {
      // TODO Auto-generated method stub
    }
  }

  /**
   * Updates the panel according to key actions.
   */
  private class KeyListenerReversi implements KeyListener {

    /**
     * Handles key presses.
     *
     * @param e The key event.
     */
    @Override
    public void keyPressed(KeyEvent e) {
      keyClickUpdateView(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {
      // TODO Auto-generated method stub
    }

    @Override
    public void keyReleased(KeyEvent e) {
      // TODO Auto-generated method stub
    }
  }

  private class ComponentListenerReversi implements ComponentListener {

    @Override
    public void componentResized(ComponentEvent e) {
      // Update the panel size
      width = getWidth();
      height = getHeight();

      hexagonButtons.clear(); // Clear existing hexagons
      initializeHexagons(); // Reinitialize hexagons with the new size
      repaint(); // Repaint the panel
    }

    @Override
    public void componentMoved(ComponentEvent e) {
      // TODO Auto-generated method stub
    }

    @Override
    public void componentShown(ComponentEvent e) {
      // TODO Auto-generated method stub
    }

    @Override
    public void componentHidden(ComponentEvent e) {
      // TODO Auto-generated method stub
    }
  }

  // handles all keyboard clicks when playing
  private void keyClickUpdateView(int keyCode) {
    if (this.features != null) {
      // Check if the user has pressed the 'm' key, which makes a move
      if (keyCode == KeyEvent.VK_M) {
        this.log.append("User has pressed m key to move");
        for (HashMap.Entry<PositionAxial, HexagonSpace> entry : hexagonButtons.entrySet()) {
          HexagonSpace hexagon = entry.getValue();
          if (hexagon.getState()) {
            features.moveToCoordinate(entry.getKey());
          }
        }
      }

      // Check if the user has pressed the 'p' key, which passes the turn
      if (keyCode == KeyEvent.VK_P) {
        this.log.append("User has pressed p key to pass");
      }

      repaint();
    }
  }

  /**
   * Updates the view according to mouse clicks.
   * 
   * @param mouseX - the x coordinate of the mouse click
   * @param mouseY - the y coordinate of the mouse click
   */
  public void mouseClickUpdateView(int mouseX, int mouseY) {
    if (this.features != null) {
      // Check if the mouse click is inside a hexagon and highlight it accordingly
      for (HashMap.Entry<PositionAxial, HexagonSpace> entry : hexagonButtons.entrySet()) {
        HexagonSpace hexagon = entry.getValue();

        // print out the coordinates of the hexagon that was clicked on
        if (hexagon.contains(mouseX, mouseY) && !hexagon.getState()) {
          this.log.append("Clicked on hexagon at:\nQ: " + entry.getKey().getQ()
                  + "\nR: " + entry.getKey().getR() + "\nS: " + entry.getKey().getS());
          hexagon.setState(!hexagon.getState());
        } else {
          hexagon.setState(false);
        }
      }
      repaint();
    }
  }

  @Override
  public boolean playerSelected() {
    return false;
  }

  /**
   * Returns the log as a String.
   *
   * @return the log as a String.
   */
  public String getLog() {
    return log.toString();
  }

  /**
   * Induces a mouse event of our choice in this mock for testing purposes.
   *
   * @param mouseX - the mouse event's X coordinate.
   * @param mouseY - the mouse event's Y coordinate.
   */
  public void induceMouseEvent(int mouseX, int mouseY) {
    this.mouseClickUpdateView(mouseX, mouseY);
  }

  /**
   * Induces a key event of our choice in this mock for testing purposes.
   *
   * @param keyCode - the keycode of the event's key.
   */
  public void induceKeyEvent(int keyCode) {
    this.keyClickUpdateView(keyCode);
  }
}
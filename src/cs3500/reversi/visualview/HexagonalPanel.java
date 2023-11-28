package cs3500.reversi.visualview;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cs3500.reversi.controller.PlayerActionFeatures;
import cs3500.reversi.controller.PlayerType;
import cs3500.reversi.model.PositionAxial;
import cs3500.reversi.model.ReadOnlyReversiModel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.FontMetrics;
import java.awt.event.ComponentListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Represents a panel of hexagonal buttons.
 */
public class HexagonalPanel extends JPanel implements ReversiPanel {
  private final ConcurrentHashMap<PositionAxial, HexagonSpace> hexagonButtons;
  private ReadOnlyReversiModel model;
  private int width;
  private int height;
  private PlayerActionFeatures features;
  private PositionAxial selectedPosn;
  private boolean currentTurn;

  /**
   * Constructs a new HexagonalPanel with the given number of rows and columns.
   *
   * @param model  The model to render.
   * @param width  The width of the panel.
   * @param height The height of the panel.
   */
  public HexagonalPanel(ReadOnlyReversiModel model, int width, int height) {

    setLayout(null); // Use absolute positioning
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }

    this.model = model;
    this.width = width;
    this.height = height;
    this.currentTurn = false;
    this.hexagonButtons = new ConcurrentHashMap<PositionAxial, HexagonSpace>();
    this.initializeHexagons();

    addMouseListener(new MouseListenerReversi());
    addKeyListener(new KeyListenerReversi());
    addComponentListener(new ComponentListenerReversi());

    // Set the panel to be focusable
    setFocusable(true);
    requestFocusInWindow();
  }

  /**
   * Sets up the features of the panel.
   *
   * @param features - the features to set up
   */
  @Override
  public void setUpFeatures(PlayerActionFeatures features) {
    this.features = features;
  }

  /**
   * Initializes the hexagons in the panel.
   */
  private void initializeHexagons() {
    HashMap<PositionAxial, HexagonSpace> newHexagonButtons = new HashMap<>();

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
        newHexagonButtons.put(posn, hexagon);
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

    this.hexagonButtons.clear();
    this.hexagonButtons.putAll(newHexagonButtons);
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

    if (this.model.hasGameStarted()) {
      // Calculate new font size based on panel size
      int fontSize = Math.max(10, Math.min(width, height) / 30);
      int topY = this.height - (this.height - fontSize);

      g2d.setFont(new Font("Serif", Font.BOLD, fontSize));
      FontMetrics fm = g2d.getFontMetrics();
      g2d.setColor(Color.BLACK);
      g2d.drawString("Player: " + this.features.getPlayer(), 0, topY);

      String turnString = "";

      if (!this.model.isGameOver()) {
        if (this.currentTurn) {
          turnString = "It is your turn!";
        } else {
          turnString = "Please wait for the other player.";
        }
      }

      int centerX = this.width / 2 - fm.stringWidth(turnString) / 2;
      g2d.drawString(turnString, centerX, topY);

      String blackScore = "Black: " + this.model.getCurrentScore(PlayerType.BLACK);
      String whiteScore = "White: " + this.model.getCurrentScore(PlayerType.WHITE);

      g2d.drawString(blackScore, this.width - fm.stringWidth(blackScore), topY);
      g2d.drawString(whiteScore, this.width - fm.stringWidth(whiteScore), topY * 2);

      if (this.model.isGameOver()) {
        String winner = this.model.getCurrentWinner();
        String gameOverString = "Game is Over. Winner: " + winner;

        fontSize = Math.max(10, Math.min(width, height) / 20);

        g2d.setFont(new Font("Serif", Font.BOLD, fontSize));
        fm = g2d.getFontMetrics();

        centerX = this.width / 2 - fm.stringWidth(gameOverString) / 2;

        g2d.drawString(gameOverString, centerX, this.height / 2);
      }
    }
  }

  /**
   * Refreshes the panel.
   *
   * @param currentTurn - whether or not it is the current player's turn
   */
  public void refresh(boolean currentTurn) {
    this.currentTurn = currentTurn;
    this.initializeHexagons();
    repaint();
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
      requestFocusInWindow();
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
        try {
          this.features.moveToCoordinate(selectedPosn);
        } catch (IllegalStateException e) {
          JOptionPane.showMessageDialog(this, e.getMessage(), "Illegal Move Made",
              JOptionPane.ERROR_MESSAGE);
        }
      }
      // Check if the user has pressed the 'p' key, which passes the turn
      if (keyCode == KeyEvent.VK_P) {
        try {
          this.features.passTurn();
        } catch (IllegalStateException e) {
          JOptionPane.showMessageDialog(this, e.getMessage(), "Illegal Move Made",
              JOptionPane.ERROR_MESSAGE);
        }
      }

      repaint();
    }
  }

  // handles all mouse clicks when playing
  private void mouseClickUpdateView(int mouseX, int mouseY) {
    if (this.features != null) {
      // Check if the mouse click is inside a hexagon and highlight it accordingly
      for (HashMap.Entry<PositionAxial, HexagonSpace> entry : hexagonButtons.entrySet()) {
        HexagonSpace hexagon = entry.getValue();

        // print out the coordinates of the hexagon that was clicked on
        if (hexagon.contains(mouseX, mouseY) && !hexagon.getState()) {
          System.out.println("Clicked on hexagon at:\nQ: " + entry.getKey().getQ()
              + "\nR: " + entry.getKey().getR() + "\nS: " + entry.getKey().getS());
          hexagon.setState(!hexagon.getState());
          selectedPosn = entry.getKey();
        } else if (hexagon.contains(mouseX, mouseY)) {
          hexagon.setState(!hexagon.getState());
          selectedPosn = null;
        } else {
          hexagon.setState(false);
        }
      }
      repaint();
    }
  }
}
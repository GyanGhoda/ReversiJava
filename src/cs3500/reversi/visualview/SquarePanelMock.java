package cs3500.reversi.visualview;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cs3500.reversi.controller.PlayerActionFeatures;
import cs3500.reversi.controller.PlayerType;
import cs3500.reversi.model.Position2D;
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
 * Represents a panel of square buttons.
 */
public class SquarePanelMock extends JPanel implements ReversiPanel {
  private final ConcurrentHashMap<Position2D, SquareSpace> squareButtons;
  private ReadOnlyReversiModel model;
  private int width;
  private int height;
  private PlayerActionFeatures features;
  private Position2D selectedPosn;
  private boolean currentTurn;
  private boolean hints;
  private final StringBuilder log;

  /**
   * Constructs a new SquarePanel with the given number of rows and columns.
   *
   * @param model  The model to render.
   * @param width  The width of the panel.
   * @param height The height of the panel.
   */
  public SquarePanelMock(ReadOnlyReversiModel model, int width, int height) {

    setLayout(null); // Use absolute positioning
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }

    this.model = model;
    this.width = width;
    this.height = height;
    this.currentTurn = false;
    this.hints = false;
    this.squareButtons = new ConcurrentHashMap<Position2D, SquareSpace>();
    this.log = new StringBuilder();
    this.initializeSquares();

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
   * Initializes the squares in the panel.
   */
  private void initializeSquares() {
    HashMap<Position2D, SquareSpace> newSquareButtons = new HashMap<>();

    int buttonSize = Math.min(this.width, this.height) / this.model.getNumRows();

    // Total grid width and height
    double totalGridWidth = (this.model.getNumRows()) * buttonSize;

    // Calculate offset to center the grid
    double offsetX = (this.width - totalGridWidth) / 2;
    double offsetY = (this.height - totalGridWidth) / 2;

    double centerX = offsetX;
    double centerY = offsetY;

    // Create the squares
    for (int y = 0; y < this.model.getNumRows(); y += 1) {
      for (int x = 0; x < this.model.getNumRows(); x += 1) {
        Position2D posn = new Position2D(x, y);
        SquareSpace square = new SquareSpace(buttonSize, centerX, centerY,
                this.model.getCellAt(posn));

        square.moveTo(centerX, centerY);
        newSquareButtons.put(posn, square);
        centerX += buttonSize;
      }
      centerX = offsetX;
      centerY += buttonSize;
    }

    this.squareButtons.clear();
    this.squareButtons.putAll(newSquareButtons);
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

    // Draw the squares
    for (HashMap.Entry<Position2D, SquareSpace> entry : squareButtons.entrySet()) {
      SquareSpace square = entry.getValue();
      Position2D posn = entry.getKey();

      square.drawFillColor(g2d);

      if (this.model.hasGameStarted()) {
        square.drawSpaceOwner(g2d);
      }
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

      // Draw the game over message
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
    this.initializeSquares();
    repaint();
  }

  @Override
  public double getCurrentX() {
    if (this.model.hasGameStarted()) {
      return this.squareButtons.get(selectedPosn).getCurrentX();
    }
    return 0;
  }

  @Override
  public double getCurrentY() {
    if (this.model.hasGameStarted()) {
      return this.squareButtons.get(selectedPosn).getCurrentY();
    }
    return 0;
  }

  @Override
  public int getScore() {
    if (this.model.hasGameStarted()) {
      return this.model.getScoreForMovePlayer(selectedPosn, this.features.getPlayer());
    }
    return 0;
  }

  @Override
  public void toggleHints() {
    //Do nothing as this does not handle hints
  }

  @Override
  public void resizeComponent() {
    // Update the panel size
    width = getWidth();
    height = getHeight();

    squareButtons.clear(); // Clear existing hexagons
    this.initializeSquares(); // Reinitialize hexagons with the new size
    repaint(); // Repaint the panel
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

      squareButtons.clear(); // Clear existing squares
      initializeSquares(); // Reinitialize squares with the new size
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
        try {
          // If there is a selectedPosn, then attempt to move to it. Null is used as null
          // represents
          // the lack of a posn selected by a player.
          if (selectedPosn != null) {
            this.features.moveToCoordinate(selectedPosn);
          }
        } catch (IllegalStateException e) {
          JOptionPane.showMessageDialog(this, e.getMessage(), "Illegal Move Made",
                  JOptionPane.ERROR_MESSAGE);
        }
      }
      // Check if the user has pressed the 'p' key, which passes the turn
      if (keyCode == KeyEvent.VK_P) {
        this.log.append("User has pressed p key to pass");
        try {
          this.features.passTurn();
        } catch (IllegalStateException e) {
          JOptionPane.showMessageDialog(this, e.getMessage(), "Illegal Move Made",
                  JOptionPane.ERROR_MESSAGE);
        }
      }

      // Check if the user has pressed the 'h' key, which toggles hints
      if (keyCode == KeyEvent.VK_H) {
        this.log.append("User has pressed h key to toggle hints");
        this.hints = !this.hints;
      }

      repaint();
    }
  }

  /*
   * Updates the view according to mouse clicks.
   * 
   * @param mouseX - the x coordinate of the mouse click
   * @param mouseY - the y coordinate of the mouse click
   */
  public void mouseClickUpdateView(int mouseX, int mouseY) {
    if (this.features != null) {
      // Check if the mouse click is inside a square and highlight it accordingly
      for (HashMap.Entry<Position2D, SquareSpace> entry : squareButtons.entrySet()) {
        SquareSpace square = entry.getValue();

        // print out the coordinates of the square that was clicked on
        if (square.contains(mouseX, mouseY) && !square.getState()) {
          if (this.hints) {
            square.setState(!square.getState());
            this.log.append("Clicked on square at " + entry.getKey().toString() + "\n");
          } else {
            square.setState(!square.getState());
            this.log.append("Clicked on square at " + entry.getKey().toString() + "\n");
          }
          selectedPosn = entry.getKey();
        } else if (square.contains(mouseX, mouseY)) {
          square.setState(!square.getState());
          selectedPosn = null;
        } else {
          square.setState(false);
        }
      }
      repaint();
    }
  }

  @Override
  public boolean playerSelected() {
    return this.selectedPosn != null;
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
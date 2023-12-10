package cs3500.reversi.visualview;

import java.awt.*;

import javax.swing.*;

import cs3500.reversi.controller.PlayerActionFeatures;
import cs3500.reversi.model.ReadOnlyReversiModel;

/**
 * Represents a frame of hexagonal buttons.
 */
public class HexagonalFrame extends JFrame implements ReversiVisualView {
  private final ReversiPanel panel;

  /**
   * Constructs a frame of hexagonal buttons.
   *
   * @param model the model to render
   */
  public HexagonalFrame(ReadOnlyReversiModel model, boolean hints) {
    setTitle("2 Player Reversi Game"); // Set the title
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the program when the frame is closed
    setSize(800, 800); // Set the size of the frame
    setLocationRelativeTo(null); // Center the frame
    setVisible(true); // Make the frame visible

    HexagonalPanel hexagonalPanel;

    if (hints) {
      hexagonalPanel = new HexagonalPanel(model, 800, 800, true);
      this.panel = new HintDecorator(hexagonalPanel);
    }
    else {
      hexagonalPanel = new HexagonalPanel(model, 800, 800, false);
      this.panel = hexagonalPanel;
    }

    this.add((Component) this.panel);
  }

  /**
   * Makes the frame visible.
   */
  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  /**
   * Refreshes the view.
   *
   * @param currentTurn - whether or not it is the current player's turn
   */
  @Override
  public void refresh(boolean currentTurn) {
    this.panel.refresh(currentTurn);
  }

  /**
   * Sets up the features of the view.
   *
   * @param features - the features to set up
   */
  @Override
  public void setUpFeatures(PlayerActionFeatures features) {
    this.panel.setUpFeatures(features);
  }
}
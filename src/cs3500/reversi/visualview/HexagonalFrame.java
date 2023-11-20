package cs3500.reversi.visualview;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import cs3500.reversi.controller.Features;
import cs3500.reversi.model.ReadOnlyReversiModel;

/**
 * Represents a frame of hexagonal buttons.
 */
public class HexagonalFrame extends JFrame implements ReversiVisualView {

  private final ReadOnlyReversiModel model;
  private final HexagonalPanel panel;

  /**
   * Constructs a frame of hexagonal buttons.
   *
   * @param model the model to render
   */
  public HexagonalFrame(ReadOnlyReversiModel model) {
    this.model = model;
    setTitle("2 Player Reversi Game"); // Set the title
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the program when the frame is closed
    setSize(800, 800); // Set the size of the frame
    setLocationRelativeTo(null); // Center the frame
    setVisible(true); // Make the frame visible

    this.panel = new HexagonalPanel(this.model, 800, 800);
    this.render(); // Render the frame
  }

  /**
   * Renders the frame.
   */
  private void render() {
    this.panel.setBounds(400, 400, 800, 800);
    add(panel, BorderLayout.CENTER);
  }

  /**
   * Makes the frame visible.
   */
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void refresh(boolean currentTurn, boolean isGameOver) {
    this.panel.refresh(currentTurn, isGameOver);
  }

  @Override
  public void setUpFeatures(Features features) {
    this.panel.setUpFeatures(features);
  }
}

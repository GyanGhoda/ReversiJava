package cs3500.reversi.visualview;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
  public HexagonalFrame(ReadOnlyReversiModel model) {
    setTitle("2 Player Reversi Game"); // Set the title
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the program when the frame is closed
    setSize(800, 800); // Set the size of the frame
    setLocationRelativeTo(null); // Center the frame
    setVisible(true); // Make the frame visible

    HexagonalPanel hexagonalPanel = new HexagonalPanel(model, 800, 800);
    HintDecorator hintDecorator = new HintDecorator(hexagonalPanel);

    this.panel = hintDecorator;

    this.add(hintDecorator);

    this.setFocusable(true);
    this.requestFocusInWindow();

    this.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'h') {
          hintDecorator.toggleHints();
          hintDecorator.repaint();
        }
      }
    });
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

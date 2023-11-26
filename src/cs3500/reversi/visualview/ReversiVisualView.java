package cs3500.reversi.visualview;

import cs3500.reversi.controller.PlayerActionFeatures;

/**
 * Represents a visual view of the Reversi game.
 * Describes what the frame should be capable of doing, which
 * includes rendering and making the view visible.
 */
public interface ReversiVisualView {
  /**
   * Makes the view visible.
   */
  void makeVisible();

  /**
   * Refreshes the view.
   *
   * @param currentTurn - whether or not it is the current player's turn
   */
  void refresh(boolean currentTurn);

  /**
   * Sets up the features of the view.
   *
   * @param features - the features to set up
   */
  void setUpFeatures(PlayerActionFeatures features);
}

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

  void refresh(boolean currentTurn);

  void setUpFeatures(PlayerActionFeatures features);
}

package cs3500.reversi.visualview;

import cs3500.reversi.controller.PlayerActionFeatures;

/**
 * Represents the panel for the Reversi game.
 * Describes what the panel should be capable of doing.
 */
public interface ReversiPanel {

  /**
   * Sets up the features of the panel.
   *
   * @param features - the features to set up
   */
  void setUpFeatures(PlayerActionFeatures features);

  /**
   * Refreshes the panel.
   *
   * @param currentTurn - whether or not it is the current player's turn
   */
  public void refresh(boolean currentTurn);
}

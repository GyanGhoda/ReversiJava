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
  void refresh(boolean currentTurn);

  /**
   * Gets the current x of the selected position by the user.
   *
   * @return - Get the current x as an int of the selected position
   */
  double getCurrentX();

  /**
   * Gets the current y of the selected position by the user.
   *
   * @return - Get the current y as an int of the selected position
   */
  double getCurrentY();

  /**
   * Gets the score of the move of the selected position by the user.
   *
   * @return - the score of the move by the selected user
   */
  int getScore();

  /**
   * Toggles the hints feature on and off using the 'h' key.
   */
  void toggleHints();

  /**
   * Resizes the component in case of a window size change.
   */
  void resizeComponent();

  /**
   * Updates the view when the mouse is clicked by the user.
   *
   * @param mouseX - the x coordinate of the mouse click
   * @param mouseY - the y coordinate of the mouse click
   */
  void mouseClickUpdateView(int mouseX, int mouseY);

  /**
   * Checks if the player has selected a position.
   *
   * @return - true if the user has a position selected, false otherwise.
   */
  boolean playerSelected();
}
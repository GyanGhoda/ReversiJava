package cs3500.reversi.visualview;

import java.awt.*;

import javax.swing.*;

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

  double getCurrentX();

  double getCurrentY();

  int getScore();

  void toggleHints();

  void resizeComponent();

  void mouseClickUpdateView(int mouseX, int mouseY);
}
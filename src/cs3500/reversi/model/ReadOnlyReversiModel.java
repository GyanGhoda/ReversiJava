package cs3500.reversi.model;

import cs3500.reversi.controller.PlayerType;

/**
 * Represents a read-only version of the ReversiModel with only observation methods.
 * Observation methods include the following:
 * getNumRows, getCellAt, isGameOver, and getCurrentScore.
 */
public interface ReadOnlyReversiModel {

  /**
   * Gets the number of rows on the game board.
   *
   * @return The number of rows in the game board.
   */
  int getNumRows();

  /**
   * Gets the cell at the specified coordinates on the game board using a PositionAxial.
   *
   * @param posn The PositionAxial to get the cell at
   * @return The cell at the specified coordinates.
   * @throws IllegalArgumentException if the position does not exist in this game.
   */
  Cell getCellAt(PositionAxial posn);

  /**
   * Checks if the Reversi game is over.
   *
   * @return true if the game is over, false otherwise.
   */
  boolean isGameOver();

  /**
   * Gets the current score of the given PlayerType of this Reversi game. Score is determined by the
   * number of cells a player occupies.
   *
   * @return - The score of the given PlayerType in this game.
   */
  int getCurrentScore(PlayerType playerType);
}

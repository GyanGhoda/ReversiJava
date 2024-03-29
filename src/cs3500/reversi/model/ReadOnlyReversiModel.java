package cs3500.reversi.model;

import cs3500.reversi.controller.Player;
import cs3500.reversi.controller.PlayerType;

/**
 * Represents a read-only version of the ReversiModel with only observation
 * methods.
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
   * Gets the cell at the specified coordinates on the game board using a
   * GamePosition.
   *
   * @param posn The GamePosition to get the cell at
   * @return The cell at the specified coordinates.
   * @throws IllegalArgumentException if the position does not exist in this game.
   */
  Cell getCellAt(GamePosition posn);

  /**
   * Checks if the Reversi game is over.
   *
   * @return true if the game is over, false otherwise.
   */
  boolean isGameOver();

  /**
   * Gets the current score of the given PlayerType of this Reversi game. Score is
   * determined by the
   * number of cells a player occupies.
   *
   * @return - The score of the given PlayerType in this game.
   */
  int getCurrentScore(PlayerType playerType);

  /**
   * Checks if the current player has any valid moves.
   *
   * @return true if the current player has valid moves, false otherwise.
   */
  boolean doesCurrentPlayerHaveValidMoves();

  /**
   * Checks if the current player has any valid moves at the given position.
   *
   * @param posn The position to check for valid moves.
   * @return true if the current player has valid moves at the given position,
   *         false otherwise.
   */
  boolean doesCurrentPlayerHaveValidMovesPosn(GamePosition posn, Player playerTurn);

  /**
   * Gets the size of the game board (number of cells).
   *
   * @return The size of the game board.
   */
  int getBoardSize();

  /**
   * Determines whether the game has started.
   *
   * @return true if the game has started, false if not.
   */
  boolean hasGameStarted();

  /**
   * Gets the winner of the game in the current stage. If the game is tied,
   * returns the white player
   * because black moves first.
   *
   * @return The current winning player of the game.
   */
  String getCurrentWinner();

  /**
   * Gets the score for the given move if it was played.
   * 
   * @param posn The position to get the score for.
   * @return The score for the given move if it was played.
   */
  int getScoreForMove(GamePosition posn);

  /**
   * Gets the score for the given move if it was played.
   *
   * @param posn The position to get the score for.
   * @param player The String representing the player
   * @return The score for the given move if it was played.
   */
  int getScoreForMovePlayer(GamePosition posn, String player);
}

package cs3500.reversi.model;

import java.util.HashMap;

import cs3500.reversi.controller.ModelStatusFeatures;
import cs3500.reversi.controller.Player;
import cs3500.reversi.controller.PlayerType;

/**
 * Defines the contract for a Reversi game model.
 * Provides the mutator methods for a Reversi game model, which include the
 * following:
 * startGame, addPieceToCoordinates, and passTurn.
 */
public interface ReversiModel extends ReadOnlyReversiModel {

  /**
   * Adds a listener to the model.
   *
   * @param feature - the feature to add
   */
  void addFeaturesListener(ModelStatusFeatures feature);

  /**
   * Starts the game of Reversi.
   *
   * @throws IllegalStateException if the game has already started.
   */
  void startGame();

  /**
   * Creates a deep copy of the board of this Reversi game.
   *
   * @return a deep copy of the board of this Reversi game.
   */
  HashMap<GamePosition, Cell> getBoardCopy();

  /**
   * Adds a player's piece to the specified position on the board and updates the
   * game state.
   *
   * @param posn   The position to add the player's piece.
   * @param player The player to add the piece for.
   * @throws IllegalStateException    if the move cannot be made.
   * @throws IllegalStateException    if the game has not started.
   * @throws IllegalStateException    if the player given is not currently up for
   *                                  a move.
   * @throws IllegalArgumentException if the position does not exist in this game.
   */
  void addPieceToCoordinates(GamePosition posn, Player player);

  /**
   * Passes the turn to the next player.
   *
   * @param player - the player to pass the turn for
   * 
   * @throws IllegalStateException if the game has not started or if it is not the
   *                               player's turn.
   */
  void passTurn(Player player);

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
   * Gets the number of rows on the game board.
   *
   * @return The number of rows in the game board.
   */
  int getNumRows();

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

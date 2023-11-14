package cs3500.reversi.model;

import java.util.HashMap;
import cs3500.reversi.controller.Player;

/**
 * Defines the contract for a Reversi game model.
 * Provides the mutator methods for a Reversi game model, which include the
 * following:
 * startGame, addPieceToCoordinates, and passTurn.
 *
 */
public interface ReversiModel extends ReadOnlyReversiModel {

  /**
   * Creates a deep copy of the board of this Reversi game.
   */
  HashMap<PositionAxial, Cell> getBoardCopy();

  /**
   * Adds a player's piece to the specified position on the board and updates the game state.
   *
   * @param posn The position to add the player's piece.
   * @param player The player to add the piece for.
   * @throws IllegalStateException if the move cannot be made.
   * @throws IllegalStateException if the game has not started.
   * @throws IllegalStateException if the player given is not currently up for a move.
   * @throws IllegalArgumentException if the position does not exist in this game.
   */
  void addPieceToCoordinates(PositionAxial posn, Player player);

  /**
   * Passes the turn to the next player.
   * 
   * @throws IllegalStateException if the game has not started.
   */
  void passTurn();

  /**
   * Gets the score for the given move if it was played.
   * 
   * @param posn The position to get the score for.
   */
  int getScoreForMove(PositionAxial posn);

  /**
   * Gets the size of the game board (number of cells).
   * 
   * @return The size of the game board.
   */
  int getBoardSize();
}

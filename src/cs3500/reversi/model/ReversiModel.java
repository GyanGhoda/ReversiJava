package cs3500.reversi.model;

import cs3500.reversi.controller.PlayerType;

/**
 * Defines the contract for a Reversi game model.
 * Provides the mutator methods for a Reversi game model, which include the following:
 * startGame, addPieceToCoordinates, and passTurn.
 *
 */
public interface ReversiModel extends ReadOnlyReversiModel {

  /**
   * Starts a new Reversi game, initializing the game board and players.
   *
   * @throws IllegalStateException if the game has already started.
   */
  void startGame();



  /**
   * Adds a player's piece to the specified position on the board and updates the game state.
   *
   * @param posn The position to add the player's piece.
   * @throws IllegalStateException if the move cannot be made.
   * @throws IllegalStateException if the game has not started.
   * @throws IllegalArgumentException if the position does not exist in this game.
   */
  void addPieceToCoordinates(PositionAxial posn);

  /**
   * Passes the turn to the next player.
   * 
   * @throws IllegalStateException if the game has not started.
   */
  void passTurn();
}

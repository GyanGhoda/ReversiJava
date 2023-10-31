package cs3500.reversi.model;

import cs3500.reversi.controller.PlayerType;

/**
 * Defines the contract for a Reversi game model. Provides methods to start a game, retrieve
 * information about the game state, access cells on the game board, and perform game actions.
 */
public interface ReversiModel {

  /**
   * Starts a new Reversi game, initializing the game board and players.
   *
   * @throws IllegalStateException if the game has already started.
   */
  void startGame();

  /**
   * Gets the number of rows on the game board.
   *
   * @return The number of rows in the game board.
   */
  int getNumRows();

  /**
   * Checks if the Reversi game is over.
   *
   * @return true if the game is over, false otherwise.
   */
  boolean isGameOver();

  /**
   * Gets the cell at the specified coordinates on the game board using a PositionAxial.
   *
   * @param posn The PositionAxial to get the cell at
   * @return The cell at the specified coordinates.
   * @throws IllegalArgumentException if the position does not exist in this game.
   */
  Cell getCellAt(PositionAxial posn);

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

  /**
   * Gets the current score of the given PlayerType of this Reversi game. Score is determined by the
   * number of cells a player occupies.
   * 
   * @return - The score of the given PlayerType in this game.
   */
  int getCurrentScore(PlayerType playerType);
}

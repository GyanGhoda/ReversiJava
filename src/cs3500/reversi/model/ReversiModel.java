package cs3500.reversi.model;

/**
 * Defines the contract for a Reversi game model.
 * Provides methods to start a game, retrieve information about the game state, 
 * access cells on the game board, and perform game actions.
 */
public interface ReversiModel {

  /**
    * Starts a new Reversi game, initializing the game board and players.
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
    * Gets the cell at the specified coordinates on the game board.
    *
    * @param q The 'q' coordinate.
    * @param r The 'r' coordinate.
    * @param s The 's' coordinate.
    * @return The cell at the specified coordinates.
    */
  Cell getCellAt(int q, int r, int s);

  /**
   * Adds a player's piece to the specified position on the board and updates the game state.
   *
   * @param posn The position to add the player's piece.
   * @throws IllegalStateException if the move cannot be made.
   */
  void addPieceToCoordinates(PositionAxial posn);

  /**
   * Passes the turn to the next player.
   */
  void passTurn();
}

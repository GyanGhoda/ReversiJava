package cs3500.reversi.controller;

import cs3500.reversi.model.PositionAxial;
import cs3500.reversi.model.ReversiModel;

/**
 * Represents a player in a Reversi game. Not implemented yet due to controller
 * not being implemented. Generalized to allow for different types of players
 * (AI and human) to be implemented into a game of Reversi.
 */
public interface Player {
  /**
   * Requests a move from the player. Either employs a strategy if it is a
   * ComputerPlayer or returns the selected position
   *
   * @param posn  - The selected position to move to
   * @param model - The model to move to
   * 
   * @return the position that is chosen to move to
   */
  public PositionAxial requestMove(ReversiModel model, PositionAxial posn);

  /**
   * Returns a Player that is the the other player.
   *
   * @return the opposite player
   */
  public Player getOppositePlayer();

  public PositionAxial notifyYourTurn(ReversiModel model);
}

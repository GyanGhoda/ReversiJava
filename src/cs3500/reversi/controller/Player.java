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
   * Plays a move for the player using the strategy given to the player.
   * 
   * @param model - the model to play the move on
   * @return the position that is chosen to move to
   */
  public PositionAxial playMove(ReversiModel model);

  /**
   * Returns a Player that is the the other player.
   * 
   * @return the opposite player
   */
  public Player getOppositePlayer();
}

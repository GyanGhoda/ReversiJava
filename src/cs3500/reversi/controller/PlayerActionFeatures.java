package cs3500.reversi.controller;

import cs3500.reversi.model.PositionAxial;

/**
 * Represents the features that the controller can use to interact with the
 * model.
 */
public interface PlayerActionFeatures {

  /**
   * Moves the current player's piece to the given position.
   *
   * @param posn the position to move to
   */
  public void moveToCoordinate(PositionAxial posn);

  /**
   * Passes the turn to the next player.
   */
  public void passTurn();

  /**
   * Gets the player that is currently playing
   * 
   * @return the player that is currently playing
   */
  public String getPlayer();
}

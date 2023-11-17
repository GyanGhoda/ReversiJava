package cs3500.reversi.controller;

import cs3500.reversi.model.PositionAxial;

/**
 * Represents the features that the controller can use to interact with the
 * model.
 */
public interface Features {

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

  public void notifyToRefresh(String currentTurn);

  public String getPlayer();
}

package cs3500.reversi.controller;

import cs3500.reversi.model.PositionAxial;

/**
 * Represents a controller for the game of Reversi.
 */
public class MockController implements PlayerActionFeatures, ModelStatusFeatures {
  private final Player player;
  // the log of the controller
  final StringBuilder log;

  /**
   * Constructs a new BasicReversiController with the given model, player, and view.
   *
   * @param player the player to use
   */
  public MockController(Player player) {
    this.player = player;
    this.log = new StringBuilder();
  }

  /**
   * Moves the current player's piece to the given position.
   *
   * @param posn the position to move to
   */
  @Override
  public void moveToCoordinate(PositionAxial posn) {

    this.log.append("Requested move to coordinate: " + posn.toString() + "\n");

  }

  /**
   * Passes the turn to the next player.
   */
  @Override
  public void passTurn() {

    this.log.append("Requested to pass turn.\n");

  }

  @Override
  public void notifyToRefresh(String currentTurn) {

    this.log.append("Notified to refresh.\n");

  }

  @Override
  public String getPlayer() {

    return this.player.toString();
  }

  /**
   * Gets the log of the game.
   *
   * @return The log of the game.
   */
  public String getLog() {
    return this.log.toString();
  }
}

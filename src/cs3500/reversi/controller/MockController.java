package cs3500.reversi.controller;

import cs3500.reversi.model.PositionAxial;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.visualview.ReversiVisualView;

/**
 * Represents a controller for the game of Reversi.
 */
public class MockController implements PlayerActionFeatures, ModelStatusFeatures {
  private final ReversiModel model;
  private final Player player;
  private final ReversiVisualView view;
  // the log of the controller
  final StringBuilder log;

  /**
   * Constructs a new BasicReversiController with the given model, player, and view.
   *
   * @param model  the model to use
   * @param player the player to use
   * @param view   the view to use
   */
  public MockController(ReversiModel model, Player player, ReversiVisualView view) {
    this.model = model;
    this.player = player;
    this.view = view;
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

package cs3500.reversi.controller;

import cs3500.reversi.model.GamePosition;
import cs3500.reversi.model.PositionAxial;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.visualview.ReversiVisualView;

/**
 * Represents a controller for the game of Reversi.
 */
public class BasicReversiController implements PlayerActionFeatures, ModelStatusFeatures {
  private final ReversiModel model;
  private final Player player;
  private final ReversiVisualView view;

  /**
   * Constructs a new BasicReversiController with the given model, player, and
   * view.
   *
   * @param model  the model to use
   * @param player the player to use
   * @param view   the view to use
   */
  public BasicReversiController(ReversiModel model, Player player, ReversiVisualView view) {
    this.model = model;
    this.player = player;
    this.view = view;
  }

  /**
   * Moves the current player's piece to the given position.
   *
   * @param posn the position to move to
   */
  @Override
  public void moveToCoordinate(GamePosition posn) {

    // if the game is not over, add the piece to the board.
    if (!this.model.isGameOver()) {
      GamePosition posnToMove = this.player.requestMove(this.model, posn);
      if (posnToMove.containsCoordinate(this.model.getBoardSize())) {
        this.passTurn();
      } else {
        this.model.addPieceToCoordinates(posnToMove, this.player);
      }
    }
  }

  /**
   * Passes the turn to the next player.
   */
  @Override
  public void passTurn() {
    if (!this.model.isGameOver()) {
      this.model.passTurn(this.player);
    }
  }

  @Override
  public void notifyToRefresh(String currentTurn) {

    // repaints the appropriate frame
    this.view.refresh(currentTurn.equals(this.player.toString()));

    // if it is the player's turn, notify them
    if (currentTurn.equals(this.player.toString())) {
      if (!this.player.notifyYourTurn(model).equals(new PositionAxial(0, 0, 0))) {
        this.moveToCoordinate(new PositionAxial(0, 0, 0));
      }
    }
  }

  @Override
  public String getPlayer() {
    return this.player.toString();
  }
}

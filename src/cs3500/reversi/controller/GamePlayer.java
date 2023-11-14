package cs3500.reversi.controller;

import java.util.Objects;

import cs3500.reversi.model.PositionAxial;
import cs3500.reversi.model.ReversiModel;

/**
 * Represents a human player in a Reversi game. It implements the Player
 * interface. Generalized to allow for different types of players (AI and human)
 */
public class GamePlayer implements Player {

  // The type of the player, which can be BLACK or WHITE.
  private final PlayerType type;
  private final ReversiStrategy strategy;

  /**
   * Constructs a new HumanPlayer with the given player type.
   *
   * @param type The type of the player, which can be BLACK or WHITE.
   */
  public GamePlayer(PlayerType type) {
    this.type = Objects.requireNonNull(type);
    this.strategy = new CaptureMostPieces();
  }

  /**
   * Constructs a new HumanPlayer with the given player type and strategy.
   *
   * @param type     The type of the player, which can be BLACK or WHITE.
   * @param strategy The strategy of the player, which can be ADD HERE
   */
  public GamePlayer(PlayerType type, ReversiStrategy strategy) {
    this.type = Objects.requireNonNull(type);
    this.strategy = strategy;
  }

  /**
   * Plays a move for the player using the strategy given to the player.
   * 
   * @param model - the model to play the move on
   * @return the position that is chosen to move to
   */
  @Override
  public PositionAxial playMove(ReversiModel model) {
    return this.strategy.chooseMove(model, this.type);
  }

  /**
   * Returns a string representation of the player.
   *
   * @return The string representation of the player, "X" for BLACK, and "O" for
   *         WHITE.
   */
  @Override
  public String toString() {
    if (this.type.equals(PlayerType.BLACK)) {
      return "X";
    } else {
      return "O";
    }
  }
}

package cs3500.reversi.strategies;

import cs3500.reversi.controller.PlayerType;
import cs3500.reversi.model.PositionAxial;
import cs3500.reversi.model.ReversiModel;

/**
 * A strategy that tries two strategies. The first strategy will be used first, and if it decides
 * to pass, then the second strategy will be used.
 */
public class TryTwoStrategies implements ReversiStrategy {
  
  private final ReversiStrategy firstStrategy;
  private final ReversiStrategy secondStrategy;

  // The constructor that takes in two different ReversiStrategies
  public TryTwoStrategies(ReversiStrategy firstStrategy, ReversiStrategy secondStrategy) {
    this.firstStrategy = firstStrategy;
    this.secondStrategy = secondStrategy;
  }

  @Override
  public PositionAxial chooseMove(ReversiModel model, PlayerType playerTurn) {
    PositionAxial firstMove = this.firstStrategy.chooseMove(model, playerTurn);
    PositionAxial secondMove = this.secondStrategy.chooseMove(model, playerTurn);

    if (firstMove.equals(new PositionAxial(model.getBoardSize(), model.getBoardSize(),
            model.getBoardSize()))) {
      return secondMove;
    } else {
      return firstMove;
    }
  }
}

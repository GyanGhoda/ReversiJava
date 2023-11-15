package cs3500.reversi.strategies;

import cs3500.reversi.controller.PlayerType;
import cs3500.reversi.model.PositionAxial;
import cs3500.reversi.model.ReversiModel;

public class TryTwoStrategies implements ReversiStrategy {
    // The two strategies that will be used. The first strategy will be used first,
    // and if it decides to pass, then the second strategy will be used.
    ReversiStrategy firstStrategy;
    ReversiStrategy secondStrategy;

    // The constructor that takes in two different ReversiStrategies
    TryTwoStrategies(ReversiStrategy firstStrategy, ReversiStrategy secondStrategy) {
        this.firstStrategy = firstStrategy;
        this.secondStrategy = secondStrategy;
    }

    @Override
    public PositionAxial chooseMove(ReversiModel model, PlayerType playerTurn) {
        PositionAxial firstMove = this.firstStrategy.chooseMove(model, playerTurn);
        PositionAxial secondMove = this.secondStrategy.chooseMove(model, playerTurn);

        if (firstMove.equals(new PositionAxial(model.getBoardSize(), model.getBoardSize(), model.getBoardSize()))) {
            return secondMove;
        } else {
            return firstMove;
        }
    }
}

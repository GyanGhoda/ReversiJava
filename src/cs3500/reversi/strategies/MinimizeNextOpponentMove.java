package cs3500.reversi.strategies;

import java.util.HashMap;

import cs3500.reversi.controller.PlayerType;
import cs3500.reversi.model.Cell;
import cs3500.reversi.model.PositionAxial;
import cs3500.reversi.model.ReversiModel;

public class MinimizeNextOpponentMove implements ReversiStrategy {

    @Override
    public PositionAxial chooseMove(ReversiModel model, PlayerType playerTurn) {
        HashMap<PositionAxial, Cell> board = model.getBoardCopy();
        HashMap<PositionAxial, Integer> scores = new HashMap<PositionAxial, Integer>();
        return null;
    }

}

package cs3500.reversi.controller;

import java.util.HashMap;

import cs3500.reversi.model.Cell;
import cs3500.reversi.model.PositionAxial;
import cs3500.reversi.model.ReversiModel;

public class CaptureMostPieces implements ReversiStrategy {

    @Override
    public PositionAxial chooseMove(ReversiModel model, PlayerType playerTurn) {
        HashMap<PositionAxial, Cell> board = model.getBoardCopy();
        HashMap<PositionAxial, Integer> scores = new HashMap<PositionAxial, Integer>();

        for (PositionAxial posn : board.keySet()) {
            if (model.doesCurrentPlayerHaveValidMovesPosn(posn)) {
                scores.put(posn, model.getScoreForMove(posn));
            }
        }

        return this.getHighestScore(scores);
    }

    private PositionAxial getHighestScore(HashMap<PositionAxial, Integer> scores) {
        int bestScore = 0;
        PositionAxial bestPosn = new PositionAxial(0, 0, 0);
        for (PositionAxial posn : scores.keySet()) {
            if (scores.get(posn) > bestScore) {
                bestScore = scores.get(posn);
                bestPosn = posn;
            }
        }

        return bestPosn;
    }
}

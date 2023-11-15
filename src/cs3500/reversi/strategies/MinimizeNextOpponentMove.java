package cs3500.reversi.strategies;

import java.util.HashMap;

import cs3500.reversi.controller.GamePlayer;
import cs3500.reversi.controller.PlayerType;
import cs3500.reversi.model.Cell;
import cs3500.reversi.model.CellType;
import cs3500.reversi.model.GameCell;
import cs3500.reversi.model.PositionAxial;
import cs3500.reversi.model.ReversiModel;

public class MinimizeNextOpponentMove implements ReversiStrategy {

    @Override
    public PositionAxial chooseMove(ReversiModel model, PlayerType playerTurn) {
        HashMap<PositionAxial, Cell> board = model.getBoardCopy();
        HashMap<PositionAxial, Integer> scores = new HashMap<PositionAxial, Integer>();

        for (PositionAxial posn : board.keySet()) {
            if (model.doesCurrentPlayerHaveValidMovesPosn(posn, new GamePlayer(playerTurn))) {
                scores.put(posn, model.getScoreForMove(posn));
            }
        }

        PositionAxial bestPosn = new PositionAxial(model.getBoardSize(), model.getBoardSize(), model.getBoardSize());
        int lowestScore = 0;

        for (PositionAxial posn : scores.keySet()) {
            HashMap<PositionAxial, Cell> futureBoard = model.getBoardCopy();
            HashMap<PositionAxial, Integer> futureScores = new HashMap<PositionAxial, Integer>();

            Cell cellToAdd = new GameCell(CellType.Player);

            cellToAdd.setCellToPlayer(new GamePlayer(playerTurn));

            futureBoard.put(posn, cellToAdd);

            for (PositionAxial futurePosn : futureBoard.keySet()) {
                if (model.doesCurrentPlayerHaveValidMovesPosn(futurePosn,
                        new GamePlayer(playerTurn).getOppositePlayer())) {
                    futureScores.put(futurePosn, model.getScoreForMove(futurePosn));
                }
            }

            if (this.getHighestScoreTemp(futureScores) < lowestScore) {
                lowestScore = this.getHighestScoreTemp(futureScores);
                bestPosn = posn;
            }

        }

        PositionAxial bestNextTurn = new TryTwoStrategies(new CaptureCellsInCorner(),
                new TryTwoStrategies(new AvoidCellsNextToCorner(), new CaptureMostPieces()))
                .chooseMove(model, playerTurn);

    }

    private int getHighestScoreTemp(HashMap<PositionAxial, Integer> scores) {
        int highestScore = 0;

        for (PositionAxial posn : scores.keySet()) {
            if (scores.get(posn) > highestScore) {
                highestScore = scores.get(posn);
            }
        }

        return highestScore;
    }

}

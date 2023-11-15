package cs3500.reversi.strategies;

import java.util.HashMap;

import cs3500.reversi.controller.GamePlayer;
import cs3500.reversi.controller.PlayerType;
import cs3500.reversi.model.BasicReversiModel;
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

            Cell cellToAdd = new GameCell(CellType.Player);

            cellToAdd.setCellToPlayer(new GamePlayer(playerTurn));

            futureBoard.put(posn, cellToAdd);

            PositionAxial bestNextTurn = new TryTwoStrategies(new CaptureCellsInCorner(),
                    new TryTwoStrategies(new AvoidCellsNextToCorner(), new CaptureMostPieces()))
                    .chooseMove(model, this.getOppositePlayerType(playerTurn));

            ReversiModel futureModel = new BasicReversiModel(model.getNumRows(), futureBoard,
                    new GamePlayer(playerTurn).getOppositePlayer());

            if (!bestNextTurn.containsCoordinate(model.getBoardSize())) {
                if (futureModel.getScoreForMove(bestNextTurn) < lowestScore) {
                    lowestScore = futureModel.getScoreForMove(bestNextTurn);
                    bestPosn = posn;
                }
            }
        }

        return bestPosn;
    }

    private PlayerType getOppositePlayerType(PlayerType playerTurn) {
        if (playerTurn.equals(PlayerType.BLACK)) {
            return PlayerType.WHITE;
        } else {
            return PlayerType.BLACK;
        }
    }

}

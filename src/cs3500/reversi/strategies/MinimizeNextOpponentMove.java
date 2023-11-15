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

/**
 * Represents a strategy that minimizes the next opponent's move. Does this by
 * looking at the next possible best
 * move for the opponent and minimizing the score of that move
 */
public class MinimizeNextOpponentMove implements ReversiStrategy {

    /**
     * Chooses the move that will minimize the next opponent's move.
     *
     * @param model      the model to choose a move from
     * @param playerTurn the player whose turn it is
     * @return the position axial of the move to make
     */
    @Override
    public PositionAxial chooseMove(ReversiModel model, PlayerType playerTurn) {
        // Create a copy of the board
        HashMap<PositionAxial, Cell> board = model.getBoardCopy();
        // Create a hashmap of the scores for each move
        HashMap<PositionAxial, Integer> scores = new HashMap<PositionAxial, Integer>();

        // For every position on the board, see if it is a valid move and, if so, add it
        for (PositionAxial posn : board.keySet()) {
            if (model.doesCurrentPlayerHaveValidMovesPosn(posn, new GamePlayer(playerTurn))) {
                scores.put(posn, model.getScoreForMove(posn));
            }
        }

        // Represents the best possible move to minimize the opponents next move.
        // Defualts to pass turn if there are no valid moves.
        PositionAxial bestPosn = new PositionAxial(model.getBoardSize(), model.getBoardSize(), model.getBoardSize());
        // The lowest possible score for the opponents next move.
        int lowestScore = Integer.MAX_VALUE;

        // For every possible move, create a copy of the board and add the move to the
        // copied board
        for (PositionAxial posn : scores.keySet()) {
            HashMap<PositionAxial, Cell> futureBoard = model.getBoardCopy();

            Cell cellToAdd = new GameCell(CellType.Player);

            cellToAdd.setCellToPlayer(new GamePlayer(playerTurn));

            futureBoard.put(posn, cellToAdd);

            // The model one move in the future
            ReversiModel futureModel = new BasicReversiModel(model.getNumRows(), futureBoard,
                    new GamePlayer(playerTurn).getOppositePlayer());

            // Get the best move for the opponent for the model in the future
            PositionAxial bestNextTurn = new TryTwoStrategies(new CaptureCellsInCorner(),
                    new TryTwoStrategies(new AvoidCellsNextToCorner(), new CaptureMostPieces()))
                    .chooseMove(futureModel, this.getOppositePlayerType(playerTurn));

            // If the best move for the opponent is off the board, then the best move for
            // them is to pass, which is the best possible case for the player
            if (bestNextTurn.containsCoordinate(model.getBoardSize())) {
                // If the move is worse for the opponent then the current worst move, update
                // bestPosn and lowest score
                if (futureModel.getScoreForMove(bestNextTurn) < lowestScore) {
                    lowestScore = futureModel.getScoreForMove(bestNextTurn);
                    bestPosn = posn;
                }
            } else {
                lowestScore = 0;
                bestPosn = posn;
            }
        }

        return bestPosn;
    }

    // Gets the opposite player type of the given player type
    private PlayerType getOppositePlayerType(PlayerType playerTurn) {
        if (playerTurn.equals(PlayerType.BLACK)) {
            return PlayerType.WHITE;
        } else {
            return PlayerType.BLACK;
        }
    }
}

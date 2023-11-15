package cs3500.reversi.strategies;

import java.util.HashMap;

import cs3500.reversi.controller.GamePlayer;
import cs3500.reversi.controller.PlayerType;
import cs3500.reversi.model.Cell;
import cs3500.reversi.model.PositionAxial;
import cs3500.reversi.model.ReversiModel;

/**
 * A strategy that chooses the move that will capture the most pieces.
 * The strategy breaks ties by choosing the uppermost-leftmost move.
 * The strategy also avoids the corner.
 */
public class AvoidCellsNextToCorner implements ReversiStrategy {

    /**
     * Chooses the move that will capture the most pieces while avoiding the cells
     * next to a corner.
     *
     * @param model      the model to choose a move from
     * @param playerTurn the player whose turn it is
     * @return the position axial of the move to make
     */
    @Override
    public PositionAxial chooseMove(ReversiModel model, PlayerType playerTurn) {
        HashMap<PositionAxial, Cell> board = model.getBoardCopy();
        HashMap<PositionAxial, Integer> scores = new HashMap<PositionAxial, Integer>();

        // Get the scores for each move
        for (PositionAxial posn : board.keySet()) {
            if (model.doesCurrentPlayerHaveValidMovesPosn(posn, new GamePlayer(playerTurn))) {
                scores.put(posn, model.getScoreForMove(posn));
            }
        }

        HashMap<PositionAxial, Integer> scoresNoCorners = new HashMap<PositionAxial, Integer>();

        for (PositionAxial posn : scores.keySet()) {
            if (!this.isNextToCorner(model, posn)) {
                scoresNoCorners.put(posn, scores.get(posn));
            }
        }

        PositionAxial bestPosn = new PositionAxial(model.getBoardSize(), model.getBoardSize(), model.getBoardSize());

        if (!scoresNoCorners.isEmpty()) {
            bestPosn = new CaptureMostPieces().getHighestScorePosn(scoresNoCorners);
        }

        return bestPosn;
    }

    // Returns true if the given position is next to a corner position
    private boolean isNextToCorner(ReversiModel model, PositionAxial posn) {
        int middleY = (model.getNumRows() - 1) / 2;

        // Check if the position is next to any of the six corners
        return (Math.abs(posn.getQ()) == middleY && Math.abs(posn.getR()) == middleY - 1) ||
                (Math.abs(posn.getR()) == middleY && Math.abs(posn.getS()) == middleY - 1) ||
                (Math.abs(posn.getS()) == middleY && Math.abs(posn.getQ()) == middleY - 1) ||
                (Math.abs(posn.getR()) == middleY && Math.abs(posn.getQ()) == middleY - 1) ||
                (Math.abs(posn.getS()) == middleY && Math.abs(posn.getR()) == middleY - 1) ||
                (Math.abs(posn.getQ()) == middleY && Math.abs(posn.getS()) == middleY - 1);
    }
}
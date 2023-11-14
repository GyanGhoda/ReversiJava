package cs3500.reversi.controller;

import java.util.HashMap;

import cs3500.reversi.model.Cell;
import cs3500.reversi.model.PositionAxial;
import cs3500.reversi.model.ReversiModel;

/**
 * A strategy that chooses the move that will capture the most pieces.
 * The strategy breaks ties by choosing the uppermost-leftmost move.
 * The strategy also avoids the corner.
 */
public class CaptureCellsInCorner implements ReversiStrategy {

    /**
     * Chooses the move that will capture the most pieces while capturing the
     * corner.
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

        HashMap<PositionAxial, Integer> scoresNoCorners = new HashMap<PositionAxial, Integer>(scores);

        for (PositionAxial posn : scores.keySet()) {
            if (!this.isNextToCorner(model, posn)) {
                scoresNoCorners.put(posn, scores.get(posn));
            }
        }

        PositionAxial bestPosn = new PositionAxial(0, 0, 0);

        if (scoresNoCorners.isEmpty()) {
            bestPosn = new CaptureMostPieces().chooseMove(model, playerTurn);
        } else {
            bestPosn = new CaptureMostPieces().getHighestScore(scoresNoCorners);
        }

        return bestPosn;
    }

    // Returns true if the given position is next to a corner position
    private boolean isNextToCorner(ReversiModel model, PositionAxial posn) {
        int middleY = (model.getNumRows() - 1) / 2;

        if (posn.getQ() == middleY - 1 || posn.getS() == middleY - 1 && posn.getR() >= 0) {
            return true;
        } else if (posn.getQ() == -middleY + 1 || posn.getS() == -middleY + 1 && posn.getR() < 0) {
            return true;
        } else if (posn.getR() == middleY - 1 && posn.getQ() != -middleY && posn.getS() != -middleY) {
            return true;
        } else if (posn.getR() == -middleY + 1 && posn.getQ() != middleY && posn.getS() != middleY) {
            return true;
        } else {
            return false;
        }
    }
}

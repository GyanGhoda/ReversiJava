package cs3500.reversi.controller;

import java.util.HashMap;

import cs3500.reversi.model.Cell;
import cs3500.reversi.model.PositionAxial;
import cs3500.reversi.model.ReversiModel;

public class AvoidCellsNextToCorner implements ReversiStrategy {

    /**
     * Chooses the move that will capture the most pieces while avoiding the corner.
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
            if (model.doesCurrentPlayerHaveValidMovesPosn(posn)) {
                scores.put(posn, model.getScoreForMove(posn));
            }
        }

        for (PositionAxial posn : scores.keySet()) {
            if (!this.isCorner(model, posn)) {
                scores.put(posn, 0);
            }
        }
        return null;

        // DO NOT KNOW WHAT TO DO HERE
    }

    private boolean isCorner(ReversiModel model, PositionAxial posn) {
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

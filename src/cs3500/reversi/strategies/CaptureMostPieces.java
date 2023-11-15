package cs3500.reversi.strategies;

import java.util.ArrayList;
import java.util.HashMap;

import cs3500.reversi.controller.GamePlayer;
import cs3500.reversi.controller.PlayerType;
import cs3500.reversi.model.Cell;
import cs3500.reversi.model.PositionAxial;
import cs3500.reversi.model.ReversiModel;

/**
 * A strategy that chooses the move that will capture the most pieces.
 * The strategy breaks ties by choosing the uppermost-leftmost move.
 */
public class CaptureMostPieces implements ReversiStrategy {

  /**
   * Chooses the move that will capture the most pieces.
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

    // If there are no valid moves to be made, return an invalid position axial that
    // is off the board
    // with each of the coordinates being the size of the board. This is the same as
    // passing the turn,
    // and will be handeled in the controller when made.
    if (scores.isEmpty()) {
      return new PositionAxial(model.getBoardSize(), model.getBoardSize(), model.getBoardSize());
    }

    return this.getHighestScorePosn(scores);
  }

  /**
   * Gets the position axial with the highest score.
   *
   * @param scores the scores of the moves
   * @return the position axial with the highest score
   */
  protected PositionAxial getHighestScorePosn(HashMap<PositionAxial, Integer> scores) {

    int bestScore = 0;
    PositionAxial bestPosn = new PositionAxial(0, 0, 0);

    // Get the highest score
    for (PositionAxial posn : scores.keySet()) {
      if (scores.get(posn) > bestScore) {
        bestScore = scores.get(posn);
        bestPosn = posn;
      }
    }

    int count = 0;
    ArrayList<PositionAxial> bestPosns = new ArrayList<PositionAxial>();

    // Get the positions with the highest score
    for (HashMap.Entry<PositionAxial, Integer> entry : scores.entrySet()) {
      if (entry.getValue() == bestScore) {
        bestPosns.add(entry.getKey());
        count += 1;
      }
    }

    if (count < 2) {
      return bestPosn;
    } else {
      return this.getLeftUpperMost(bestPosns);
    }
  }

  // Gets the left uppermost positionaxial from the given list of positions axial
  private PositionAxial getLeftUpperMost(ArrayList<PositionAxial> bestPosns) {
    PositionAxial leftUpperMost = bestPosns.get(0);

    // Find the left uppermost position axial
    for (PositionAxial posn : bestPosns) {
      if (posn.getS() >= leftUpperMost.getS()) {
        if (posn.getR() <= 0) {
          leftUpperMost = posn;
        } else if (posn.getQ() <= leftUpperMost.getQ() && posn.getR() > 0) {
          leftUpperMost = posn;
        }
      }
    }

    return leftUpperMost;
  }
}

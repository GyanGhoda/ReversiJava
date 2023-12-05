package cs3500.reversi.strategies;

import java.util.ArrayList;
import java.util.HashMap;

import cs3500.reversi.controller.ComputerPlayer;
import cs3500.reversi.controller.PlayerType;
import cs3500.reversi.model.Cell;
import cs3500.reversi.model.GamePosition;
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
  public GamePosition chooseMove(ReversiModel model, PlayerType playerTurn) {

    HashMap<GamePosition, Cell> board = model.getBoardCopy();
    HashMap<GamePosition, Integer> scores = new HashMap<GamePosition, Integer>();

    // Get the scores for each move
    for (GamePosition posn : board.keySet()) {
      if (model.doesCurrentPlayerHaveValidMovesPosn(posn, new ComputerPlayer(playerTurn))) {
        scores.put(posn, model.getScoreForMove(posn));
      }
    }

    // If there are no valid moves to be made, return an invalid position axial that
    // is off the board
    // with each of the coordinates being the size of the board. This is the same as
    // passing the turn,
    // and will be handled in the controller when made.
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
  protected GamePosition getHighestScorePosn(HashMap<GamePosition, Integer> scores) {

    int bestScore = 0;
    GamePosition bestPosn;

    // Get the highest score
    for (GamePosition posn : scores.keySet()) {
      if (scores.get(posn) > bestScore) {
        bestScore = scores.get(posn);
        bestPosn = posn;
      }
    }

    int count = 0;
    ArrayList<GamePosition> bestPosns = new ArrayList<GamePosition>();

    // Get the positions with the highest score
    for (HashMap.Entry<GamePosition, Integer> entry : scores.entrySet()) {
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
  private GamePosition getLeftUpperMost(ArrayList<GamePosition> bestPosns) {
    GamePosition leftUpperMost = bestPosns.get(0);

    // Find the left uppermost position axial
    for (GamePosition posn : bestPosns) {
      if (posn.getS() >= leftUpperMost.getS()) {
        if (posn.getR() <= 0) {
          leftUpperMost = posn;
        } else if (posn.getQ() <= leftUpperMost.getQ()) {
          leftUpperMost = posn;
        }
      }
    }

    return leftUpperMost;
  }
}

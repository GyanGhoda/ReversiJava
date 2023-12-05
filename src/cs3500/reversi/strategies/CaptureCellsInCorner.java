package cs3500.reversi.strategies;

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
  public GamePosition chooseMove(ReversiModel model, PlayerType playerTurn) {
    HashMap<GamePosition, Cell> board = model.getBoardCopy();
    HashMap<GamePosition, Integer> scores = new HashMap<GamePosition, Integer>();

    // Get the scores for each move
    for (GamePosition posn : board.keySet()) {
      if (model.doesCurrentPlayerHaveValidMovesPosn(posn, new ComputerPlayer(playerTurn))) {
        scores.put(posn, model.getScoreForMove(posn));
      }
    }

    // finding moves in corners
    HashMap<GamePosition, Integer> scoresCorners = new HashMap<GamePosition, Integer>();

    for (GamePosition posn : scores.keySet()) {
      if (this.isCorner(model, posn)) {
        scoresCorners.put(posn, scores.get(posn));
      }
    }

    // if there are no moves in the corner, return the boardsize for each coordinate
    // as a pass
    GamePosition bestPosn = new PositionAxial(model.getBoardSize(), model.getBoardSize(),
        model.getBoardSize());

    // choose the best move of those in the corner
    if (!scoresCorners.isEmpty()) {
      bestPosn = new CaptureMostPieces().getHighestScorePosn(scoresCorners);
    }

    return bestPosn;
  }

  // Returns true if the given position is a corner position
  private boolean isCorner(ReversiModel model, GamePosition posn) {
    return posn.checkCorner(model.getNumRows());
  }
}

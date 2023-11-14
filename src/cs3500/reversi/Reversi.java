package cs3500.reversi;

import cs3500.reversi.controller.CaptureMostPieces;
import cs3500.reversi.controller.GamePlayer;
import cs3500.reversi.controller.Player;
import cs3500.reversi.controller.PlayerType;
import cs3500.reversi.controller.ReversiStrategy;
import cs3500.reversi.model.BasicReversiModel;
import cs3500.reversi.model.PositionAxial;
import cs3500.reversi.model.ReadOnlyReversiModel;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.visualview.HexagonalFrame;
import cs3500.reversi.visualview.ReversiVisualView;

public final class Reversi {
  public static void main(String[] args) {

    Player playerBlack = new GamePlayer(PlayerType.BLACK);
    Player playerWhite = new GamePlayer(PlayerType.WHITE);

    ReversiModel model = new BasicReversiModel(7, playerBlack, playerWhite);

    model.addPieceToCoordinates(new PositionAxial(1, -2, 1), playerBlack);
    model.addPieceToCoordinates(new PositionAxial(2, -3, 1), playerWhite);
    model.addPieceToCoordinates(new PositionAxial(-1, 2, -1), playerBlack);
    model.addPieceToCoordinates(new PositionAxial(-1, 3, -2), playerWhite);
    model.addPieceToCoordinates(new PositionAxial(-2, 3, -1), playerBlack);
    model.addPieceToCoordinates(new PositionAxial(2, -1, -1), playerWhite);
    model.addPieceToCoordinates(new PositionAxial(-1, -1, 2), playerBlack);

    ReversiVisualView view = new HexagonalFrame(model);
    // YourModel model = ...create an example model...
    // YourView view = new YourView(model);

    view.makeVisible();

  }
}
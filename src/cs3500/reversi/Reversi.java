package cs3500.reversi;

import cs3500.reversi.controller.BasicReversiController;
import cs3500.reversi.controller.Features;
import cs3500.reversi.controller.HumanPlayer;
import cs3500.reversi.controller.ComputerPlayer;
import cs3500.reversi.controller.PlayerType;
import cs3500.reversi.model.BasicReversiModel;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.visualview.HexagonalFrame;
import cs3500.reversi.visualview.ReversiVisualView;

/**
 * Main class that instantiates a model and view.
 */
public final class Reversi {

  /**
   * Main class that instantiates a model and view.
   */
  public static void main(String[] args) {
    ReversiModel model = new BasicReversiModel(7);

    ReversiVisualView view1 = new HexagonalFrame(model);
    ReversiVisualView view2 = new HexagonalFrame(model);

    Features controller1 = new BasicReversiController(model, new HumanPlayer(PlayerType.BLACK), view1);
    Features controller2 = new BasicReversiController(model, new ComputerPlayer(PlayerType.WHITE), view2);

    model.addFeaturesListener(controller1);
    model.addFeaturesListener(controller2);

    view1.setUpFeatures(controller1);
    view2.setUpFeatures(controller2);

    view1.makeVisible();
    view2.makeVisible();

    model.startGame();
  }
}
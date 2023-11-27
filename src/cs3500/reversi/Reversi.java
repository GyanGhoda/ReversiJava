package cs3500.reversi;

import cs3500.reversi.controller.BasicReversiController;
import cs3500.reversi.controller.HumanPlayer;
import cs3500.reversi.controller.Player;
import cs3500.reversi.controller.ComputerPlayer;
import cs3500.reversi.controller.PlayerType;
import cs3500.reversi.model.BasicReversiModel;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.strategies.AvoidCellsNextToCorner;
import cs3500.reversi.strategies.CaptureCellsInCorner;
import cs3500.reversi.strategies.CaptureMostPieces;
import cs3500.reversi.strategies.MinimizeNextOpponentMove;
import cs3500.reversi.strategies.ReversiStrategy;
import cs3500.reversi.strategies.TryTwoStrategies;
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

    if (args.length < 1) {
      throw new IllegalArgumentException("Not enough arguments given");
    }

    Player player1;
    Player player2;
    int argCounter = 0;

    try {
      switch (args[0]) {
        case "human":
          player1 = new HumanPlayer(PlayerType.BLACK);
          argCounter = 1;
          break;
        case "strategy1":
          player1 = new ComputerPlayer(PlayerType.BLACK, new CaptureMostPieces());
          argCounter = 1;
          break;
        case "strategy2":
          player1 = new ComputerPlayer(PlayerType.BLACK, new AvoidCellsNextToCorner());
          argCounter = 1;
          break;
        case "strategy3":
          player1 = new ComputerPlayer(PlayerType.BLACK, new CaptureCellsInCorner());
          argCounter = 1;
          break;
        case "strategy4":
          player1 = new ComputerPlayer(PlayerType.BLACK, new MinimizeNextOpponentMove());
          argCounter = 1;
          break;
        case "combined":
          player1 = new ComputerPlayer(PlayerType.BLACK,
              new TryTwoStrategies(getStrategy(args[1]), getStrategy(args[2])));
          argCounter = 3;
          break;
        default:
          throw new IllegalArgumentException("Player type not supported");
      }

      switch (args[argCounter]) {
        case "human":
          player2 = new HumanPlayer(PlayerType.WHITE);
          break;
        case "strategy1":
          player2 = new ComputerPlayer(PlayerType.WHITE, new CaptureMostPieces());
          break;
        case "strategy2":
          player2 = new ComputerPlayer(PlayerType.WHITE, new AvoidCellsNextToCorner());
          break;
        case "strategy3":
          player2 = new ComputerPlayer(PlayerType.WHITE, new CaptureCellsInCorner());
          break;
        case "strategy4":
          player2 = new ComputerPlayer(PlayerType.WHITE, new MinimizeNextOpponentMove());
          break;
        case "combined":
          player2 = new ComputerPlayer(PlayerType.WHITE,
              new TryTwoStrategies(getStrategy(args[argCounter + 1]), getStrategy(args[argCounter + 2])));
          break;
        default:
          throw new IllegalArgumentException("Player type not supported");
      }

    } catch (Exception e) {
      throw new IllegalArgumentException("Game not created: " + e.getMessage());
    }

    ReversiModel model = new BasicReversiModel(7, player1, player2);

    ReversiVisualView view1 = new HexagonalFrame(model);
    ReversiVisualView view2 = new HexagonalFrame(model);

    BasicReversiController controller1 = new BasicReversiController(model, player1, view1);
    BasicReversiController controller2 = new BasicReversiController(model, player2, view2);

    model.addFeaturesListener(controller1);
    model.addFeaturesListener(controller2);

    view1.setUpFeatures(controller1);
    view2.setUpFeatures(controller2);

    view1.makeVisible();
    view2.makeVisible();

    model.startGame();
  }
  
  // helper for switch statement when combining strategies
  private static ReversiStrategy getStrategy(String strategy) {
    switch (strategy) {
      case "strategy1":
        return new CaptureMostPieces();
      case "strategy2":
        return new AvoidCellsNextToCorner();
      case "strategy3":
        return new CaptureCellsInCorner();
      case "strategy4":
        return new MinimizeNextOpponentMove();
      default:
        throw new IllegalArgumentException("Strategy not supported");
    }
  }
}
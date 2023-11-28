package cs3500.reversi;

import java.util.ArrayList;
import java.util.List;

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
      switch (args[argCounter]) {
        case "human":
          argCounter += 1;
          player1 = new HumanPlayer(PlayerType.BLACK);
          break;
        case "computer":
          argCounter = getToNextPlayer(args, argCounter + 1);
          player1 = setUpComputer(args, argCounter, PlayerType.BLACK);
          break;
        default:
          throw new IllegalArgumentException("Player type not supported");
      }

      switch (args[argCounter]) {
        case "human":
          player2 = new HumanPlayer(PlayerType.WHITE);
          break;
        case "computer":
          player2 = setUpComputer(args, argCounter, PlayerType.WHITE);
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

  private static int getToNextPlayer(String[] args, int argCounter) {
    while (argCounter < args.length) {
      if (args[argCounter].equals("human") || args[argCounter].equals("computer")) {
        return argCounter;
      }
    }
    throw new IllegalArgumentException("No player 2 specified");
  }

  private static Player setUpComputer(String[] args, int argCounter, PlayerType type) {
    List<ReversiStrategy> strats = new ArrayList<>();
    while (argCounter < args.length) {
      switch (args[argCounter]) {
        case "strategy1":
          strats.add(new CaptureMostPieces());
          break;
        case "strategy2":
          strats.add(new AvoidCellsNextToCorner());
          break;
        case "strategy3":
          strats.add(new CaptureCellsInCorner());
          break;
        case "strategy4":
          strats.add(new MinimizeNextOpponentMove());
          break;
        case "human":
        case "computer":
          break;
        default:
          throw new IllegalArgumentException("Invalid argument given");
      }
    }

    if (strats.isEmpty()) {
      return new ComputerPlayer(type, new CaptureMostPieces());
    } else {
      return new ComputerPlayer(type, composeAIStrategy(strats));
    }
  }

  private static ReversiStrategy composeAIStrategy(List<ReversiStrategy> strats) {
    ReversiStrategy currentStrategy = strats.get(0);
    for (int i = 1; i < strats.size(); i += 1) {
      currentStrategy = new TryTwoStrategies(strats.get(i), currentStrategy);
    }

    return currentStrategy;
  }
}
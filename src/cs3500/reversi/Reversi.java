package cs3500.reversi;

import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.controller.BasicReversiController;
import cs3500.reversi.controller.HumanPlayer;
import cs3500.reversi.controller.Player;
import cs3500.reversi.controller.ComputerPlayer;
import cs3500.reversi.controller.PlayerType;
import cs3500.reversi.model.BasicReversiModel;
import cs3500.reversi.model.BasicSquareReversiModel;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.strategies.AvoidCellsNextToCorner;
import cs3500.reversi.strategies.CaptureCellsInCorner;
import cs3500.reversi.strategies.CaptureMostPieces;
import cs3500.reversi.strategies.MinimizeNextOpponentMove;
import cs3500.reversi.strategies.ReversiStrategy;
import cs3500.reversi.strategies.TryTwoStrategies;
import cs3500.reversi.visualview.HexagonalFrame;
import cs3500.reversi.visualview.ReversiVisualView;
import cs3500.reversi.visualview.SquareFrame;

/**
 * Main class that instantiates a model and view.
 */
public final class Reversi {

  /**
   * Main class that instantiates a model and view.
   */
  public static void main(String[] args) {
    Player player1 = new HumanPlayer(PlayerType.BLACK);
    Player player2 = new HumanPlayer(PlayerType.WHITE);
    int argCounter = 0;
    int boardLength = 7;
    boolean square = false;
    boolean boardLengthSpecified = false;

    if (args.length >= 1) {
      if (!args[argCounter].equals("square")) {
        square = true;
        argCounter += 1;
      }
    }

    if (args.length > argCounter) {
      if (!args[argCounter].equals("human") && !args[argCounter].equals("computer")) {
        try {
          boardLength = Integer.valueOf(args[argCounter]);
          if (boardLength % 2 == 0) {
            throw new NumberFormatException();
          }
          argCounter += 1;
          boardLengthSpecified = true;
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException("Invalid board size or invalid argument given");
        }
      }

      if (args.length > argCounter) {
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
          throw new IllegalArgumentException("Invalid arguments given");
        }
      }
    }

    ReversiModel model;
    ReversiVisualView view1;
    ReversiVisualView view2;

    if (square && !boardLengthSpecified) {
      model = new BasicSquareReversiModel(8, player1, player2);

      view1 = new SquareFrame(model);
      view2 = new SquareFrame(model);
    } else if (square) {
      model = new BasicSquareReversiModel(boardLength, player1, player2);

      view1 = new SquareFrame(model);
      view2 = new SquareFrame(model);
    } else {
      model = new BasicReversiModel(boardLength, player1, player2);

      view1 = new HexagonalFrame(model);
      view2 = new HexagonalFrame(model);
    }

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
      argCounter += 1;
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
      argCounter += 1;
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
package cs3500.reversi;

import cs3500.reversi.controller.ComputerPlayer;
import cs3500.reversi.controller.Player;
import cs3500.reversi.controller.PlayerType;
import cs3500.reversi.model.BasicReversiModel;
import cs3500.reversi.model.CellType;
import cs3500.reversi.model.PositionAxial;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.textualview.ReversiTextualView;
import cs3500.reversi.textualview.TextualView;

/**
 * This class is used to demonstrate the functionality of the Reversi game
 * by providing examples of the different classes.
 */
public class Examples {
  // Examples of the CellType enum
  CellType empty = CellType.Empty;
  CellType player = CellType.Player;

  // Examples of the PlayerType enum
  PlayerType black = PlayerType.BLACK;
  PlayerType white = PlayerType.WHITE;

  // Examples of the GamePlayer class
  Player gamePlayerBlack = new ComputerPlayer(PlayerType.BLACK);
  Player gamePlayerWhite = new ComputerPlayer(PlayerType.WHITE);

  // Examples of the BasicReversiModel class
  ReversiModel basicReversiModel3 = new BasicReversiModel(3);
  ReversiModel basicReversiModel5 = new BasicReversiModel(5);
  ReversiModel basicReversiModel7 = new BasicReversiModel(7);

  // Examples of the ReversiTextualView class
  TextualView basicReversiModel3TextualView = new ReversiTextualView(basicReversiModel3);
  TextualView basicReversiModel5TextualView = new ReversiTextualView(basicReversiModel5);
  TextualView basicReversiModel7TextualView = new ReversiTextualView(basicReversiModel7);

  // Examples of the PositionAxial class
  PositionAxial positionAxial123 = new PositionAxial(1, 2, 3);
  PositionAxial positionAxialN34N5 = new PositionAxial(-3, 4, -5);
  PositionAxial positionAxial000 = new PositionAxial(0, 0, 0);
}

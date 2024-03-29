package cs3500.reversi;

import org.junit.Assert;
import org.junit.Test;

import cs3500.reversi.controller.ComputerPlayer;
import cs3500.reversi.controller.Player;
import cs3500.reversi.controller.PlayerType;
import cs3500.reversi.model.BasicReversiModel;
import cs3500.reversi.model.PositionAxial;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.textualview.ReversiTextualView;
import cs3500.reversi.textualview.TextualView;

/**
 * Tests the public methods in the textualview package.
 */
public class TestTextualView {
  // Test that the TextualView has initialized correctly with a correct starting
  // board of width 7.
  @Test
  public void testModelTextualViewIntialization7() {
    ReversiModel model = new BasicReversiModel(7);

    TextualView modelView = new ReversiTextualView(model);

    Assert.assertEquals(modelView.toString(), "   _ _ _ _\n" + //
        "  _ _ _ _ _\n" + //
        " _ _ X O _ _\n" + //
        "_ _ O _ X _ _\n" + //
        " _ _ X O _ _\n" + //
        "  _ _ _ _ _\n" + //
        "   _ _ _ _\n");
  }

  // Test that the TextualView has initialized correctly with a correct starting
  // board
  // of width 7.
  @Test
  public void testModelTextualIntialization3() {
    ReversiModel model = new BasicReversiModel(3);

    TextualView modelView = new ReversiTextualView(model);

    Assert.assertEquals(modelView.toString(), " X O\n" + //
        "O _ X\n" + //
        " X O\n");
  }

  // Test that the TextualView has rendered the board correctly with a starting
  // width of 7 and one valid move.
  @Test
  public void testModelTextualViewMoveOneMove() {

    Player player = new ComputerPlayer(PlayerType.BLACK);

    ReversiModel model = new BasicReversiModel(7, player, new ComputerPlayer(PlayerType.WHITE));

    model.startGame();

    model.addPieceToCoordinates(new PositionAxial(1, -2, 1), player);

    TextualView modelView = new ReversiTextualView(model);

    Assert.assertEquals(modelView.toString(), "   _ _ _ _\n" + //
        "  _ _ X _ _\n" + //
        " _ _ X X _ _\n" + //
        "_ _ O _ X _ _\n" + //
        " _ _ X O _ _\n" + //
        "  _ _ _ _ _\n" + //
        "   _ _ _ _\n");
  }

  // Test that the TextualView has rendered the board correctly with a starting
  // width of 7 and multiple valid moves.
  @Test
  public void testModelTextualViewMoveMultipleMoves() {

    Player playerBlack = new ComputerPlayer(PlayerType.BLACK);
    Player playerWhite = new ComputerPlayer(PlayerType.WHITE);

    ReversiModel model = new BasicReversiModel(7, playerBlack, playerWhite);

    model.startGame();

    model.addPieceToCoordinates(new PositionAxial(1, -2, 1), playerBlack);
    model.addPieceToCoordinates(new PositionAxial(-2, 1, 1), playerWhite);
    model.addPieceToCoordinates(new PositionAxial(-3, 2, 1), playerBlack);
    model.addPieceToCoordinates(new PositionAxial(-3, 1, 2), playerWhite);

    TextualView modelView = new ReversiTextualView(model);

    Assert.assertEquals(modelView.toString(), "   _ _ _ _\n" + //
        "  _ _ X _ _\n" + //
        " _ _ X X _ _\n" + //
        "_ _ X _ X _ _\n" + //
        " O O O O _ _\n" + //
        "  X _ _ _ _\n" + //
        "   _ _ _ _\n");
  }
}

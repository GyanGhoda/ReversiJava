package cs3500.reversi;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cs3500.reversi.controller.ComputerPlayer;
import cs3500.reversi.controller.Player;
import cs3500.reversi.controller.PlayerType;
import cs3500.reversi.model.BasicSquareReversiModel;
import cs3500.reversi.model.Position2D;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.textualview.SquareReversiTextualView;
import cs3500.reversi.textualview.TextualView;

/**
 * Tests the public methods in the model package.
 */
public class TestSquareModel {

  private Player playerBlack;
  private Player playerWhite;

  @Before
  public void init() {
    playerBlack = new ComputerPlayer(PlayerType.BLACK);
    playerWhite = new ComputerPlayer(PlayerType.WHITE);
  }

  // Test that the model has initialized correctly with a correct starting board
  // of width 8.
  @Test
  public void testModelIntialization8() {
    ReversiModel model = new BasicSquareReversiModel(8);

    model.startGame();

    model.addPieceToCoordinates(new Position2D(2, 4), playerBlack);
  }

  // Test that the model has initialized correctly with a correct starting board
  // of width 2.
  @Test
  public void testModelIntialization2() {
    ReversiModel model = new BasicSquareReversiModel(2);

    TextualView modelView = new SquareReversiTextualView(model);

    Assert.assertEquals(modelView.toString(), " X  O \n" + //
            " O  X \n");
  }

  // Test that the model has initialized correclty with a correct
  // starting board of width 4.
  @Test
  public void testModelIntialization4() {
    ReversiModel model = new BasicSquareReversiModel(4);

    TextualView modelView = new SquareReversiTextualView(model);

    Assert.assertEquals(modelView.toString(),
            " _  _  _  _ \n" + " _  X  O  _ \n" + " _  O  X  _ \n" + " _  _  _  _ \n");
  }

  // Tests that one valid move made on the model correctly works as intended.
  @Test
  public void testModelOneMove() {
    ReversiModel model = new BasicSquareReversiModel(4);

    model.startGame();

    model.addPieceToCoordinates(new Position2D(2, 0), playerBlack);

    TextualView modelView = new SquareReversiTextualView(model);

    Assert.assertEquals(modelView.toString(),
            " _  _  X  _ \n" + " _  X  X  _ \n" + " _  O  X  _ \n" + " _  _  _  _ \n");
  }

  // Tests that multiple moves on the model work as intended
  @Test
  public void testModelMultipleMoves() {
    ReversiModel model = new BasicSquareReversiModel(4);

    model.startGame();

    model.addPieceToCoordinates(new Position2D(2, 0), playerBlack);
    model.addPieceToCoordinates(new Position2D(1, 0), playerWhite);
    model.addPieceToCoordinates(new Position2D(0, 0), playerBlack);

    TextualView modelView = new SquareReversiTextualView(model);

    Assert.assertEquals(modelView.toString(),
            " X  X  X  _ \n" + " _  X  X  _ \n" + " _  O  X  _ \n" + " _  _  _  _ \n");
  }

  // Test that passTurn correctly passes the turn from black to white without
  // black placing a piece
  @Test
  public void testPassTurnBlackToWhite() {
    ReversiModel model = new BasicSquareReversiModel(4);

    model.startGame();

    model.passTurn(playerBlack);

    TextualView modelView = new SquareReversiTextualView(model);

    Assert.assertEquals(modelView.toString(),
            " _  _  _  _ \n" + " _  X  O  _ \n" + " _  O  X  _ \n" + " _  _  _  _ \n");
  }

  // Test that passing turn twice causes game to end
  @Test
  public void testPassTurnTwice() {
    ReversiModel model = new BasicSquareReversiModel(4);

    model.startGame();

    model.passTurn(playerBlack);
    model.passTurn(playerWhite);

    Assert.assertTrue(model.isGameOver());
  }

  // Tests that isGameOver correctly realizes the game is immediately over
  @Test
  public void testIsGameOverImmediately() {
    ReversiModel model = new BasicSquareReversiModel(2);

    model.startGame();

    Assert.assertTrue(model.isGameOver());
  }

  // Tests that isGameOver correctly realizes that the game is not immediately
  // over
  @Test
  public void testIsGameOverNotImmediately() {
    ReversiModel model = new BasicSquareReversiModel(4);

    model.startGame();

    Assert.assertFalse(model.isGameOver());
  }


  // Test that isGameOver correctly realizes that the game is not over with
  // multiple moves made.
  @Test
  public void testIsGameOverMultipleMoves() {
    ReversiModel model = new BasicSquareReversiModel(4);

    model.startGame();

    model.addPieceToCoordinates(new Position2D(2, 0), playerBlack);
    model.addPieceToCoordinates(new Position2D(1, 0), playerWhite);
    model.addPieceToCoordinates(new Position2D(0, 0), playerBlack);

    Assert.assertFalse(model.isGameOver());
  }


  // Tests that isGameOver returns false when two passes are done with a move in
  // the middle.
  @Test
  public void testIsGameOverTwoPasses() {
    ReversiModel model = new BasicSquareReversiModel(4);

    model.startGame();

    model.passTurn(playerBlack);
    model.addPieceToCoordinates(new Position2D(1, 0), playerWhite);
    model.passTurn(playerBlack);

    Assert.assertFalse(model.isGameOver());
  }

  // Test that getCurrentScore correctly returns the score of the game without
  // it being over.
  @Test
  public void testGetCurrentScoreNotOver() {
    ReversiModel model = new BasicSquareReversiModel(4);

    model.startGame();

    model.addPieceToCoordinates(new Position2D(2, 0), playerBlack);
    model.addPieceToCoordinates(new Position2D(1, 0), playerWhite);
    model.addPieceToCoordinates(new Position2D(0, 0), playerBlack);

    Assert.assertEquals(model.getCurrentScore(PlayerType.BLACK), 6);
    Assert.assertEquals(model.getCurrentScore(PlayerType.WHITE), 1);
  }

  // Test that getNumRows correctly returns the number of rows in this game for a
  // width of 4.
  @Test
  public void testGetNumRows4() {
    ReversiModel model = new BasicSquareReversiModel(4);

    Assert.assertEquals(model.getNumRows(), 4);
  }

  // Test that getCellAt correctly returns an empty cell.
  @Test
  public void testGetCellAtEmpty() {
    ReversiModel model = new BasicSquareReversiModel(4);

    Assert.assertEquals(model.getCellAt(new Position2D(0, 0)).toString(), "_");
  }

  // Test that getCellAt correctly returns a white cell.
  @Test
  public void testGetCellAtWhite() {
    ReversiModel model = new BasicSquareReversiModel(4);

    Assert.assertEquals(model.getCellAt(new Position2D(2, 1)).toString(), "O");
  }

  // Test that getCellAt correctly returns a black cell.
  @Test
  public void testGetCellAtBlack() {
    ReversiModel model = new BasicSquareReversiModel(4);

    Assert.assertEquals(model.getCellAt(new Position2D(1, 1)).toString(), "X");
  }

  // Test that containsCoordinate correctly returns true
  @Test
  public void testContainsCoordinateTrue() {
    Position2D position = new Position2D(0, 0);

    Assert.assertTrue(position.containsCoordinate(0));
  }

  // Test that getX and getY correctly return the x and y values of a position
  @Test
  public void testGetXGetY() {
    Position2D position = new Position2D(0, 0);

    Assert.assertEquals(position.getQ(), 0);
    Assert.assertEquals(position.getR(), 0);
  }

  // Test that Position2D returns that the common coordinate and that
  // there isnt a common coordinate if there is not.
  @Test
  public void testCommonCoordinate() {
    Position2D position1 = new Position2D(0, 0);
    Position2D position2 = new Position2D(0, 1);
    Position2D position3 = new Position2D(1, 0);
    Position2D position4 = new Position2D(2, 2);

    Assert.assertEquals(position1.commonCoordinate(position2), "x");
    Assert.assertEquals(position1.commonCoordinate(position3), "y");
    Assert.assertEquals(position1.commonCoordinate(position4), "NoCommonCoordinate");

  }

  // Test that equals works for Position2D
  @Test
  public void testEqualsPosition2D() {
    Position2D position1 = new Position2D(0, 0);
    Position2D position2 = new Position2D(0, 0);
    Position2D position3 = new Position2D(0, 1);

    Assert.assertTrue(position1.equals(position2));
    Assert.assertFalse(position1.equals(position3));
  }

  // Test that isNextTo works for Position2D
  @Test
  public void testIsNeighborPosition2D() {
    Position2D position1 = new Position2D(0, 0);
    Position2D position2 = new Position2D(0, 1);
    Position2D position3 = new Position2D(1, 0);
    Position2D position4 = new Position2D(2, 2);

    Assert.assertTrue(position1.isNextTo(position2));
    Assert.assertTrue(position1.isNextTo(position3));
    Assert.assertFalse(position1.isNextTo(position4));
  }

  // Test that BasicSqaureReversiModel constructor correctly
  // throws illegal argument error for odd width number
  @Test
  public void testBasicSquareReversiModelConstructorOdd() {

    Assert.assertThrows(IllegalArgumentException.class, () -> {
      ReversiModel model = new BasicSquareReversiModel(3);
    });
  }

  // Test that BasicSqaureReversiModel constructor correctly
  // throws illegal argument error for low width number
  @Test
  public void testBasicSquareReversiModelConstructorLow() {

    Assert.assertThrows(IllegalArgumentException.class, () -> {
      ReversiModel model = new BasicSquareReversiModel(-3);
    });
  }

  // Tests that addPieceToCoordinate throws error when given position doesn't
  // exist.
  @Test
  public void testModelAddPieceToCoordinateNonExistingCoordinate() {
    ReversiModel model = new BasicSquareReversiModel(4, playerBlack, playerWhite);

    model.startGame();

    Assert.assertThrows(IllegalArgumentException.class, () -> {
      model.addPieceToCoordinates(new Position2D(10, -2), playerBlack);
    });
  }

  // Tests that addPieceToCoordinate throws error when given illogical invalid
  // move.
  @Test
  public void testModelAddPieceToCoordinateInvalidMove() {
    ReversiModel model = new BasicSquareReversiModel(4);

    Assert.assertThrows(IllegalStateException.class, () -> {
      model.addPieceToCoordinates(new Position2D(1, 1), new ComputerPlayer(PlayerType.BLACK));
    });
  }

  // Tests that getCellAt throws error when given position doesn't exist.
  @Test
  public void testModelGetCellAtNonExistingCoordinate() {
    ReversiModel model = new BasicSquareReversiModel(4);

    Assert.assertThrows(IllegalArgumentException.class, () -> {
      model.getCellAt(new Position2D(10, -2));
    });
  }

  // Tests that getBoardSize correctly returns the board size.
  @Test
  public void testModelGetBoardSize() {
    ReversiModel model = new BasicSquareReversiModel(4);

    Assert.assertEquals(model.getBoardSize(), 16);
  }


  // Tests that doesCurrentPlayerHaveValidMoves returns true when the current
  // player has a valid move.
  @Test
  public void testModelDoesCurrentPlayerHaveValidMovesPosnTrue() {
    ReversiModel model = new BasicSquareReversiModel(4);

    model.startGame();

    Assert.assertTrue(model.doesCurrentPlayerHaveValidMoves());
  }

  // Tests that doesCurrentPlayerHaveValidMovesPosn returns true when the current
  // player has a valid move at the position.
  @Test
  public void testModelDoesCurrentPlayerHaveValidMovesPosnTrue2() {
    ReversiModel model = new BasicSquareReversiModel(4);

    model.startGame();

    Assert.assertTrue(
            model.doesCurrentPlayerHaveValidMovesPosn(new Position2D(2, 0), playerBlack));
  }

  // Tests that getBoardCopy reutrns a copy of the board
  @Test
  public void testModelGetBoardCopy() {
    ReversiModel model = new BasicSquareReversiModel(4);

    model.startGame();

    Assert.assertEquals(model.getBoardCopy().get(new Position2D(0, 0)).toString(), "_");
    Assert.assertEquals(model.getBoardCopy().get(new Position2D(2, 1)).toString(), "O");
    Assert.assertEquals(model.getBoardCopy().get(new Position2D(2, 2)).toString(), "X");
  }


  // Tests that getScoreForMove returns the correct score (one) for the given
  // move.
  @Test
  public void testModelGetScoreForMove() {
    ReversiModel model = new BasicSquareReversiModel(4);

    model.startGame();

    Assert.assertEquals(model.getScoreForMove(new Position2D(2, 0)), 1);
  }

  // Tests that getScoreForMove returns the correct score (zero) for the given
  // move.
  @Test
  public void testModelGetScoreForMoveZero() {
    ReversiModel model = new BasicSquareReversiModel(4);

    model.startGame();

    Assert.assertEquals(model.getScoreForMove(new Position2D(0, 0)), 0);
  }

}

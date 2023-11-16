package cs3500.reversi;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cs3500.reversi.controller.GamePlayer;
import cs3500.reversi.controller.Player;
import cs3500.reversi.controller.PlayerType;
import cs3500.reversi.model.BasicReversiModel;
import cs3500.reversi.model.Cell;
import cs3500.reversi.model.CellType;
import cs3500.reversi.model.GameCell;
import cs3500.reversi.model.PositionAxial;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.textualview.ReversiTextualView;
import cs3500.reversi.textualview.TextualView;

/**
 * Tests the public methods in the model package.
 */
public class TestModel {

  private Player playerBlack;
  private Player playerWhite;

  @Before
  public void init() {
    playerBlack = new GamePlayer(PlayerType.BLACK);
    playerWhite = new GamePlayer(PlayerType.WHITE);
  }

  // Test that the model has initialized correctly with a correct starting board
  // of width 7.
  @Test
  public void testModelIntialization7() {
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

  // Test that the model has initialized correctly with a correct starting board
  // of width 7.
  @Test
  public void testModelIntialization3() {
    ReversiModel model = new BasicReversiModel(3);

    TextualView modelView = new ReversiTextualView(model);

    Assert.assertEquals(modelView.toString(), " X O\n" + //
            "O _ X\n" + //
            " X O\n");
  }

  // Tests that one valid move made on the model correctly works as intended.
  @Test
  public void testModelMoveOneMove() {

    ReversiModel model = new BasicReversiModel(7, playerBlack, playerWhite);

    model.addPieceToCoordinates(new PositionAxial(1, -2, 1), playerBlack);

    TextualView modelView = new ReversiTextualView(model);

    Assert.assertEquals(modelView.toString(), "   _ _ _ _\n" + //
            "  _ _ X _ _\n" + //
            " _ _ X X _ _\n" + //
            "_ _ O _ X _ _\n" + //
            " _ _ X O _ _\n" + //
            "  _ _ _ _ _\n" + //
            "   _ _ _ _\n");
  }

  // Tests that multiple valid moves on the model work as intended.
  @Test
  public void testModelMoveMultipleMoves() {
    ReversiModel model = new BasicReversiModel(7, playerBlack, playerWhite);

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

  // Tests that passTurn correctly passes the turn from black to white without
  // black placing a piece.
  @Test
  public void testModelPassTurn() {
    ReversiModel model = new BasicReversiModel(7, playerBlack, playerWhite);

    model.passTurn();
    model.addPieceToCoordinates(new PositionAxial(-2, 1, 1), playerWhite);

    TextualView modelView = new ReversiTextualView(model);

    Assert.assertEquals(modelView.toString(), "   _ _ _ _\n" + //
            "  _ _ _ _ _\n" + //
            " _ _ X O _ _\n" + //
            "_ _ O _ X _ _\n" + //
            " _ O O O _ _\n" + //
            "  _ _ _ _ _\n" + //
            "   _ _ _ _\n");
  }

  // Since pass turn cannot be directly tested, it is tested with isGameOver to
  // see
  // if two passed turns correctly cause a game to end
  @Test
  public void testModelPassTurnWithIsGameOverWorks() {
    ReversiModel model = new BasicReversiModel(7);

    model.passTurn();
    model.passTurn();

    Assert.assertEquals(model.isGameOver(), true);
  }

  // Tests that isGameOver correctly realizes that the game is immediately over
  @Test
  public void testModelIsGameOverAtStart() {
    ReversiModel model = new BasicReversiModel(3);

    Assert.assertEquals(model.isGameOver(), true);
  }

  // Tests that isGameOver correctly realizes that the game is not immediately
  // over
  @Test
  public void testModelIsNotGameOverAtStart() {
    ReversiModel model = new BasicReversiModel(7);

    Assert.assertEquals(model.isGameOver(), false);
  }

  // Test that isGameOver correctly realizes that the game is not over with
  // multiple moves made.
  @Test
  public void testModelIsNotGameOverWithMoves() {
    ReversiModel model = new BasicReversiModel(7, playerBlack, playerWhite);

    model.addPieceToCoordinates(new PositionAxial(1, -2, 1), playerBlack);
    model.addPieceToCoordinates(new PositionAxial(-2, 1, 1), playerWhite);
    model.addPieceToCoordinates(new PositionAxial(-3, 2, 1), playerBlack);
    model.addPieceToCoordinates(new PositionAxial(-3, 1, 2), playerWhite);

    Assert.assertEquals(model.isGameOver(), false);
  }

  // Test that isGameOver correctly realizes that the game is over with multiple
  // moves made.
  @Test
  public void testModelIsGameOverWithMoves() {
    ReversiModel model = new BasicReversiModel(5, playerBlack, playerWhite);

    model.addPieceToCoordinates(new PositionAxial(2, -1, -1), playerBlack);
    model.addPieceToCoordinates(new PositionAxial(-1, 2, -1), playerWhite);
    model.addPieceToCoordinates(new PositionAxial(-2, 1, 1), playerBlack);
    model.addPieceToCoordinates(new PositionAxial(-1, -1, 2), playerWhite);
    model.addPieceToCoordinates(new PositionAxial(1, 1, -2), playerBlack);
    model.addPieceToCoordinates(new PositionAxial(1, -2, 1), playerWhite);

    Assert.assertEquals(model.isGameOver(), true);
  }

  // Tests that isGameOver returns false when two passes are done with a move in
  // the middle.
  @Test
  public void testModelIsGameNotOverWithTwoPasses() {
    ReversiModel model = new BasicReversiModel(5, playerBlack, playerWhite);

    model.passTurn();
    model.addPieceToCoordinates(new PositionAxial(2, -1, -1), playerWhite);
    model.passTurn();

    Assert.assertEquals(model.isGameOver(), false);
  }

  // Test that getCurrentScore correctly returns the score of the game without
  // it being over.
  @Test
  public void testModelGetCurrentScoreGameNotOver() {
    ReversiModel model = new BasicReversiModel(7, playerBlack, playerWhite);

    model.addPieceToCoordinates(new PositionAxial(1, -2, 1), playerBlack);
    model.addPieceToCoordinates(new PositionAxial(-2, 1, 1), playerWhite);
    model.addPieceToCoordinates(new PositionAxial(-3, 2, 1), playerBlack);
    model.addPieceToCoordinates(new PositionAxial(-3, 1, 2), playerWhite);

    Assert.assertEquals(model.getCurrentScore(PlayerType.BLACK), 6);
    Assert.assertEquals(model.getCurrentScore(PlayerType.WHITE), 4);
  }

  // Test that getCurrentScore correctly returns the score of the game with
  // it being over.
  @Test
  public void testModelGetCurrentScoreGameOver() {
    ReversiModel model = new BasicReversiModel(5, playerBlack, playerWhite);

    model.addPieceToCoordinates(new PositionAxial(2, -1, -1), playerBlack);
    model.addPieceToCoordinates(new PositionAxial(-1, 2, -1), playerWhite);
    model.addPieceToCoordinates(new PositionAxial(-2, 1, 1), playerBlack);
    model.addPieceToCoordinates(new PositionAxial(-1, -1, 2), playerWhite);
    model.addPieceToCoordinates(new PositionAxial(1, 1, -2), playerBlack);
    model.addPieceToCoordinates(new PositionAxial(1, -2, 1), playerWhite);

    Assert.assertEquals(model.getCurrentScore(PlayerType.BLACK), 7);
    Assert.assertEquals(model.getCurrentScore(PlayerType.WHITE), 5);
  }

  // Test that getNumRows correctly returns the number of rows in this game for a
  // width of 7.
  @Test
  public void testModelGetNumRows7() {
    ReversiModel model = new BasicReversiModel(7);

    Assert.assertEquals(model.getNumRows(), 7);
  }

  // Test that getNumRows correctly returns the number of rows in this game for a
  // width of 5.
  @Test
  public void testModelGetNumRows5() {
    ReversiModel model = new BasicReversiModel(5);

    Assert.assertEquals(model.getNumRows(), 5);
  }

  // Test that getCellAt correctly returns an empty cell.
  @Test
  public void testModelGetCellAtEmpty() {
    ReversiModel model = new BasicReversiModel(5);

    Assert.assertEquals(model.getCellAt(new PositionAxial(0, -2, 2)).toString(), "_");
  }

  // Test that getCellAt correctly returns a black (X) cell.
  @Test
  public void testModelGetCellAtBlack() {
    ReversiModel model = new BasicReversiModel(5);

    Assert.assertEquals(model.getCellAt(new PositionAxial(0, -1, 1)).toString(), "X");
  }

  // Test that getCellAt correctly returns a white (O) cell.
  @Test
  public void testModelGetCellAtWhite() {
    ReversiModel model = new BasicReversiModel(5);

    Assert.assertEquals(model.getCellAt(new PositionAxial(-1, 0, 1)).toString(), "O");
  }

  // Test that PositionAxial returns that the common coordinate row and that
  // there isnt a common coordinate if there is not.
  @Test
  public void testPositionAxialCommonCoordinate() {
    PositionAxial posn = new PositionAxial(5, -5, 5);
    PositionAxial posnCommon = new PositionAxial(10, 10, 5);
    PositionAxial posnNotCommon = new PositionAxial(10, 10, 10);

    Assert.assertEquals(posn.commonCoordinate(posnCommon), "s");
    Assert.assertEquals(posn.commonCoordinate(posnNotCommon), "NoCommonCoordinate");
  }

  // Test that PositionAxial getQ, getR, and getS method work correctly.
  @Test
  public void testPositionAxialGetQGetRGetS() {
    PositionAxial posn = new PositionAxial(5, -5, 10);

    Assert.assertEquals(posn.getQ(), 5);
    Assert.assertEquals(posn.getR(), -5);
    Assert.assertEquals(posn.getS(), 10);
  }

  // Test that PositionAxial equals method works as intended.
  @Test
  public void testPositionAxialEquals() {
    PositionAxial posn = new PositionAxial(5, -5, 10);
    PositionAxial posnEqual = new PositionAxial(5, -5, 10);
    PositionAxial posnNotEqual = new PositionAxial(5, 5, 10);

    Assert.assertEquals(posn.equals(posnEqual), true);
    Assert.assertEquals(posn.equals(posnNotEqual), false);
  }

  // Test that PositionAxial isNextTo works as intended.
  @Test
  public void testPositionAxialIsNextTo() {
    PositionAxial posn = new PositionAxial(0, 0, 0);
    PositionAxial posnNextTo = new PositionAxial(-1, 1, 0);
    PositionAxial posnNotNotNextTo = new PositionAxial(-1, 1, 1);

    Assert.assertEquals(posn.isNextTo(posnNextTo), true);
    Assert.assertEquals(posn.isNextTo(posnNotNotNextTo), false);
  }

  // Test that cell toString returns the correct string based on the cell type.
  @Test
  public void testCellToString() {
    Cell cellEmpty = new GameCell(CellType.Empty);
    Cell cellPlayer = new GameCell(CellType.Player);

    cellPlayer.setCellToPlayer(new GamePlayer(PlayerType.BLACK));

    Assert.assertEquals(cellEmpty.toString(), "_");
    Assert.assertEquals(cellPlayer.toString(), "X");

    cellPlayer.setCellToPlayer(new GamePlayer(PlayerType.WHITE));

    Assert.assertEquals(cellPlayer.toString(), "O");
  }

  // Test that the cell setCellToPlayer method works as intended.
  @Test
  public void testCellSetCellToPlayer() {
    Cell cellPlayer = new GameCell(CellType.Player);

    cellPlayer.setCellToPlayer(new GamePlayer(PlayerType.BLACK));

    Assert.assertEquals(cellPlayer.getCellOwner(), "X");

    cellPlayer.setCellToPlayer(new GamePlayer(PlayerType.WHITE));

    Assert.assertEquals(cellPlayer.getCellOwner(), "O");
  }

  // Test that the cell getCellType method works as intended.
  @Test
  public void testCellGetCellType() {
    Cell cellEmpty = new GameCell(CellType.Empty);
    Cell cellPlayer = new GameCell(CellType.Player);

    Assert.assertEquals(cellEmpty.getCellType(), CellType.Empty);
    Assert.assertEquals(cellPlayer.getCellType(), CellType.Player);
  }

  // Test that the cell sameCellType method works as intended.
  @Test
  public void testCellSameCellType() {
    Cell cellEmpty = new GameCell(CellType.Empty);
    Cell cellPlayer = new GameCell(CellType.Player);

    Assert.assertEquals(cellEmpty.sameCellType(CellType.Empty), true);
    Assert.assertEquals(cellPlayer.sameCellType(CellType.Player), true);
    Assert.assertEquals(cellEmpty.sameCellType(CellType.Player), false);
    Assert.assertEquals(cellPlayer.sameCellType(CellType.Empty), false);
  }

  // Test that the cell getCellOwner method works as intended.
  @Test
  public void testCellGetCellOwner() {
    Cell cellEmpty = new GameCell(CellType.Empty);
    Cell cellPlayer = new GameCell(CellType.Player);

    Assert.assertEquals(cellEmpty.getCellOwner(), "");

    cellPlayer.setCellToPlayer(new GamePlayer(PlayerType.BLACK));

    Assert.assertEquals(cellPlayer.getCellOwner(), "X");

    cellPlayer.setCellToPlayer(new GamePlayer(PlayerType.WHITE));

    Assert.assertEquals(cellPlayer.getCellOwner(), "O");
  }

  // Test that BasicReversiModel constructor correctly throws illegal argument
  // error for
  // even width number
  @Test
  public void testModelConstructorErrorGivenEvenWidth() {

    Assert.assertThrows(IllegalArgumentException.class, () -> {
      ReversiModel model = new BasicReversiModel(6);
    });

  }

  // Test that BasicReversiModel constructor correctly throws illegal argument
  // error for
  // low width number
  @Test
  public void testModelConstructorErrorGivenLowWidth() {

    Assert.assertThrows(IllegalArgumentException.class, () -> {
      ReversiModel model = new BasicReversiModel(2);
    });

  }

  // Test that BasicReversiModel constructor correctly throws illegal argument
  // error for
  // negative width number
  @Test
  public void testModelConstructorErrorGivenNegativeWidth() {

    Assert.assertThrows(IllegalArgumentException.class, () -> {
      ReversiModel model = new BasicReversiModel(-2);
    });

  }

  // Tests that addPieceToCoordinate throws error when given position doesn't
  // exist.
  @Test
  public void testModelAddPieceToCoordinateNonExistingCoordinate() {
    ReversiModel model = new BasicReversiModel(7, playerBlack, playerWhite);

    Assert.assertThrows(IllegalArgumentException.class, () -> {
      model.addPieceToCoordinates(new PositionAxial(10, -2, 1), playerBlack);
    });
  }

  // Tests that addPieceToCoordinate throws error when given illogical invalid
  // move.
  @Test
  public void testModelAddPieceToCoordinateInvalidMove() {
    ReversiModel model = new BasicReversiModel(7);

    Assert.assertThrows(IllegalStateException.class, () -> {
      model.addPieceToCoordinates(new PositionAxial(0, -1, 1), new GamePlayer(PlayerType.BLACK));
    });
  }

  // Tests that getCellAt throws error when given position doesn't exist.
  @Test
  public void testModelGetCellAtNonExistingCoordinate() {
    ReversiModel model = new BasicReversiModel(7);

    Assert.assertThrows(IllegalArgumentException.class, () -> {
      model.getCellAt(new PositionAxial(10, -2, 1));
    });
  }

  // Tests that getBoardSize returns the correct board size with width 7.
  @Test
  public void testModelGetBoardSizeWidthSeven() {
    ReversiModel model = new BasicReversiModel(7);

    Assert.assertEquals(model.getBoardSize(), 37);
  }

  // Tests that getBoardSize returns the correct board size with width 11.
  @Test
  public void testModelGetBoardSizeWidthEleven() {
    ReversiModel model = new BasicReversiModel(11);

    Assert.assertEquals(model.getBoardSize(), 91);
  }

  // Tests that doesCurrentPlayerHaveValidMoves returns true when the current
  // player has valid moves.
  @Test
  public void testModelDoesCurrentPlayerHaveValidMovesTrue() {
    ReversiModel model = new BasicReversiModel(7);

    Assert.assertEquals(model.doesCurrentPlayerHaveValidMoves(), true);
  }

  // Tests that doesCurrentPlayerHaveValidMoves returns false when the current
  // player does not have valid moves.
  @Test
  public void testModelDoesCurrentPlayerHaveValidMovesFalse() {
    ReversiModel model = new BasicReversiModel(5, playerBlack, playerWhite);

    model.addPieceToCoordinates(new PositionAxial(2, -1, -1), playerBlack);
    model.addPieceToCoordinates(new PositionAxial(-1, 2, -1), playerWhite);
    model.addPieceToCoordinates(new PositionAxial(-2, 1, 1), playerBlack);
    model.addPieceToCoordinates(new PositionAxial(-1, -1, 2), playerWhite);
    model.addPieceToCoordinates(new PositionAxial(1, 1, -2), playerBlack);
    model.addPieceToCoordinates(new PositionAxial(1, -2, 1), playerWhite);

    Assert.assertEquals(model.doesCurrentPlayerHaveValidMoves(), false);
  }

  // Tests that doesCurrentPlayerHaveValidMovesPosn returns true when the current
  // player has a valid move at the position.
  @Test
  public void testModelDoesCurrentPlayerHaveValidMovesPosnTrue() {
    ReversiModel model = new BasicReversiModel(7);

    Assert.assertEquals(model.doesCurrentPlayerHaveValidMovesPosn(
            new PositionAxial(2, -1, -1), playerBlack), true);
  }

  // Tests that doesCurrentPlayerHaveValidMovesPosn returns false when the current
  // player does not have valid moves.
  @Test
  public void testModelDoesCurrentPlayerHaveValidMovesPosnFalse() {
    ReversiModel model = new BasicReversiModel(5, playerBlack, playerWhite);

    model.addPieceToCoordinates(new PositionAxial(2, -1, -1), playerBlack);
    model.addPieceToCoordinates(new PositionAxial(-1, 2, -1), playerWhite);
    model.addPieceToCoordinates(new PositionAxial(-2, 1, 1), playerBlack);
    model.addPieceToCoordinates(new PositionAxial(-1, -1, 2), playerWhite);
    model.addPieceToCoordinates(new PositionAxial(1, 1, -2), playerBlack);
    model.addPieceToCoordinates(new PositionAxial(1, -2, 1), playerWhite);

    Assert.assertEquals(model.doesCurrentPlayerHaveValidMovesPosn(
            new PositionAxial(0, 0, 0), playerBlack),
            false);
  }

  // Tests that getBoardCopy returns a copy of the board.
  @Test
  public void testModelGetBoardCopy() {
    ReversiModel model = new BasicReversiModel(5, playerBlack, playerWhite);

    model.addPieceToCoordinates(new PositionAxial(2, -1, -1), playerBlack);
    model.addPieceToCoordinates(new PositionAxial(-1, 2, -1), playerWhite);
    model.addPieceToCoordinates(new PositionAxial(-2, 1, 1), playerBlack);
    model.addPieceToCoordinates(new PositionAxial(-1, -1, 2), playerWhite);
    model.addPieceToCoordinates(new PositionAxial(1, 1, -2), playerBlack);
    model.addPieceToCoordinates(new PositionAxial(1, -2, 1), playerWhite);

    Assert.assertEquals(model.getBoardCopy().get(new PositionAxial(2, -1, -1)).toString(), "X");
    Assert.assertEquals(model.getBoardCopy().get(new PositionAxial(-1, 2, -1)).toString(), "O");
    Assert.assertEquals(model.getBoardCopy().get(new PositionAxial(0, 0, 0)).toString(), "_");

  }

  // Tests that getScoreForMove returns the correct score (one) for the given
  // move.
  @Test
  public void testModelGetScoreForMoveOne() {

    ReversiModel model = new BasicReversiModel(5);

    Assert.assertEquals(model.getScoreForMove(new PositionAxial(2, -1, -1)), 1);
  }

  // Tests that getScoreForMove returns the correct score (zero) for the given
  // move.
  @Test
  public void testModelGetScoreForMoveZero() {

    ReversiModel model = new BasicReversiModel(5, playerBlack, playerWhite);

    model.addPieceToCoordinates(new PositionAxial(2, -1, -1), playerBlack);

    Assert.assertEquals(model.getScoreForMove(new PositionAxial(2, -2, 0)), 0);

  }

  // Tests that getScoreForMove returns the correct score (two) for the given
  // move.
  @Test
  public void testModelGetScoreForMoveTwo() {

    ReversiModel model = new BasicReversiModel(5, playerBlack, playerWhite);

    model.addPieceToCoordinates(new PositionAxial(1, -2, 1), playerBlack);

    Assert.assertEquals(model.getScoreForMove(new PositionAxial(2, -3, 1)), 2);

  }

}

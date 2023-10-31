package cs3500.reversi.test;

import org.junit.Assert;
import org.junit.Test;

import cs3500.reversi.controller.GamePlayer;
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
    // Test that the model has initialized correctly with a correct starting board
    // of width 7.
    @Test
    public void testModelIntialization7() {
        ReversiModel model = new BasicReversiModel(7);

        model.startGame();

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

        model.startGame();

        TextualView modelView = new ReversiTextualView(model);

        Assert.assertEquals(modelView.toString(), " X O\n" + //
                "O _ X\n" + //
                " X O\n");
    }

    // Tests that one valid move made on the model correctly works as intended.
    @Test
    public void testModelMoveOneMove() {
        ReversiModel model = new BasicReversiModel(7);

        model.startGame();

        model.addPieceToCoordinates(new PositionAxial(1, -2, 1));

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
        ReversiModel model = new BasicReversiModel(7);

        model.startGame();

        model.addPieceToCoordinates(new PositionAxial(1, -2, 1));
        model.addPieceToCoordinates(new PositionAxial(-2, 1, 1));
        model.addPieceToCoordinates(new PositionAxial(-3, 2, 1));
        model.addPieceToCoordinates(new PositionAxial(-3, 1, 2));

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
    // black placing a pieve.
    @Test
    public void testModelPassTurn() {
        ReversiModel model = new BasicReversiModel(7);

        model.startGame();

        model.passTurn();
        model.addPieceToCoordinates(new PositionAxial(-2, 1, 1));

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

        model.startGame();

        model.passTurn();
        model.passTurn();

        Assert.assertEquals(model.isGameOver(), true);
    }

    // Tests that isGameOver correctly realizes that the game is immedietly over
    @Test
    public void testModelIsGameOverAtStart() {
        ReversiModel model = new BasicReversiModel(3);

        model.startGame();

        Assert.assertEquals(model.isGameOver(), true);
    }

    // Tests that isGameOver correctly realizes that the game is not immedietly over
    @Test
    public void testModelIsNotGameOverAtStart() {
        ReversiModel model = new BasicReversiModel(7);

        model.startGame();

        Assert.assertEquals(model.isGameOver(), false);
    }

    // Test that isGameOver correctly realizes that the game is not over with
    // multiple moves made.
    @Test
    public void testModelIsNotGameOverWithMoves() {
        ReversiModel model = new BasicReversiModel(7);

        model.startGame();

        model.addPieceToCoordinates(new PositionAxial(1, -2, 1));
        model.addPieceToCoordinates(new PositionAxial(-2, 1, 1));
        model.addPieceToCoordinates(new PositionAxial(-3, 2, 1));
        model.addPieceToCoordinates(new PositionAxial(-3, 1, 2));

        Assert.assertEquals(model.isGameOver(), false);
    }

    // Test that isGameOver correctly realizes that the game is over with multiple
    // moves made.
    @Test
    public void testModelIsGameOverWithMoves() {
        ReversiModel model = new BasicReversiModel(5);

        model.startGame();

        model.addPieceToCoordinates(new PositionAxial(2, -1, -1));
        model.addPieceToCoordinates(new PositionAxial(-1, 2, -1));
        model.addPieceToCoordinates(new PositionAxial(-2, 1, 1));
        model.addPieceToCoordinates(new PositionAxial(-1, -1, 2));
        model.addPieceToCoordinates(new PositionAxial(1, 1, -2));
        model.addPieceToCoordinates(new PositionAxial(1, -2, 1));

        Assert.assertEquals(model.isGameOver(), true);
    }

    // Test that getCurrentWinner correctly returns the winner of the game without
    // it being over.
    @Test
    public void testModelGetCurrentWinnerGameNotOver() {
        ReversiModel model = new BasicReversiModel(7);

        model.startGame();

        model.addPieceToCoordinates(new PositionAxial(1, -2, 1));
        model.addPieceToCoordinates(new PositionAxial(-2, 1, 1));
        model.addPieceToCoordinates(new PositionAxial(-3, 2, 1));
        model.addPieceToCoordinates(new PositionAxial(-3, 1, 2));

        Assert.assertEquals(model.getCurrentWinner().toString(), new GamePlayer(PlayerType.BLACK).toString());
    }

    // Test that getCurrentWinner correctly returns the winner of the game with
    // it being over.
    @Test
    public void testModelGetCurrentWinnerGameOver() {
        ReversiModel model = new BasicReversiModel(5);

        model.startGame();

        model.addPieceToCoordinates(new PositionAxial(2, -1, -1));
        model.addPieceToCoordinates(new PositionAxial(-1, 2, -1));
        model.addPieceToCoordinates(new PositionAxial(-2, 1, 1));
        model.addPieceToCoordinates(new PositionAxial(-1, -1, 2));
        model.addPieceToCoordinates(new PositionAxial(1, 1, -2));
        model.addPieceToCoordinates(new PositionAxial(1, -2, 1));

        Assert.assertEquals(model.getCurrentWinner().toString(), new GamePlayer(PlayerType.BLACK).toString());
    }

    // Test that getNumRows correctly returns the number of rows in this game for a
    // width of 7.
    @Test
    public void testModelGetNumRows7() {
        ReversiModel model = new BasicReversiModel(7);

        model.startGame();

        Assert.assertEquals(model.getNumRows(), 7);
    }

    // Test that getNumRows correctly returns the number of rows in this game for a
    // width of 5.
    @Test
    public void testModelGetNumRows5() {
        ReversiModel model = new BasicReversiModel(5);

        model.startGame();

        Assert.assertEquals(model.getNumRows(), 5);
    }

    // Test that getCellAt correctly returns an empty cell.
    @Test
    public void testModelGetCellAtEmpty() {
        ReversiModel model = new BasicReversiModel(5);

        model.startGame();

        Assert.assertEquals(model.getCellAt(new PositionAxial(0, -2, 2)).toString(), "_");
    }

    // Test that getCellAt correctly returns a black (X) cell.
    @Test
    public void testModelGetCellAtBlack() {
        ReversiModel model = new BasicReversiModel(5);

        model.startGame();

        Assert.assertEquals(model.getCellAt(new PositionAxial(0, -1, 1)).toString(), "X");
    }

    // Test that getCellAt correctly returns a white (O) cell.
    @Test
    public void testModelGetCellAtWhite() {
        ReversiModel model = new BasicReversiModel(5);

        model.startGame();

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
}

package cs3500.reversi.test;

import org.junit.Assert;
import org.junit.Test;

import cs3500.reversi.controller.GamePlayer;
import cs3500.reversi.controller.PlayerType;
import cs3500.reversi.model.BasicReversiModel;
import cs3500.reversi.model.PositionAxial;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.textualview.ReversiTextualView;
import cs3500.reversi.textualview.TextualView;

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
    // width of 7
    @Test
    public void testModelGetNumRows7() {
        ReversiModel model = new BasicReversiModel(7);

        model.startGame();

        Assert.assertEquals(model.getNumRows(), 7);
    }

    // Test that getNumRows correctly returns the number of rows in this game for a
    // width of 5
    @Test
    public void testModelGetNumRows5() {
        ReversiModel model = new BasicReversiModel(5);

        model.startGame();

        Assert.assertEquals(model.getNumRows(), 5);
    }

    // Test that getCellAt correctly returns an empty cell
    @Test
    public void testModelGetCellAtEmpty() {
        ReversiModel model = new BasicReversiModel(5);

        model.startGame();

        TextualView modelView = new ReversiTextualView(model);

        System.out.println(modelView.toString());

        Assert.assertEquals(model.getCellAt(new PositionAxial(0, -2, 2)).toString(), "_");
    }

    // Test that getCellAt correctly returns a black (X) cell
    @Test
    public void testModelGetCellAtBlack() {
        ReversiModel model = new BasicReversiModel(5);

        model.startGame();

        TextualView modelView = new ReversiTextualView(model);

        System.out.println(modelView.toString());

        Assert.assertEquals(model.getCellAt(new PositionAxial(0, -1, 1)).toString(), "X");
    }

    // Test that getCellAt correctly returns a white (O) cell
    @Test
    public void testModelGetCellAtWhite() {
        ReversiModel model = new BasicReversiModel(5);

        model.startGame();

        TextualView modelView = new ReversiTextualView(model);

        System.out.println(modelView.toString());

        Assert.assertEquals(model.getCellAt(new PositionAxial(-1, 0, 1)).toString(), "O");
    }
}

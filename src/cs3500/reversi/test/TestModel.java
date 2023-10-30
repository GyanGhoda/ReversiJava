package cs3500.reversi.test;

import org.junit.Assert;
import org.junit.Test;

import cs3500.reversi.model.BasicReversiModel;
import cs3500.reversi.model.PositionAxial;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.textualview.ReversiTextualView;
import cs3500.reversi.textualview.TextualView;

public class TestModel {
    // Test that the model has initialized correctly with a correct starting board.
    @Test
    public void testModelIntialization() {
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

        TextualView modelView = new ReversiTextualView(model);

        System.out.println(modelView.toString());

        System.out.println(model.getCellAt(new PositionAxial(1, -1, 0)));

        model.addPieceToCoordinates(new PositionAxial(2, -1, -1));
        // model.addPieceToCoordinates(new PositionAxial(-3, 2, 1));
        // model.addPieceToCoordinates(new PositionAxial(-3, 1, 2));

        Assert.assertEquals(model.isGameOver(), false);
    }
}

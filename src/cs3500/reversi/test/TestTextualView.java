package cs3500.reversi.test;

import org.junit.Assert;
import org.junit.Test;

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
    // board
    // of width 7.
    @Test
    public void testModelTextualViewIntialization7() {
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

    // Test that the TextualView has initialized correctly with a correct starting
    // board
    // of width 7.
    @Test
    public void testModelTextualIntialization3() {
        ReversiModel model = new BasicReversiModel(3);

        model.startGame();

        TextualView modelView = new ReversiTextualView(model);

        Assert.assertEquals(modelView.toString(), " X O\n" + //
                "O _ X\n" + //
                " X O\n");
    }

    // Test that the TextualView has rendered the board correctly with a starting
    // width of 7 and one valid move.
    @Test
    public void testModelTextualViewMoveOneMove() {
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

    // Test that the TextualView has rendered the board correctly with a starting
    // width of 7 and multiple valid moves.
    @Test
    public void testModelTextualViewMoveMultipleMoves() {
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

}

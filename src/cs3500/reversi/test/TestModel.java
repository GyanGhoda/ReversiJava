package cs3500.reversi.test;

import org.junit.Assert;
import org.junit.Test;

import cs3500.reversi.model.BasicReversiModel;
import cs3500.reversi.model.PositionAxial;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.textualview.ReversiTextualView;
import cs3500.reversi.textualview.TextualView;

public class TestModel {
    @Test
    public void testModelIntialization() {
        ReversiModel model = new BasicReversiModel(7);

        model.startGame();

        TextualView modelView = new ReversiTextualView(model);

        Assert.assertEquals(modelView.toString(), "   _ _ _ _\n" + //
                "  _ _ _ _ _\n" + //
                " _ _ X O _ _\n" + //
                "_ _ X _ X _ _\n" + //
                " _ _ O O _ _\n" + //
                "  _ _ _ _ _\n" + //
                "   _ _ _ _\n");
    }

    @Test
    public void testModelMove() {
        ReversiModel model = new BasicReversiModel(7);

        model.startGame();

        model.addPieceToCoordinates(new PositionAxial(1, -2, 1));

        TextualView modelView = new ReversiTextualView(model);

        Assert.assertEquals(modelView.toString(), "   _ _ _ _\n" + //
                "  _ _ _ _ _\n" + //
                " _ _ X O _ _\n" + //
                "_ _ X _ X _ _\n" + //
                " _ _ O O _ _\n" + //
                "  _ _ _ _ _\n" + //
                "   _ _ _ _\n");
    }
}

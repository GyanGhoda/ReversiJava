package cs3500.reversi.test;

import org.junit.Assert;
import org.junit.Test;

import cs3500.reversi.model.BasicReversiModel;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.textualview.ReversiTextualView;
import cs3500.reversi.textualview.TextualView;

public class TestModel {
    @Test
    public void testModelIntialization() {
        ReversiModel model = new BasicReversiModel(7);

        model.startGame();

        TextualView modelView = new ReversiTextualView(model);

        System.out.println(modelView.toString());
    }

}

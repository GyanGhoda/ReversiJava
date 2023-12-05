package cs3500.reversi;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cs3500.reversi.controller.ComputerPlayer;
import cs3500.reversi.controller.Player;
import cs3500.reversi.controller.PlayerType;
import cs3500.reversi.model.BasicReversiModel;
import cs3500.reversi.model.BasicSquareReversiModel;
import cs3500.reversi.model.Cell;
import cs3500.reversi.model.CellType;
import cs3500.reversi.model.GameCell;
import cs3500.reversi.model.Position2D;
import cs3500.reversi.model.PositionAxial;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.textualview.ReversiTextualView;
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
    // of width 7.
    @Test
    public void testModelIntialization7() {
        ReversiModel model = new BasicSquareReversiModel(8);

        model.startGame();

        model.addPieceToCoordinates(new Position2D(2, 4), playerBlack);
    }
}

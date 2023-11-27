package cs3500.reversi;

import org.junit.Assert;
import org.junit.Test;
import cs3500.reversi.controller.BasicReversiController;
import cs3500.reversi.controller.ComputerPlayer;
import cs3500.reversi.controller.Player;
import cs3500.reversi.controller.PlayerType;
import cs3500.reversi.model.BasicReversiModel;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.visualview.HexagonalFrame;
import cs3500.reversi.visualview.ReversiVisualView;

/**
 * Tests the public methods in the controller package.
 */
public class TestController {

  // Test that toString correctly returns "X" for a black player
  @Test
  public void testPlayerToStringBlack() {
    ComputerPlayer blackPlayer = new ComputerPlayer(PlayerType.BLACK);

    Assert.assertEquals(blackPlayer.toString(), "X");
  }

  // Test that toString correctly returns "O" for a white player
  @Test
  public void testPlayerToStringWhite() {
    ComputerPlayer whitePlayer = new ComputerPlayer(PlayerType.WHITE);

    Assert.assertEquals(whitePlayer.toString(), "O");
  }

  // Test that notifyToRefresh correctly updates the view
  // @Test
  // public void testNotifyToRefresh() {

  //   ReversiModel model = new BasicReversiModel(5);
  //   Player gamePlayerBlack = new ComputerPlayer(PlayerType.BLACK);
  //   ReversiVisualView view = new HexagonalFrame(model);

  //   BasicReversiController controller = new BasicReversiController(model, gamePlayerBlack, view);

  // }



}

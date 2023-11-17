package cs3500.reversi;

import org.junit.Assert;
import org.junit.Test;

import cs3500.reversi.controller.ComputerPlayer;
import cs3500.reversi.controller.PlayerType;

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
}

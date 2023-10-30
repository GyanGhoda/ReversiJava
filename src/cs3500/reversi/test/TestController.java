package cs3500.reversi.test;

import org.junit.Assert;
import org.junit.Test;

import cs3500.reversi.controller.GamePlayer;
import cs3500.reversi.controller.PlayerType;

/**
 * Tests the public methods in the controller package.
 */
public class TestController {
    // Test that toString correctly returns "X" for a black player
    @Test
    public void testPlayerToStringBlack() {
        GamePlayer blackPlayer = new GamePlayer(PlayerType.BLACK);

        Assert.assertEquals(blackPlayer.toString(), "X");
    }

    // Test that toString correctly returns "O" for a white player
    @Test
    public void testPlayerToStringWhite() {
        GamePlayer whitePlayer = new GamePlayer(PlayerType.WHITE);

        Assert.assertEquals(whitePlayer.toString(), "O");
    }
}

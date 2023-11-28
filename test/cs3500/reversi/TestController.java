package cs3500.reversi;

import org.junit.Assert;
import org.junit.Test;
import cs3500.reversi.controller.BasicReversiController;
import cs3500.reversi.controller.ComputerPlayer;
import cs3500.reversi.controller.HumanPlayer;
import cs3500.reversi.controller.MockController;
import cs3500.reversi.controller.Player;
import cs3500.reversi.controller.PlayerType;
import cs3500.reversi.model.BasicReversiModel;
import cs3500.reversi.model.PositionAxial;
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

  // Test that notifyYourTurn correctly returns the default position for a Human Player
  @Test
  public void testNotifyYourTurnHuman() {
    ReversiModel model = new BasicReversiModel(5);
    Player gamePlayerBlack = new HumanPlayer(PlayerType.BLACK);

    Assert.assertEquals(gamePlayerBlack.notifyYourTurn(model).getQ(), 0);
    Assert.assertEquals(gamePlayerBlack.notifyYourTurn(model).getR(), 0);
    Assert.assertEquals(gamePlayerBlack.notifyYourTurn(model).getS(), 0);
  }

  // Test that notifyYourTurn correctly returns the appropriate position for a Computer Player
  @Test
  public void testNotifyYourTurnComputer() {
    ReversiModel model = new BasicReversiModel(5);
    Player gamePlayerBlack = new ComputerPlayer(PlayerType.BLACK);

    Assert.assertEquals(gamePlayerBlack.notifyYourTurn(model).getQ(), -1);
    Assert.assertEquals(gamePlayerBlack.notifyYourTurn(model).getR(), -1);
    Assert.assertEquals(gamePlayerBlack.notifyYourTurn(model).getS(), 2);
  }

  // Test that requestMove correctly returns the appropriate position for a Human Player
  @Test
  public void testRequestMoveHuman() {
    ReversiModel model = new BasicReversiModel(5);
    Player gamePlayerBlack = new HumanPlayer(PlayerType.BLACK);

    Assert.assertEquals(gamePlayerBlack.requestMove(model, new PositionAxial(0, 0, 0)).getQ(), 0);
    Assert.assertEquals(gamePlayerBlack.requestMove(model, new PositionAxial(0, 0, 0)).getR(), 0);
    Assert.assertEquals(gamePlayerBlack.requestMove(model, new PositionAxial(0, 0, 0)).getS(), 0);
  }

  // Test that requestMove correctly returns the appropriate position for a Computer Player
  @Test
  public void testRequestMoveComputer() {
    ReversiModel model = new BasicReversiModel(5);
    Player gamePlayerBlack = new ComputerPlayer(PlayerType.BLACK);

    Assert.assertEquals(gamePlayerBlack.requestMove(model, new PositionAxial(0, 0, 0)).getQ(), -1);
    Assert.assertEquals(gamePlayerBlack.requestMove(model, new PositionAxial(0, 0, 0)).getR(), -1);
    Assert.assertEquals(gamePlayerBlack.requestMove(model, new PositionAxial(0, 0, 0)).getS(), 2);
  }

  // Test that toString correctly returns symbols for a human player
  @Test
  public void testPlayerToStringHuman() {
    HumanPlayer blackPlayer = new HumanPlayer(PlayerType.BLACK);
    HumanPlayer whitePlayer = new HumanPlayer(PlayerType.WHITE);

    Assert.assertEquals(blackPlayer.toString(), "X");
    Assert.assertEquals(whitePlayer.toString(), "O");

  }

  // Test that toString correctly returns symbols for a computer player
  @Test
  public void testPlayerToStringComputer() {
    ComputerPlayer blackPlayer = new ComputerPlayer(PlayerType.BLACK);
    ComputerPlayer whitePlayer = new ComputerPlayer(PlayerType.WHITE);

    Assert.assertEquals(blackPlayer.toString(), "X");
    Assert.assertEquals(whitePlayer.toString(), "O");

  }

  // Test that equals correctly returns true for two human players
  @Test
  public void testPlayerEqualsHuman() {
    HumanPlayer blackPlayer1 = new HumanPlayer(PlayerType.BLACK);
    HumanPlayer blackPlayer2 = new HumanPlayer(PlayerType.BLACK);

    Assert.assertTrue(blackPlayer1.equals(blackPlayer2));
  }

  // Test that equals correctly returns true for two computer players
  @Test
  public void testPlayerEqualsComputer() {
    ComputerPlayer blackPlayer1 = new ComputerPlayer(PlayerType.BLACK);
    ComputerPlayer blackPlayer2 = new ComputerPlayer(PlayerType.BLACK);

    Assert.assertTrue(blackPlayer1.equals(blackPlayer2));
  }

  // Test that getOppositePlayer correctly returns the opposite player for a human player
  @Test
  public void testGetOppositePlayerHuman() {
    HumanPlayer blackPlayer = new HumanPlayer(PlayerType.BLACK);
    HumanPlayer whitePlayer = new HumanPlayer(PlayerType.WHITE);

    Assert.assertEquals(blackPlayer.getOppositePlayer(), whitePlayer);
    Assert.assertEquals(whitePlayer.getOppositePlayer(), blackPlayer);
  }

  // Test that getOppositePlayer correctly returns the opposite player for a computer player
  @Test
  public void testGetOppositePlayerComputer() {
    ComputerPlayer blackPlayer = new ComputerPlayer(PlayerType.BLACK);
    ComputerPlayer whitePlayer = new ComputerPlayer(PlayerType.WHITE);

    Assert.assertEquals(blackPlayer.getOppositePlayer(), whitePlayer);
    Assert.assertEquals(whitePlayer.getOppositePlayer(), blackPlayer);
  }

  // Test that getPlayer correctly returns the player for a human player
  @Test
  public void testControllerGetPlayerHuman() {

    ReversiModel model = new BasicReversiModel(5);
    HumanPlayer blackPlayer = new HumanPlayer(PlayerType.BLACK);
    ReversiVisualView view = new HexagonalFrame(model);

    BasicReversiController controller = new BasicReversiController(model, blackPlayer, view);
    Assert.assertEquals(controller.getPlayer(), "X");
  }

  // Test that getPlayer correctly returns the player for a computer player
  @Test
  public void testControllerGetPlayerComputer() {

    ReversiModel model = new BasicReversiModel(5);
    ComputerPlayer blackPlayer = new ComputerPlayer(PlayerType.BLACK);
    ReversiVisualView view = new HexagonalFrame(model);

    BasicReversiController controller = new BasicReversiController(model, blackPlayer, view);
    Assert.assertEquals(controller.getPlayer(), "X");
  }

  // Test that controller moveToCoordinate correctly attempts to move the piece for a human player
  @Test
  public void testControllerMoveToCoordinateHuman() {

    ReversiModel model = new BasicReversiModel(5);
    HumanPlayer blackPlayer = new HumanPlayer(PlayerType.BLACK);
    ReversiVisualView view = new HexagonalFrame(model);

    MockController controller = new MockController(model, blackPlayer, view);
    controller.moveToCoordinate(new PositionAxial(0, 0, 0));
    Assert.assertEquals(controller.getLog(), "Requested move to coordinate: Q: 0, R: 0, S: 0\n");
  }

  // Test that controller moveToCoordinate correctly attempts to move the piece for computer player
  @Test
  public void testControllerMoveToCoordinateComputer() {

    ReversiModel model = new BasicReversiModel(5);
    ComputerPlayer blackPlayer = new ComputerPlayer(PlayerType.BLACK);
    ReversiVisualView view = new HexagonalFrame(model);

    MockController controller = new MockController(model, blackPlayer, view);
    controller.moveToCoordinate(new PositionAxial(0, 0, 0));
    Assert.assertEquals(controller.getLog(), "Requested move to coordinate: Q: 0, R: 0, S: 0\n");
  }

  // Test that controller passTurn correctly attempts to pass the turn 
  @Test
  public void testControllerPassTurn() {

    ReversiModel model = new BasicReversiModel(5);
    HumanPlayer blackPlayer = new HumanPlayer(PlayerType.BLACK);
    ReversiVisualView view = new HexagonalFrame(model);

    MockController controller = new MockController(model, blackPlayer, view);
    controller.passTurn();
    Assert.assertEquals(controller.getLog(), "Requested to pass turn.\n");
  }

  // Test that controller notifyToRefresh correctly attempts to refresh the view
  @Test
  public void testControllerNotifyToRefresh() {

    ReversiModel model = new BasicReversiModel(5);
    HumanPlayer blackPlayer = new HumanPlayer(PlayerType.BLACK);
    ReversiVisualView view = new HexagonalFrame(model);

    MockController controller = new MockController(model, blackPlayer, view);
    controller.notifyToRefresh("X");
    Assert.assertEquals(controller.getLog(), "Notified to refresh.\n");
  }



}

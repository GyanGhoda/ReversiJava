package cs3500.reversi;

import org.junit.Assert;
import org.junit.Test;
import cs3500.reversi.controller.BasicReversiController;
import cs3500.reversi.controller.ComputerPlayer;
import cs3500.reversi.controller.PlayerType;
import cs3500.reversi.model.BasicReversiModel;
import cs3500.reversi.model.BasicSquareReversiModel;
import cs3500.reversi.model.ReadOnlyReversiModel;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.visualview.HexagonalFrame;
import cs3500.reversi.visualview.HexagonalPanelMock;
import cs3500.reversi.visualview.ReversiVisualView;
import cs3500.reversi.visualview.SquareFrame;
import cs3500.reversi.visualview.SquarePanelMock;

/**
 * Tests for the hexagonal and square visual views.
 */
public class TestVisualView {

  // tests that the mouse returns proper output when clicking (200, 200)
  @Test
  public void testMouse200200() {
    ReversiModel model = new BasicReversiModel(7);
    HexagonalPanelMock mockPanel = new HexagonalPanelMock(model, 800, 800);
    ReversiVisualView view = new HexagonalFrame(model, false);
    mockPanel.setUpFeatures(new BasicReversiController(model, new ComputerPlayer(PlayerType.BLACK),
            view));

    mockPanel.induceMouseEvent(200, 200);

    Assert.assertEquals(mockPanel.getLog(),
        "Clicked on hexagon at:\n" + "Q: -1\n" + "R: -2\n" + "S: 3");
  }
  
  // tests that the mouse returns proper output when clicking (200, 200)
  // for square reversi
  @Test
  public void testMouse200200Square() {
    ReversiModel model = new BasicSquareReversiModel(4);
    SquarePanelMock mockPanel = new SquarePanelMock(model, 800, 800);
    ReversiVisualView view = new SquareFrame(model);
    mockPanel.setUpFeatures(
        new BasicReversiController(model, new ComputerPlayer(PlayerType.BLACK), view));

    mockPanel.induceMouseEvent(200, 200);

    Assert.assertEquals(mockPanel.getLog(),
        "Clicked on square at X: 1, Y: 1\n");
  }

  // tests that the mouse returns proper output when clicking (0, 0)
  @Test
  public void testMouse00() {
    ReadOnlyReversiModel model = new BasicReversiModel(7);
    HexagonalPanelMock mockPanel = new HexagonalPanelMock(model, 800, 800);

    mockPanel.induceMouseEvent(0, 0);

    Assert.assertEquals(mockPanel.getLog(), "");
  }

  // tests that the mouse returns proper output when clicking (0, 0)

  // tests that the mouse returns proper output when clicking (400, 400)
  @Test
  public void testMouse400400() {
    ReversiModel model = new BasicReversiModel(7);
    HexagonalPanelMock mockPanel = new HexagonalPanelMock(model, 800, 800);
    ReversiVisualView view = new HexagonalFrame(model, false);
    mockPanel.setUpFeatures(new BasicReversiController(model, new ComputerPlayer(PlayerType.BLACK),
            view));

    mockPanel.induceMouseEvent(400, 400);

    Assert.assertEquals(mockPanel.getLog(), "Clicked on hexagon at:\n" +
            "Q: 0\n" +
            "R: 0\n" +
            "S: 0");
  }

  // tests that the m key signals moves properly
  @Test
  public void testKeyMove() {
    ReversiModel model = new BasicReversiModel(7);
    HexagonalPanelMock mockPanel = new HexagonalPanelMock(model, 800, 800);
    ReversiVisualView view = new HexagonalFrame(model, false);
    mockPanel.setUpFeatures(new BasicReversiController(model, new ComputerPlayer(PlayerType.BLACK),
            view));

    mockPanel.induceKeyEvent(77);

    Assert.assertEquals(mockPanel.getLog(), "User has pressed m key to move");
  }

  // tests that the p key signals passes properly
  @Test
  public void testKeyPass() {
    ReversiModel model = new BasicReversiModel(7);
    model.startGame();
    HexagonalPanelMock mockPanel = new HexagonalPanelMock(model, 800, 800);
    ReversiVisualView view = new HexagonalFrame(model, false);
    BasicReversiController controller = new BasicReversiController(model, new ComputerPlayer(PlayerType.BLACK),
            view);
    mockPanel.setUpFeatures(controller);

    mockPanel.induceKeyEvent(80);

    Assert.assertEquals(mockPanel.getLog(), "User has pressed p key to pass");
  }

  // tests that the a key doesn't do anything
  @Test
  public void testKeyIrrelevant() {
    ReadOnlyReversiModel model = new BasicReversiModel(7);
    HexagonalPanelMock mockPanel = new HexagonalPanelMock(model, 800, 800);

    mockPanel.induceKeyEvent(65);

    Assert.assertEquals(mockPanel.getLog(), "");
  }
}

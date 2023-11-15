package cs3500.reversi;

import java.util.HashMap;
import org.junit.Assert;
import org.junit.Test;
import cs3500.reversi.controller.AvoidCellsNextToCorner;
import cs3500.reversi.controller.CaptureCellsInCorner;
import cs3500.reversi.controller.CaptureMostPieces;
import cs3500.reversi.controller.GamePlayer;
import cs3500.reversi.controller.Player;
import cs3500.reversi.controller.PlayerType;
import cs3500.reversi.controller.ReversiStrategy;
import cs3500.reversi.model.BasicReversiModel;
import cs3500.reversi.model.BasicReversiModelMockTranscript;
import cs3500.reversi.model.Cell;
import cs3500.reversi.model.CellType;
import cs3500.reversi.model.GameCell;
import cs3500.reversi.model.PositionAxial;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.textualview.ReversiTextualView;
import cs3500.reversi.textualview.TextualView;
import cs3500.reversi.visualview.HexagonalFrame;
import cs3500.reversi.visualview.ReversiVisualView;

public class TestStrategies {

  // Test that the strategy chooses the uppermost-leftmost move that will capture the most pieces
  // when there are multiple moves that will capture the most pieces.
  @Test
  public void testCaptureMostStrategyTieStartGame() {

    BasicReversiModelMockTranscript model = new BasicReversiModelMockTranscript();

    ReversiStrategy strat = new CaptureMostPieces();

    PositionAxial stratPosn = strat.chooseMove(model, PlayerType.BLACK);

    Assert.assertEquals(model.getLog(),
        "getScoreForMove(Q: 1, R: -2, S: 1)\n" + "getScoreForMove(Q: -2, R: 1, S: 1)\n"
            + "getScoreForMove(Q: 1, R: 1, S: -2)\n" + "getScoreForMove(Q: -1, R: -1, S: 2)\n"
            + "getScoreForMove(Q: 2, R: -1, S: -1)\n" + "getScoreForMove(Q: -1, R: 2, S: -1)\n");
    Assert.assertEquals(stratPosn, new PositionAxial(-1, -1, 2));
  }
  
  // Test that the strategy chooses the uppermost-leftmost move that will capture the most pieces
  // when there is one move that will capture the most pieces.
  @Test
  public void testCaptureMostStrategyOneMoveStartGame() {

    Cell blackCell = new GameCell(CellType.Player);
    Player player = new GamePlayer(PlayerType.BLACK);
    blackCell.setCellToPlayer(player);

    HashMap<PositionAxial, Cell> boardToAdd = new HashMap<PositionAxial, Cell>();
    boardToAdd.put(new PositionAxial(1, -2, 1), blackCell);

    BasicReversiModelMockTranscript model =
        new BasicReversiModelMockTranscript(7, boardToAdd, new GamePlayer(PlayerType.WHITE));

    ReversiStrategy strat = new CaptureMostPieces();

    PositionAxial stratPosn = strat.chooseMove(model, PlayerType.WHITE);

    Assert.assertEquals(model.getLog(),
        "getScoreForMove(Q: -2, R: 1, S: 1)\n" + "getScoreForMove(Q: 1, R: 1, S: -2)\n"
            + "getScoreForMove(Q: -1, R: -1, S: 2)\n" + "getScoreForMove(Q: 2, R: -1, S: -1)\n"
            + "getScoreForMove(Q: -1, R: 2, S: -1)\n" + "getScoreForMove(Q: 1, R: -3, S: 2)\n"
            + "getScoreForMove(Q: 2, R: -3, S: 1)\n");
    Assert.assertEquals(stratPosn, new PositionAxial(2, -3, 1));
  }
  
  // Test that the strategy that avoids cells next to the corner works as intended with a 
  // move to make that is next to the corner
  @Test
  public void testAvoidCellsNextToCornerMoveStartGame() {

    Cell blackCell = new GameCell(CellType.Player);
    Player player = new GamePlayer(PlayerType.BLACK);
    blackCell.setCellToPlayer(player);

    HashMap<PositionAxial, Cell> boardToAdd = new HashMap<PositionAxial, Cell>();
    boardToAdd.put(new PositionAxial(1, -2, 1), blackCell);

    BasicReversiModelMockTranscript model =
        new BasicReversiModelMockTranscript(7, boardToAdd, new GamePlayer(PlayerType.WHITE));

    ReversiStrategy strat = new AvoidCellsNextToCorner();

    PositionAxial stratPosn = strat.chooseMove(model, PlayerType.WHITE);

    Assert.assertEquals(model.getLog(),
        "getScoreForMove(Q: -2, R: 1, S: 1)\n" + "getScoreForMove(Q: 1, R: 1, S: -2)\n"
            + "getScoreForMove(Q: -1, R: -1, S: 2)\n" + "getScoreForMove(Q: 2, R: -1, S: -1)\n"
            + "getScoreForMove(Q: -1, R: 2, S: -1)\n" + "getScoreForMove(Q: 1, R: -3, S: 2)\n"
            + "getScoreForMove(Q: 2, R: -3, S: 1)\n");
    Assert.assertEquals(stratPosn, new PositionAxial(-1, -1, 2));
  }

  // Test that the strategy that captures cells in the corner works as intended with 
  // no move to make
  @Test
  public void testCaptureCellsInCornerNoMoveStartGame() {

    Cell blackCell = new GameCell(CellType.Player);
    Player player = new GamePlayer(PlayerType.BLACK);
    blackCell.setCellToPlayer(player);

    HashMap<PositionAxial, Cell> boardToAdd = new HashMap<PositionAxial, Cell>();
    boardToAdd.put(new PositionAxial(1, -2, 1), blackCell);

    BasicReversiModelMockTranscript model =
        new BasicReversiModelMockTranscript(7, boardToAdd, new GamePlayer(PlayerType.WHITE));

    ReversiStrategy strat = new CaptureCellsInCorner();

    PositionAxial stratPosn = strat.chooseMove(model, PlayerType.WHITE);

    Assert.assertEquals(model.getLog(),
        "getScoreForMove(Q: -2, R: 1, S: 1)\n" + "getScoreForMove(Q: 1, R: 1, S: -2)\n"
            + "getScoreForMove(Q: -1, R: -1, S: 2)\n" + "getScoreForMove(Q: 2, R: -1, S: -1)\n"
            + "getScoreForMove(Q: -1, R: 2, S: -1)\n" + "getScoreForMove(Q: 1, R: -3, S: 2)\n"
            + "getScoreForMove(Q: 2, R: -3, S: 1)\n");
    Assert.assertEquals(stratPosn, new PositionAxial(37, 37, 37));
  }

  // Test that the strategy that captures cells in the corner makes the
  // correct choice when there is a move available in the corner
  @Test
  public void testCaptureCellsInCornerOneMoveStartGame() {

    Cell blackCell = new GameCell(CellType.Player);
    Player player = new GamePlayer(PlayerType.BLACK);
    blackCell.setCellToPlayer(player);

    HashMap<PositionAxial, Cell> boardToAdd = new HashMap<PositionAxial, Cell>();
    boardToAdd.put(new PositionAxial(2, -2, 0), blackCell);

    BasicReversiModelMockTranscript model =
        new BasicReversiModelMockTranscript(7, boardToAdd, new GamePlayer(PlayerType.WHITE));

    ReversiStrategy strat = new CaptureCellsInCorner();

    PositionAxial stratPosn = strat.chooseMove(model, PlayerType.WHITE);

    Assert.assertEquals(model.getLog(),
        "getScoreForMove(Q: 3, R: -3, S: 0)\n" + "getScoreForMove(Q: 1, R: -2, S: 1)\n"
            + "getScoreForMove(Q: -2, R: 1, S: 1)\n" + "getScoreForMove(Q: 1, R: 1, S: -2)\n"
            + "getScoreForMove(Q: -1, R: -1, S: 2)\n" + "getScoreForMove(Q: 2, R: -1, S: -1)\n"
            + "getScoreForMove(Q: -1, R: 2, S: -1)\n");
    Assert.assertEquals(stratPosn, new PositionAxial(3, -3, 0));
  }

  




}

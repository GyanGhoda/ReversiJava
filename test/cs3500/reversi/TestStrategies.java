package cs3500.reversi;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

import cs3500.reversi.controller.ComputerPlayer;
import cs3500.reversi.controller.Player;
import cs3500.reversi.controller.PlayerType;
import cs3500.reversi.model.BasicReversiModelMockTranscript;
import cs3500.reversi.model.BasicSquareReversiModelMock;
import cs3500.reversi.model.Cell;
import cs3500.reversi.model.CellType;
import cs3500.reversi.model.GameCell;
import cs3500.reversi.model.GamePosition;
import cs3500.reversi.model.Position2D;
import cs3500.reversi.model.PositionAxial;
import cs3500.reversi.strategies.AvoidCellsNextToCorner;
import cs3500.reversi.strategies.CaptureCellsInCorner;
import cs3500.reversi.strategies.CaptureMostPieces;
import cs3500.reversi.strategies.MinimizeNextOpponentMove;
import cs3500.reversi.strategies.ReversiStrategy;
import cs3500.reversi.strategies.TryTwoStrategies;

/**
 * Tests the strategies for playing Reversi for both hexagonal and square models.
 */
public class TestStrategies {

  // Test that the strategy chooses the uppermost-leftmost move that will
  // capture
  // the most pieces when there are multiple moves that will capture the most
  // pieces.
  @Test
  public void testCaptureMostStrategyTieStartGame() {

    BasicReversiModelMockTranscript model = new BasicReversiModelMockTranscript();

    ReversiStrategy strat = new CaptureMostPieces();

    GamePosition stratPosn = strat.chooseMove(model, PlayerType.BLACK);

    Assert.assertEquals(model.getLog(),
            "getScoreForMove(Q: 1, R: -2, S: 1)\n"
                    + "getScoreForMove(Q: -2, R: 1, S: " + "1)\n"
                    + "getScoreForMove(Q: 1, R: 1, S: -2)\n"
                    + "getScoreForMove(Q: -1, R: -1, S: 2)\n"
                    + "getScoreForMove(Q: 2, R: -1, S: -1)\n"
                    + "getScoreForMove(Q: -1, R: 2, S: -1)\n");
    Assert.assertEquals(stratPosn, new PositionAxial(-1, -1, 2));
  }

  // Test that the strategy chooses the uppermost-leftmost move that will
  // capture in square reversi.
  // the most pieces when there are multiple moves that will capture the most
  // pieces.
  @Test
  public void testCaptureMostStrategyTieStartGameSquare() {

    BasicSquareReversiModelMock model = new
            BasicSquareReversiModelMock();

    ReversiStrategy strat = new CaptureMostPieces();

    GamePosition stratPosn = strat.chooseMove(model, PlayerType.BLACK);

    Assert.assertEquals(model.getLog(),
            "getScoreForMove(X: 5, Y: 3)\n" +
                    "getScoreForMove(X: 3, Y: 5)\n" +
                    "getScoreForMove(X: 6, Y: 4)\n" +
                    "getScoreForMove(X: 4, Y: 6)\n");
    Assert.assertEquals(stratPosn, new Position2D(3, 5));
  }

  // Test that the strategy chooses the uppermost-leftmost move that will
  // capture
  // the most pieces when there is one move that will capture the most pieces.
  @Test
  public void testCaptureMostStrategyOneMoveStartGame() {

    Cell blackCell = new GameCell(CellType.Player);
    Player player = new ComputerPlayer(PlayerType.BLACK);
    blackCell.setCellToPlayer(player);

    HashMap<PositionAxial, Cell> boardToAdd = new HashMap<PositionAxial, Cell>();
    boardToAdd.put(new PositionAxial(1, -2, 1), blackCell);

    BasicReversiModelMockTranscript model =
            new BasicReversiModelMockTranscript(7, boardToAdd,
                    new ComputerPlayer(PlayerType.WHITE));

    ReversiStrategy strat = new CaptureMostPieces();

    GamePosition stratPosn = strat.chooseMove(model, PlayerType.WHITE);

    Assert.assertEquals(model.getLog(),
            "getScoreForMove(Q: -2, R: 1, S: 1)\n"
                    + "getScoreForMove(Q: 1, R: 1, S: -2)\n"
                    + "getScoreForMove(Q: -1, R: -1, S: 2)\n"
                    + "getScoreForMove(Q: 2, R: -1, S: -1)\n"
                    + "getScoreForMove(Q: -1, R: 2, S: -1)\n"
                    + "getScoreForMove(Q: 1, R: -3, S: 2)\n"
                    + "getScoreForMove(Q: 2, R: -3, S: 1)\n");
    Assert.assertEquals(stratPosn, new PositionAxial(2, -3, 1));
  }

  // Test that the strategy chooses the uppermost-leftmost move that will
  // capture
  // the most pieces when there is one move that will capture the most pieces.
  // in the square reversi game.
  @Test
  public void testCaptureMostStrategyOneMoveStartGameSquare() {

    Cell blackCell = new GameCell(CellType.Player);
    Player player = new ComputerPlayer(PlayerType.BLACK);
    blackCell.setCellToPlayer(player);

    HashMap<GamePosition, Cell> boardToAdd = new HashMap<GamePosition, Cell>();
    boardToAdd.put(new Position2D(5, 3), blackCell);

    BasicSquareReversiModelMock model =
            new BasicSquareReversiModelMock(4, boardToAdd,
                    new ComputerPlayer(PlayerType.WHITE));

    ReversiStrategy strat = new CaptureMostPieces();

    GamePosition stratPosn = strat.chooseMove(model, PlayerType.WHITE);

    Assert.assertEquals(model.getLog(),
            "getScoreForMove(X: 1, Y: 0)\n" +
                    "getScoreForMove(X: 0, Y: 1)\n" +
                    "getScoreForMove(X: 3, Y: 2)\n" +
                    "getScoreForMove(X: 2, Y: 3)\n");
    Assert.assertEquals(stratPosn, new Position2D(0, 1));


  }


  // Tests that the strategy is model-dependent and listens to the model
  // for hexagonal reversi
  @Test
  public void testCaptureMostStrategyMockListensToModel() {

    Cell blackCell = new GameCell(CellType.Player);
    Player player = new ComputerPlayer(PlayerType.BLACK);
    blackCell.setCellToPlayer(player);

    HashMap<PositionAxial, Cell> boardToAdd = new HashMap<PositionAxial, Cell>();
    boardToAdd.put(new PositionAxial(1, 1, -2), blackCell);

    BasicReversiModelMockTranscript model =
            new BasicReversiModelMockTranscript(7, boardToAdd,
                    new ComputerPlayer(PlayerType.WHITE));

    ReversiStrategy strat = new CaptureMostPieces();

    GamePosition stratPosn = strat.chooseMove(model, PlayerType.WHITE);

    Assert.assertEquals(model.getLog(),
            "getScoreForMove(Q: 1, R: -2, S: 1)\n"
                    + "getScoreForMove(Q: -2, R: 1, S: 1)\n"
                    + "getScoreForMove(Q: -1, R: -1, S: 2)\n"
                    + "getScoreForMove(Q: 2, R: -1, S: -1)\n"
                    + "getScoreForMove(Q: -1, R: 2, S: -1)\n"
                    + "getScoreForMove(Q: 2, R: 1, S: -3)\n"
                    + "getScoreForMove(Q: 1, R: 2, S: -3)\n");
    // Correct logical move would be (1, 2, -3) but the mock tells the strategy
    // that move is not valid, so the strategy correctly listens to the mock
    // and chooses a different move
    Assert.assertEquals(stratPosn, new PositionAxial(-1, -1, 2));
  }

  // Tests that the strategy is model-dependent and listens to the model
  // for square reversi
  @Test
  public void testCaptureMostStrategyMockListensToModelSquare() {
    Cell blackCell = new GameCell(CellType.Player);
    Player player = new ComputerPlayer(PlayerType.BLACK);
    blackCell.setCellToPlayer(player);

    HashMap<GamePosition, Cell> boardToAdd = new HashMap<GamePosition, Cell>();
    boardToAdd.put(new Position2D(3, 2), blackCell);

    BasicSquareReversiModelMock model =
            new BasicSquareReversiModelMock(4, boardToAdd,
                    new ComputerPlayer(PlayerType.WHITE));

    ReversiStrategy strat = new CaptureMostPieces();

    GamePosition stratPosn = strat.chooseMove(model, PlayerType.WHITE);

    Assert.assertEquals(model.getLog(),
            "getScoreForMove(X: 1, Y: 0)\n" +
                    "getScoreForMove(X: 0, Y: 1)\n" +
                    "getScoreForMove(X: 2, Y: 3)\n");
    // Correct logical move would be (0, 1) but the mock tells the strategy
    // that move is not valid, so the strategy correctly listens to the mock
    // and chooses a different move
    Assert.assertEquals(stratPosn, new Position2D(0, 1));
  }

  // Test that the strategy that chooses the uppermost-leftmost move that will
  // pass
  // when no proper moves are available for hexagonal reversi.
  @Test
  public void testCaptureMostStrategyNoMoveStartGame() {

    Cell blackCell = new GameCell(CellType.Player);
    Player player = new ComputerPlayer(PlayerType.BLACK);
    blackCell.setCellToPlayer(player);

    HashMap<PositionAxial, Cell> boardToAdd = new HashMap<PositionAxial, Cell>();
    boardToAdd.put(new PositionAxial(1, -2, 1), blackCell);
    boardToAdd.put(new PositionAxial(-1, -1, 2), blackCell);
    boardToAdd.put(new PositionAxial(-2, 1, 1), blackCell);
    boardToAdd.put(new PositionAxial(-1, 2, -1), blackCell);
    boardToAdd.put(new PositionAxial(1, 1, -2), blackCell);
    boardToAdd.put(new PositionAxial(2, -1, -1), blackCell);

    BasicReversiModelMockTranscript model =
            new BasicReversiModelMockTranscript(5, boardToAdd,
                    new ComputerPlayer(PlayerType.WHITE));

    ReversiStrategy strat = new CaptureMostPieces();

    GamePosition stratPosn = strat.chooseMove(model, PlayerType.WHITE);

    Assert.assertEquals(model.getLog(), "");
    Assert.assertEquals(stratPosn, new PositionAxial(19, 19, 19));
  }

  // Test that the strategy that chooses the uppermost-leftmost move that will
  // pass when no proper moves are available for square reversi.
  @Test
  public void testCaptureMostStrategyNoMoveStartGameSquare() {

    Cell blackCell = new GameCell(CellType.Player);
    Player player = new ComputerPlayer(PlayerType.BLACK);
    blackCell.setCellToPlayer(player);

    HashMap<GamePosition, Cell> boardToAdd = new HashMap<GamePosition, Cell>();
    boardToAdd.put(new Position2D(0, 0), blackCell);
    boardToAdd.put(new Position2D(0, 1), blackCell);
    boardToAdd.put(new Position2D(1, 0), blackCell);
    boardToAdd.put(new Position2D(1, 1), blackCell);

    BasicSquareReversiModelMock model =
            new BasicSquareReversiModelMock(2, boardToAdd,
                    new ComputerPlayer(PlayerType.WHITE));

    ReversiStrategy strat = new CaptureMostPieces();

    GamePosition stratPosn = strat.chooseMove(model, PlayerType.WHITE);

    Assert.assertEquals(model.getLog(), "");
    Assert.assertEquals(stratPosn, new PositionAxial(4, 4, 4));
  }

  // Test that the strategy that avoids cells next to the corner works as
  // intended
  // with one move to make that is next to the corner for hexagonal reversi.
  @Test
  public void testAvoidCellsNextToCornerMoveStartGame() {

    Cell blackCell = new GameCell(CellType.Player);
    Player player = new ComputerPlayer(PlayerType.BLACK);
    blackCell.setCellToPlayer(player);

    HashMap<PositionAxial, Cell> boardToAdd = new HashMap<PositionAxial, Cell>();
    boardToAdd.put(new PositionAxial(1, -2, 1), blackCell);

    BasicReversiModelMockTranscript model =
            new BasicReversiModelMockTranscript(7, boardToAdd,
                    new ComputerPlayer(PlayerType.WHITE));

    ReversiStrategy strat = new AvoidCellsNextToCorner();

    GamePosition stratPosn = strat.chooseMove(model, PlayerType.WHITE);

    Assert.assertEquals(model.getLog(),
            "getScoreForMove(Q: -2, R: 1, S: 1)\n"
                    + "getScoreForMove(Q: 1, R: 1, S: -2)\n"
                    + "getScoreForMove(Q: -1, R: -1, S: 2)\n"
                    + "getScoreForMove(Q: 2, R: -1, S: -1)\n"
                    + "getScoreForMove(Q: -1, R: 2, S: -1)\n"
                    + "getScoreForMove(Q: 1, R: -3, S: 2)\n"
                    + "getScoreForMove(Q: 2, R: -3, S: 1)\n");
    Assert.assertEquals(stratPosn, new PositionAxial(-1, -1, 2));
  }

  // Test that the strategy that avoids cells next to the corner works as
  // intended for square reversi.
  @Test
  public void testAvoidCellsNextToCornerMoveStartGameSquare() {

    Cell blackCell = new GameCell(CellType.Player);
    Player player = new ComputerPlayer(PlayerType.BLACK);
    blackCell.setCellToPlayer(player);

    HashMap<GamePosition, Cell> boardToAdd = new HashMap<GamePosition, Cell>();
    boardToAdd.put(new Position2D(0, 0), blackCell);

    BasicSquareReversiModelMock model =
            new BasicSquareReversiModelMock(4, boardToAdd,
                    new ComputerPlayer(PlayerType.WHITE));

    ReversiStrategy strat = new AvoidCellsNextToCorner();

    GamePosition stratPosn = strat.chooseMove(model, PlayerType.WHITE);

    Assert.assertEquals(model.getLog(),
            "getScoreForMove(X: 1, Y: 0)\n" +
                    "getScoreForMove(X: 0, Y: 1)\n" +
                    "getScoreForMove(X: 3, Y: 2)\n" +
                    "getScoreForMove(X: 2, Y: 3)\n");
    Assert.assertEquals(stratPosn, new Position2D(0, 1));
  }

  // Test that the strategy that captures cells in the corner works as intended
  // with no move to make for hexagonal reversi.
  @Test
  public void testCaptureCellsInCornerNoMoveStartGame() {

    Cell blackCell = new GameCell(CellType.Player);
    Player player = new ComputerPlayer(PlayerType.BLACK);
    blackCell.setCellToPlayer(player);

    HashMap<PositionAxial, Cell> boardToAdd = new HashMap<PositionAxial, Cell>();
    boardToAdd.put(new PositionAxial(1, -2, 1), blackCell);

    BasicReversiModelMockTranscript model =
            new BasicReversiModelMockTranscript(7, boardToAdd,
                    new ComputerPlayer(PlayerType.WHITE));

    ReversiStrategy strat = new CaptureCellsInCorner();

    GamePosition stratPosn = strat.chooseMove(model, PlayerType.WHITE);

    Assert.assertEquals(model.getLog(),
            "getScoreForMove(Q: -2, R: 1, S: 1)\n"
                    + "getScoreForMove(Q: 1, R: 1, S: -2)\n"
                    + "getScoreForMove(Q: -1, R: -1, S: 2)\n"
                    + "getScoreForMove(Q: 2, R: -1, S: -1)\n"
                    + "getScoreForMove(Q: -1, R: 2, S: -1)\n"
                    + "getScoreForMove(Q: 1, R: -3, S: 2)\n"
                    + "getScoreForMove(Q: 2, R: -3, S: 1)\n");
    Assert.assertEquals(stratPosn, new PositionAxial(37, 37, 37));
  }

  // Test that the strategy that captures cells in the corner works as intended
  // for square reversi.
  @Test
  public void testCaptureCellsInCornerNoMoveStartGameSquare() {

    Cell blackCell = new GameCell(CellType.Player);
    Player player = new ComputerPlayer(PlayerType.BLACK);
    blackCell.setCellToPlayer(player);

    HashMap<GamePosition, Cell> boardToAdd = new HashMap<GamePosition, Cell>();
    boardToAdd.put(new Position2D(0, 0), blackCell);

    BasicSquareReversiModelMock model =
            new BasicSquareReversiModelMock(4, boardToAdd,
                    new ComputerPlayer(PlayerType.WHITE));

    ReversiStrategy strat = new CaptureCellsInCorner();

    GamePosition stratPosn = strat.chooseMove(model, PlayerType.WHITE);

    Assert.assertEquals(model.getLog(),
            "getScoreForMove(X: 1, Y: 0)\n" +
                    "getScoreForMove(X: 0, Y: 1)\n" +
                    "getScoreForMove(X: 3, Y: 2)\n" +
                    "getScoreForMove(X: 2, Y: 3)\n");
    Assert.assertEquals(stratPosn, new PositionAxial(16, 16, 16));
  }

  // Test that the strategy that captures cells in the corner makes the
  // correct choice when there is a move available in the corner
  // for hexagonal reversi.
  @Test
  public void testCaptureCellsInCornerOneMoveStartGame() {

    Cell blackCell = new GameCell(CellType.Player);
    Player player = new ComputerPlayer(PlayerType.BLACK);
    blackCell.setCellToPlayer(player);

    HashMap<PositionAxial, Cell> boardToAdd = new HashMap<PositionAxial, Cell>();
    boardToAdd.put(new PositionAxial(2, -2, 0), blackCell);

    BasicReversiModelMockTranscript model =
            new BasicReversiModelMockTranscript(7, boardToAdd,
                    new ComputerPlayer(PlayerType.WHITE));

    ReversiStrategy strat = new CaptureCellsInCorner();

    GamePosition stratPosn = strat.chooseMove(model, PlayerType.WHITE);

    Assert.assertEquals(model.getLog(),
            "getScoreForMove(Q: 3, R: -3, S: 0)\n"
                    + "getScoreForMove(Q: 1, R: -2, S: 1)\n"
                    + "getScoreForMove(Q: -2, R: 1, S: 1)\n"
                    + "getScoreForMove(Q: 1, R: 1, S: -2)\n"
                    + "getScoreForMove(Q: -1, R: -1, S: 2)\n"
                    + "getScoreForMove(Q: 2, R: -1, S: -1)\n"
                    + "getScoreForMove(Q: -1, R: 2, S: -1)\n");
    Assert.assertEquals(stratPosn, new PositionAxial(3, -3, 0));
  }

  // Test that the strategy that captures cells in the corner makes the
  // correct choice when there is a move available in the corner
  // for square reversi.
  @Test
  public void testCaptureCellsInCornerOneMoveStartGameSquare() {

    Cell blackCell = new GameCell(CellType.Player);
    Player player = new ComputerPlayer(PlayerType.BLACK);
    blackCell.setCellToPlayer(player);

    HashMap<GamePosition, Cell> boardToAdd = new HashMap<GamePosition, Cell>();
    boardToAdd.put(new Position2D(3, 3), blackCell);

    BasicSquareReversiModelMock model =
            new BasicSquareReversiModelMock(4, boardToAdd,
                    new ComputerPlayer(PlayerType.WHITE));

    ReversiStrategy strat = new CaptureCellsInCorner();

    GamePosition stratPosn = strat.chooseMove(model, PlayerType.WHITE);

    Assert.assertEquals(model.getLog(),
            "getScoreForMove(X: 1, Y: 0)\n" +
                    "getScoreForMove(X: 0, Y: 1)\n" +
                    "getScoreForMove(X: 3, Y: 2)\n" +
                    "getScoreForMove(X: 2, Y: 3)\n");
  }

  // Test that tryTwoStrategies works as intended when the first strategy is
  // to capture the most pieces and the second strategy is to capture cells in
  // the corner for hexagonal reversi.
  @Test
  public void
  testTryTwoStrategiesCaptureMostPiecesCaptureCellsInCornerStartGame() {

    Cell blackCell = new GameCell(CellType.Player);
    Player player = new ComputerPlayer(PlayerType.BLACK);
    blackCell.setCellToPlayer(player);

    HashMap<PositionAxial, Cell> boardToAdd = new HashMap<PositionAxial, Cell>();
    boardToAdd.put(new PositionAxial(2, -2, 0), blackCell);

    BasicReversiModelMockTranscript model =
            new BasicReversiModelMockTranscript(7, boardToAdd,
                    new ComputerPlayer(PlayerType.WHITE));

    ReversiStrategy strat1 = new CaptureMostPieces();
    ReversiStrategy strat2 = new CaptureCellsInCorner();
    ReversiStrategy strat = new TryTwoStrategies(strat1, strat2);

    GamePosition stratPosn = strat.chooseMove(model, PlayerType.WHITE);

    Assert.assertEquals(model.getLog(),
            "getScoreForMove(Q: 3, R: -3, S: 0)\n"
                    + "getScoreForMove(Q: 1, R: -2, S: 1)\n"
                    + "getScoreForMove(Q: -2, R: 1, S: 1)\n"
                    + "getScoreForMove(Q: 1, R: 1, S: -2)\n"
                    + "getScoreForMove(Q: -1, R: -1, S: 2)\n"
                    + "getScoreForMove(Q: 2, R: -1, S: -1)\n"
                    + "getScoreForMove(Q: -1, R: 2, S: -1)\n"
                    + "getScoreForMove(Q: 3, R: -3, S: 0)\n"
                    + "getScoreForMove(Q: 1, R: -2, S: 1)\n"
                    + "getScoreForMove(Q: -2, R: 1, S: 1)\n"
                    + "getScoreForMove(Q: 1, R: 1, S: -2)\n"
                    + "getScoreForMove(Q: -1, R: -1, S: 2)\n"
                    + "getScoreForMove(Q: 2, R: -1, S: -1)\n"
                    + "getScoreForMove(Q: -1, R: 2, S: -1)\n");

    Assert.assertEquals(stratPosn, new PositionAxial(-1, -1, 2));
  }

  // Test that tryTwoStrategies works as intended when the first strategy is
  // to capture the most pieces and the second strategy is to capture cells in
  // the corner for square reversi.
  @Test
  public void testTryTwoStrategiesCaptureMostPiecesCaptureCellsInCornerStartGameSquare() {

    Cell blackCell = new GameCell(CellType.Player);
    Player player = new ComputerPlayer(PlayerType.BLACK);
    blackCell.setCellToPlayer(player);

    HashMap<GamePosition, Cell> boardToAdd = new HashMap<GamePosition, Cell>();
    boardToAdd.put(new Position2D(3, 3), blackCell);

    BasicSquareReversiModelMock model =
            new BasicSquareReversiModelMock(4, boardToAdd,
                    new ComputerPlayer(PlayerType.WHITE));

    ReversiStrategy strat1 = new CaptureMostPieces();
    ReversiStrategy strat2 = new CaptureCellsInCorner();
    ReversiStrategy strat = new TryTwoStrategies(strat1, strat2);

    GamePosition stratPosn = strat.chooseMove(model, PlayerType.WHITE);

    Assert.assertEquals(model.getLog(),
            "getScoreForMove(X: 1, Y: 0)\n" +
                    "getScoreForMove(X: 0, Y: 1)\n" +
                    "getScoreForMove(X: 3, Y: 2)\n" +
                    "getScoreForMove(X: 2, Y: 3)\n" +
                    "getScoreForMove(X: 1, Y: 0)\n" +
                    "getScoreForMove(X: 0, Y: 1)\n" +
                    "getScoreForMove(X: 3, Y: 2)\n" +
                    "getScoreForMove(X: 2, Y: 3)\n");
    Assert.assertEquals(stratPosn, new Position2D(0, 1));
  }


  // Test that tryTwoStrategies works as intended when the first strategy is
  // to capture cells in the corner and the second strategy is to capture the
  // most pieces for hexagonal reversi.
  @Test
  public void
  testTryTwoStrategiesCaptureCellsInCornerCaptureMostPiecesStartGame() {

    Cell blackCell = new GameCell(CellType.Player);
    Player player = new ComputerPlayer(PlayerType.BLACK);
    blackCell.setCellToPlayer(player);

    HashMap<PositionAxial, Cell> boardToAdd = new HashMap<PositionAxial, Cell>();
    boardToAdd.put(new PositionAxial(2, -2, 0), blackCell);

    BasicReversiModelMockTranscript model =
            new BasicReversiModelMockTranscript(7, boardToAdd,
                    new ComputerPlayer(PlayerType.WHITE));

    ReversiStrategy strat1 = new CaptureCellsInCorner();
    ReversiStrategy strat2 = new CaptureMostPieces();
    ReversiStrategy strat = new TryTwoStrategies(strat1, strat2);

    GamePosition stratPosn = strat.chooseMove(model, PlayerType.WHITE);

    Assert.assertEquals(model.getLog(),
            "getScoreForMove(Q: 3, R: -3, S: 0)\n"
                    + "getScoreForMove(Q: 1, R: -2, S: 1)\n"
                    + "getScoreForMove(Q: -2, R: 1, S: 1)\n"
                    + "getScoreForMove(Q: 1, R: 1, S: -2)\n"
                    + "getScoreForMove(Q: -1, R: -1, S: 2)\n"
                    + "getScoreForMove(Q: 2, R: -1, S: -1)\n"
                    + "getScoreForMove(Q: -1, R: 2, S: -1)\n"
                    + "getScoreForMove(Q: 3, R: -3, S: 0)\n"
                    + "getScoreForMove(Q: 1, R: -2, S: 1)\n"
                    + "getScoreForMove(Q: -2, R: 1, S: 1)\n"
                    + "getScoreForMove(Q: 1, R: 1, S: -2)\n"
                    + "getScoreForMove(Q: -1, R: -1, S: 2)\n"
                    + "getScoreForMove(Q: 2, R: -1, S: -1)\n"
                    + "getScoreForMove(Q: -1, R: 2, S: -1)\n");

    Assert.assertEquals(stratPosn, new PositionAxial(3, -3, 0));
  }

  // Test that tryTwoStrategies works as intended when the first strategy is
  // to capture cells in the corner and the second strategy is to capture the
  // most pieces for square reversi.
  @Test
  public void testTryTwoStrategiesCaptureCellsInCornerCaptureMostPiecesStartGameSquare() {

    Cell blackCell = new GameCell(CellType.Player);
    Player player = new ComputerPlayer(PlayerType.BLACK);
    blackCell.setCellToPlayer(player);

    HashMap<GamePosition, Cell> boardToAdd = new HashMap<GamePosition, Cell>();
    boardToAdd.put(new Position2D(3, 3), blackCell);

    BasicSquareReversiModelMock model =
            new BasicSquareReversiModelMock(4, boardToAdd,
                    new ComputerPlayer(PlayerType.WHITE));

    ReversiStrategy strat1 = new CaptureCellsInCorner();
    ReversiStrategy strat2 = new CaptureMostPieces();
    ReversiStrategy strat = new TryTwoStrategies(strat1, strat2);

    GamePosition stratPosn = strat.chooseMove(model, PlayerType.WHITE);

    Assert.assertEquals(model.getLog(),
            "getScoreForMove(X: 1, Y: 0)\n" +
                    "getScoreForMove(X: 0, Y: 1)\n" +
                    "getScoreForMove(X: 3, Y: 2)\n" +
                    "getScoreForMove(X: 2, Y: 3)\n" +
                    "getScoreForMove(X: 1, Y: 0)\n" +
                    "getScoreForMove(X: 0, Y: 1)\n" +
                    "getScoreForMove(X: 3, Y: 2)\n" +
                    "getScoreForMove(X: 2, Y: 3)\n");
    Assert.assertEquals(stratPosn, new Position2D(0, 1));
  }

  // Test that minimizeNextOpponentMove works as intended when there is one
  // move
  // that will minimize the next opponent move
  @Test
  public void testMinimizeNextOpponentMoveOneMoveStartGame() {

    Cell blackCell = new GameCell(CellType.Player);
    Player player = new ComputerPlayer(PlayerType.BLACK);
    blackCell.setCellToPlayer(player);

    HashMap<PositionAxial, Cell> boardToAdd = new HashMap<PositionAxial, Cell>();
    boardToAdd.put(new PositionAxial(2, -2, 0), blackCell);

    BasicReversiModelMockTranscript model =
            new BasicReversiModelMockTranscript(7, boardToAdd,
                    new ComputerPlayer(PlayerType.WHITE));

    ReversiStrategy strat = new MinimizeNextOpponentMove();

    GamePosition stratPosn = strat.chooseMove(model, PlayerType.WHITE);

    Assert.assertEquals(model.getLog(),

            "getScoreForMove(Q: 3, R: -3, S: 0)" + "\n"
                    + "getScoreForMove(Q: 1, R: -2, S: 1)" + "\n"
                    + "getScoreForMove(Q: -2, R: 1, S: 1)" + "\n"
                    + "getScoreForMove(Q: 1, R: 1, S: -2)"
                    + "\n" + "getScoreForMove(Q: -1, R: -1, S: 2)"
                    + "\n"
                    + "getScoreForMove(Q: 2, R: -1, S: -1)"
                    + "\n"
                    + "getScoreForMove(Q: -1, R: 2, S: -1)\n");
    Assert.assertEquals(stratPosn, new PositionAxial(-1, 2, -1));

  }

  // Test that minimizeNextOpponentMove works as intended when
  // it should pass the turn for hexagonal reversi. 
  @Test
  public void testMinimizeNextOpponentMoveNoMoveStartGame() {

    Cell blackCell = new GameCell(CellType.Player);
    Player player = new ComputerPlayer(PlayerType.BLACK);
    blackCell.setCellToPlayer(player);

    HashMap<PositionAxial, Cell> boardToAdd = new HashMap<PositionAxial, Cell>();
    boardToAdd.put(new PositionAxial(1, -2, 1), blackCell);
    boardToAdd.put(new PositionAxial(-1, -1, 2), blackCell);
    boardToAdd.put(new PositionAxial(-2, 1, 1), blackCell);
    boardToAdd.put(new PositionAxial(-1, 2, -1), blackCell);
    boardToAdd.put(new PositionAxial(1, 1, -2), blackCell);
    boardToAdd.put(new PositionAxial(2, -1, -1), blackCell);

    BasicReversiModelMockTranscript model =
            new BasicReversiModelMockTranscript(5, boardToAdd,
                    new ComputerPlayer(PlayerType.WHITE));

    ReversiStrategy strat = new MinimizeNextOpponentMove();

    GamePosition stratPosn = strat.chooseMove(model, PlayerType.WHITE);

    Assert.assertEquals(model.getLog(), "");
    Assert.assertEquals(stratPosn, new PositionAxial(19, 19, 19));

  }

  // Test that minimizeNextOpponentMove works as intended when
  // it should pass the turn for square reversi.
  @Test
  public void testMinimizeNextOpponentMoveNoMoveStartGameSquare() {

    Cell blackCell = new GameCell(CellType.Player);
    Player player = new ComputerPlayer(PlayerType.BLACK);
    blackCell.setCellToPlayer(player);

    HashMap<GamePosition, Cell> boardToAdd = new HashMap<GamePosition, Cell>();
    boardToAdd.put(new Position2D(0, 0), blackCell);
    boardToAdd.put(new Position2D(0, 1), blackCell);
    boardToAdd.put(new Position2D(1, 0), blackCell);
    boardToAdd.put(new Position2D(1, 1), blackCell);

    BasicSquareReversiModelMock model =
            new BasicSquareReversiModelMock(2, boardToAdd,
                    new ComputerPlayer(PlayerType.WHITE));

    ReversiStrategy strat = new MinimizeNextOpponentMove();

    GamePosition stratPosn = strat.chooseMove(model, PlayerType.WHITE);

    Assert.assertEquals(model.getLog(), "");
    Assert.assertEquals(stratPosn, new PositionAxial(4, 4, 4));

  }

  // Tests that minimizeNextOpponentMove works as intended when there are
  // multiple moves
  // at the start of the game for hexagonal reversi.
  @Test
  public void testMinimizeNextOpponentMoveMultipleMovesStartGame() {

    BasicReversiModelMockTranscript model = new BasicReversiModelMockTranscript();

    ReversiStrategy strat = new MinimizeNextOpponentMove();

    GamePosition stratPosn = strat.chooseMove(model, PlayerType.BLACK);

    Assert.assertEquals(model.getLog(),
            "getScoreForMove(Q: 1, R: -2, S: 1)\n"
                    + "getScoreForMove(Q: -2, R: 1, S: 1)\n"
                    + "getScoreForMove(Q: 1, R: 1, S: -2)\n"
                    + "getScoreForMove(Q: -1, R: -1, S: 2)\n"
                    + "getScoreForMove(Q: 2, R: -1, S: -1)\n"
                    + "getScoreForMove(Q: -1, R: 2, S: -1)\n");
    Assert.assertEquals(stratPosn, new PositionAxial(-1, 2, -1));
  }

}

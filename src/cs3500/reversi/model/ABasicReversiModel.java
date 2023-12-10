package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cs3500.reversi.controller.ComputerPlayer;
import cs3500.reversi.controller.ModelStatusFeatures;
import cs3500.reversi.controller.Player;
import cs3500.reversi.controller.PlayerType;

public abstract class ABasicReversiModel implements ReversiModel, ReadOnlyReversiModel {
  // the game board, represented as a HashMap of positions to cells
  protected final HashMap<GamePosition, Cell> board;
  // the players in the game
  protected final Player playerBlack;
  protected final Player playerWhite;
  // the width of the game board
  // INVARIENT: Width must be odd and at least three. Enforced by constructor at
  // construction of BasicReversiModel.
  protected final int width;
  // the turn of the current player
  // INVARIENT: currentPlayer must be either playerBlack or playerWhite. Enforced
  // by changeTurns(),
  // which is the only method to change currentPlayer. Also enforced by
  // constructor at construction.
  protected Player currentPlayer;
  // the number of consecutive passed turns.
  // INVARIENT: consectivePassedTurns must be a non-negative integer. Enforced by
  // passTurn(), which only adds to the consectivePassedTurns counter. Also
  // enforced by constructor at construction by instantiating
  // consectivePassedTurns to 0.
  protected int consectivePassedTurns;
  // whether or not the game has started
  protected boolean gameStarted;

  protected final List<ModelStatusFeatures> controllers;

  /**
   * Constructs a new BasicReversiModel with the specified width. The game can
   * only be played on a regular grid of cells, so the width needs to be an odd
   * number. For a playable game, the width needs to be at least three.
   *
   * @param width The width of the game board.
   * @throws IllegalArgumentException If the provided width is not an odd number
   *                                  or is less than three.
   */
  public ABasicReversiModel(int width) {
    this.isWidthCorrect(width);

    this.board = new HashMap<>();
    this.playerBlack = new ComputerPlayer(PlayerType.BLACK);
    this.playerWhite = new ComputerPlayer(PlayerType.WHITE);
    this.width = width;
    this.currentPlayer = this.playerBlack;
    this.consectivePassedTurns = 0;
    this.gameStarted = false;
    this.controllers = new ArrayList<>();
  }

  /**
   * Constructs a new BasicReversiModel with the specified width. The game can
   * only be played on a regular grid of cells, so the width needs to be an odd
   * number. For a playable game, the width needs to be at least three. Players
   * are taken as parameters for testing purposes.
   *
   * @param width       The width of the game board.
   * @param playerBlack The black player.
   * @param playerWhite The white player.
   * @throws IllegalArgumentException If the provided width is not an odd number
   *                                  or is less than three.
   */
  public ABasicReversiModel(int width, Player playerBlack, Player playerWhite) {
    this.isWidthCorrect(width);

    this.board = new HashMap<>();
    this.playerBlack = playerBlack;
    this.playerWhite = playerWhite;
    this.width = width;
    this.currentPlayer = this.playerBlack;
    this.consectivePassedTurns = 0;
    this.gameStarted = false;
    this.controllers = new ArrayList<>();
  }

  /**
   * Constructs a new BasicReversiModel with the specified width and a hashmap of
   * posns to cells.
   * The game can only be played on a regular grid of cells, so the width needs to
   * be an odd
   * number. For a playable game, the width needs to be at least three. The
   * purpose of the hashmap
   * is to allow for the model to be constructed with a board that is in the
   * middle of a game.
   *
   * @param width The width of the game board.
   * @param board The hashmap of posns to cells that represents the board.
   * @throws IllegalArgumentException If the provided width is not an odd number
   *                                  or is less than three.
   */
  public ABasicReversiModel(int width, HashMap<GamePosition, Cell> board, Player currentPlayer) {
    this.isWidthCorrect(width);

    this.board = new HashMap<>();
    this.playerBlack = new ComputerPlayer(PlayerType.BLACK);
    this.playerWhite = new ComputerPlayer(PlayerType.WHITE);
    this.width = width;
    this.currentPlayer = currentPlayer;
    this.consectivePassedTurns = 0;
    this.gameStarted = false;
    this.controllers = new ArrayList<>();
  }

  /**
   * Constructs a new BasicReversiModel with a default width of 11.
   */
  public ABasicReversiModel() {

    this.board = new HashMap<>();
    this.playerBlack = new ComputerPlayer(PlayerType.BLACK);
    this.playerWhite = new ComputerPlayer(PlayerType.WHITE);
    this.width = 11;
    this.currentPlayer = this.playerBlack;
    this.consectivePassedTurns = 0;
    this.gameStarted = false;
    this.controllers = new ArrayList<>();
  }

  /**
   * Gets the current turn of the game
   * 
   * @return the current turn of the game as a String
   */
  protected String getCurrentTurn() {
    return this.currentPlayer.toString();
  }

  @Override
  public String getCurrentWinner() {
    // returns the current winning player, white player if tied because black goes
    // first
    if (this.getCurrentScore(PlayerType.BLACK) > this.getCurrentScore(PlayerType.WHITE)) {
      return this.playerBlack.toString();
    } else if (this.getCurrentScore(PlayerType.BLACK) == this.getCurrentScore(PlayerType.WHITE)) {
      return "Tie";
    } else {
      return this.playerWhite.toString();
    }
  }

  @Override
  public boolean hasGameStarted() {
    return this.gameStarted;
  }

  // helper that handles if the given position does not exist in this game.
  protected void doesPosnExist(GamePosition posn) {
    if (!board.containsKey(posn)) {
      throw new IllegalArgumentException("Nonexistant position in this game");
    }
  }

  // helper for making sure it is the given player's turn
  protected void currentTurnCorrect(Player player) {
    if (!player.equals(this.currentPlayer)) {
      throw new IllegalStateException("Not current player's turn");
    }
  }

  // helper for making sure game is started
  protected void gameStartedHelper() {
    if (!gameStarted) {
      throw new IllegalStateException("Game not started yet");
    }
  }

  @Override
  public abstract int getScoreForMove(GamePosition posn);

  @Override
  public abstract int getScoreForMovePlayer(GamePosition posn, String player);

  @Override
  public int getCurrentScore(PlayerType playerType) {
    int score = 0;

    // iterate over the board and count the number of cells owned by the given
    for (Cell cell : this.board.values()) {
      if (cell.getCellType().equals(CellType.Player)
          && cell.getCellOwner().equals(new ComputerPlayer(playerType).toString())) {
        score += 1;
      }
    }

    return score;
  }

  @Override
  public int getBoardSize() {
    return this.board.size();
  }

  @Override
  public Cell getCellAt(GamePosition posn) {
    doesPosnExist(posn);

    Cell cellAtPosn = this.board.get(posn);
    Cell cellToReturn = new GameCell(cellAtPosn.getCellType());

    // if the cell at the given position is a player cell, set the cell to the
    // proper player
    if (cellAtPosn.sameCellType(CellType.Player)) {
      if (cellAtPosn.getCellOwner().equals("X")) {
        cellToReturn.setCellToPlayer(new ComputerPlayer(PlayerType.BLACK));
      } else {
        cellToReturn.setCellToPlayer(new ComputerPlayer(PlayerType.WHITE));
      }
    }

    return cellToReturn;
  }

  @Override
  public int getNumRows() {
    return this.width;
  }

  protected void changeTurns() {
    // change the active player's turn. Enforces the invarient by
    // only changing currentPlayer to playerBlack or playerWhite.
    if (this.currentPlayer.equals(this.playerBlack)) {
      this.currentPlayer = this.playerWhite;
    } else {
      this.currentPlayer = this.playerBlack;
    }
  }

  @Override
  public void passTurn(Player player) {

    gameStartedHelper();
    currentTurnCorrect(player);

    // add to consectivePassedTurns counter. Enforced invarient by only adding a
    // positive number.
    this.consectivePassedTurns += 1;

    // change the active player's turn.
    this.changeTurns();

    for (ModelStatusFeatures f : this.controllers) {
      f.notifyToRefresh(this.getCurrentTurn());
    }
  }

  @Override
  public void addFeaturesListener(ModelStatusFeatures feature) {
    this.controllers.add(feature);
  }

  @Override
  public void startGame() {

    if (gameStarted) {
      throw new IllegalStateException("Game already started");
    }

    // set the game to has started
    this.gameStarted = true;

    // notify the controller to refresh the views
    for (ModelStatusFeatures f : this.controllers) {
      f.notifyToRefresh(this.getCurrentTurn());
    }
  }

  // helper for checking if appropriate width was defined depeneding 
  // on game type
  protected abstract void isWidthCorrect(int width);
}

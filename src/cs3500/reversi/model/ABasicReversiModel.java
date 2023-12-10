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

  // helper for making sure the width is correct
  protected abstract void isWidthCorrect(int width);

  @Override
  public int getScoreForMove(GamePosition posn) {
    // Get the list of valid positions to add a piece to on this move.
    List<GamePosition> validTiles = this.isValidMoveForPlayer(posn, this.currentPlayer);

    return validTiles.size();
  }

  /**
   * Checks for cells that allow for valid moves for a player.
   *
   * @param givenPosn  The position to check for a valid move.
   * @param playerTurn The player whose turn it currently is.
   * @return A list of positions that represent valid moves, or an empty list if
   *         the move is invalid.
   */
  protected List<GamePosition> isValidMoveForPlayer(GamePosition givenPosn, Player playerTurn) {
    Player otherPlayer = playerTurn.getOppositePlayer();
    ArrayList<GamePosition> allCellsBetween = new ArrayList<>();
    List<GamePosition> surroundingCells = this.getSurroundingCells(givenPosn);

    // iterate over the surrounding positions adjacent to the given position
    for (GamePosition posn : surroundingCells) {
      // see if the cell at the surrounding position is owned by the other player
      // and if there is a valid line of cells after it, all cells in this line are
      // returned
      if (this.getCellAt(posn).getCellType().equals(CellType.Player)
              && this.getCellAt(posn).getCellOwner().equals(otherPlayer.toString())) {
        allCellsBetween.addAll(this.checkValidLineMade(givenPosn, posn, playerTurn));
      }
    }

    return allCellsBetween;
  }

  @Override
  public int getScoreForMovePlayer(GamePosition posn, String player) {
    List<GamePosition> validTiles;
    if (player.equals("X")) {
      validTiles = this.isValidMoveForPlayer(posn, this.playerBlack);
    } else {
      validTiles = this.isValidMoveForPlayer(posn, this.playerWhite);
    }

    // Get the list of valid positions to add a piece to on this move.
    return validTiles.size();
  }

  protected abstract List<GamePosition> checkValidLineMade(GamePosition givenPosn, GamePosition posn,
                                                Player playerTurn);

  protected List<GamePosition> getSurroundingCells(GamePosition givenPosn) {
    ArrayList<GamePosition> surroundingCells = new ArrayList<>();

    for (GamePosition posn : this.board.keySet()) {
      if (posn.isNextTo(givenPosn)) {
        surroundingCells.add(posn);
      }
    }

    return surroundingCells;
  }

  // helper for changing the ownership of cells between the given positions
  protected void changeAllCellsBetween(List<GamePosition> posnBetween) {
    for (GamePosition posn : posnBetween) {
      this.board.get(posn).setCellToPlayer(this.currentPlayer);
    }
  }

  /**
   * Checks if the Reversi game is over.
   *
   * @return true if the game is over, false otherwise.
   */
  @Override
  public boolean isGameOver() {

    // if two or more passes made, game should end
    if (this.consectivePassedTurns >= 2) {
      return true;
    }

    // for every position on the board, check if the current player has any valid
    // moves
    for (GamePosition posn : this.board.keySet()) {
      if (this.getCellAt(posn).sameCellType(CellType.Empty)
              && (!this.isValidMoveForPlayer(posn, this.playerBlack).isEmpty()
              || !this.isValidMoveForPlayer(posn, this.playerWhite).isEmpty())) {
        return false;
      }
    }

    return true;
  }

  @Override
  public boolean doesCurrentPlayerHaveValidMovesPosn(GamePosition posn, Player playerTurn) {
    if (this.getCellAt(posn).sameCellType(CellType.Empty)) {
      return !(this.isValidMoveForPlayer(posn, playerTurn).isEmpty());
    } else {
      return false;
    }
  }

  @Override
  public boolean doesCurrentPlayerHaveValidMoves() {
    // for every position on the board, check if the current player has any valid
    // moves
    for (GamePosition posn : this.board.keySet()) {
      if (this.getCellAt(posn).sameCellType(CellType.Empty)
              && !(this.isValidMoveForPlayer(posn, this.currentPlayer).isEmpty())) {
        // if the current player has a valid move, return true
        return true;
      }
    }

    // if the current player does not have a valid move, return false
    return false;
  }

  @Override
  public void addPieceToCoordinates(GamePosition posn, Player player) {

    currentTurnCorrect(player);
    gameStartedHelper();
    doesPosnExist(posn);

    // Get the list of valid positions to add a piece to on this move.
    List<GamePosition> validTiles = this.isValidMoveForPlayer(posn, player);

    if (!validTiles.isEmpty()) {

      // create a player cell to represent the player's piece.
      Cell playerCell = new GameCell(CellType.Player);

      // set the owner of the player cell to the current player.
      playerCell.setCellToPlayer(this.currentPlayer);

      // place the player cell on the game board at the specified position.
      this.board.put(posn, playerCell);

      // change the ownership of cells between the specified positions.
      this.changeAllCellsBetween(validTiles);

      // change the active player's turn and reset the consecutive passed turns
      // counter.
      this.changeTurns();

      // reset consectivePassedTurns counter. Enforced invarient by being non-negative
      // integer.
      this.consectivePassedTurns = 0;

      for (ModelStatusFeatures f : this.controllers) {
        f.notifyToRefresh(this.getCurrentTurn());
      }
    } else {
      throw new IllegalStateException("Move cannot be made");
    }
  }
}

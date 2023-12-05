package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cs3500.reversi.controller.ModelStatusFeatures;
import cs3500.reversi.controller.ComputerPlayer;
import cs3500.reversi.controller.Player;
import cs3500.reversi.controller.PlayerType;

/**
 * Represents the model of a Reversi game. Manages the game board, players, and
 * game logic. In our game, the grid is made up of hexagons. The game is played
 * on a regular grid of cells.
 */
public class BasicReversiModel implements ReversiModel {

  // the game board, represented as a HashMap of positions to cells
  private final HashMap<PositionAxial, Cell> board;
  // the players in the game
  private final Player playerBlack;
  private final Player playerWhite;
  // the width of the game board
  // INVARIENT: Width must be odd and at least three. Enforced by constructor at
  // construction of BasicReversiModel.
  private final int width;
  // the turn of the current player
  // INVARIENT: currentPlayer must be either playerBlack or playerWhite. Enforced
  // by changeTurns(),
  // which is the only method to change currentPlayer. Also enforced by
  // constructor at construction.
  private Player currentPlayer;
  // the number of consecutive passed turns.
  // INVARIENT: consectivePassedTurns must be a non-negative integer. Enforced by
  // passTurn(), which only adds to the consectivePassedTurns counter. Also
  // enforced by constructor at construction by instantiating
  // consectivePassedTurns to 0.
  private int consectivePassedTurns;
  // whether or not the game has started
  private boolean gameStarted;

  private final List<ModelStatusFeatures> controllers;

  /**
   * Constructs a new BasicReversiModel with the specified width. The game can
   * only be played on a regular grid of cells, so the width needs to be an odd
   * number. For a playable game, the width needs to be at least three.
   *
   * @param width The width of the game board.
   * @throws IllegalArgumentException If the provided width is not an odd number
   *                                  or is less than three.
   */
  public BasicReversiModel(int width) {
    // Enforced invarient by checking if width is odd and at least three.
    if (width < 3 || width % 2 == 0) {
      throw new IllegalArgumentException("Width must be odd and at least three.");
    }
    this.board = new HashMap<>();
    this.playerBlack = new ComputerPlayer(PlayerType.BLACK);
    this.playerWhite = new ComputerPlayer(PlayerType.WHITE);
    this.width = width;
    this.currentPlayer = this.playerBlack;
    this.consectivePassedTurns = 0;
    this.gameStarted = false;
    this.controllers = new ArrayList<>();

    // initialize the board
    this.initializeBoard();
    // add the starting pieces
    this.addStartingPieces();
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
  public BasicReversiModel(int width, Player playerBlack, Player playerWhite) {
    // Enforced invarient by checking if width is odd and at least three.
    if (width < 3 || width % 2 == 0) {
      throw new IllegalArgumentException("Width must be odd and at least three.");
    }
    this.board = new HashMap<>();
    this.playerBlack = playerBlack;
    this.playerWhite = playerWhite;
    this.width = width;
    this.currentPlayer = this.playerBlack;
    this.consectivePassedTurns = 0;
    this.gameStarted = false;
    this.controllers = new ArrayList<>();

    // initialize the board
    this.initializeBoard();
    // add the starting pieces
    this.addStartingPieces();
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
  public BasicReversiModel(int width, HashMap<PositionAxial, Cell> board, Player currentPlayer) {

    // Enforced invarient by checking if width is odd and at least three.
    if (width < 3 || width % 2 == 0) {
      throw new IllegalArgumentException("Width must be odd and at least three.");
    }

    this.board = new HashMap<>();
    this.playerBlack = new ComputerPlayer(PlayerType.BLACK);
    this.playerWhite = new ComputerPlayer(PlayerType.WHITE);
    this.width = width;
    this.currentPlayer = currentPlayer;
    this.consectivePassedTurns = 0;
    this.gameStarted = false;
    this.controllers = new ArrayList<>();

    // initialize the board
    this.initializeBoard();
    // add the starting pieces
    this.addStartingPieces();

    for (PositionAxial posn : board.keySet()) {
      this.board.put(posn, board.get(posn));
    }
  }

  /**
   * Constructs a new BasicReversiModel with a default width of 11.
   */
  public BasicReversiModel() {
    this.board = new HashMap<>();
    this.playerBlack = new ComputerPlayer(PlayerType.BLACK);
    this.playerWhite = new ComputerPlayer(PlayerType.WHITE);
    this.width = 11;
    this.currentPlayer = this.playerBlack;
    this.consectivePassedTurns = 0;
    this.gameStarted = false;
    this.controllers = new ArrayList<>();

    // initialize the board
    this.initializeBoard();
    // add the starting pieces
    this.addStartingPieces();
  }

  /**
   * Starts the game.
   *
   * @throws IllegalStateException if the game has already started.
   */
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

  @Override
  public void addFeaturesListener(ModelStatusFeatures feature) {
    this.controllers.add(feature);
  }

  /**
   * Initializes the game board by creating empty cells for all positions on the
   * board.
   */
  private void initializeBoard() {

    // calculate the middle row of the game board
    int middleY = (this.width - 1) / 2;

    // initialize starting q and s coordinates for the current row
    int currentRowStartingQ = 0;
    int currentRowStartingS = middleY;

    // initialize the r coordinate for the current row
    int currentR = -middleY;

    for (int rowsMade = 0; rowsMade < width; rowsMade += 1) {
      // initialize q coordinate for current position
      int currentQ = currentRowStartingQ;

      for (int currentS = currentRowStartingS; currentS >= currentRowStartingQ; currentS -= 1) {
        // create empty cell and add it to the board at the current position
        this.board.put(new PositionAxial(currentQ, currentR, currentS),
            new GameCell(CellType.Empty));
        // move to the next q coordinate in the row
        currentQ += 1;
      }

      // adjust the starting q or s coordinate for the next row based on the row
      // index.
      // decrease q for the upper part of the board
      // decrease s for the lower part of the board
      if (rowsMade < middleY) {
        currentRowStartingQ -= 1;
      } else {
        currentRowStartingS -= 1;
      }

      // move to the next r coordinate for the next row
      currentR += 1;
    }
  }

  /**
   * Adds the starting pieces to the game board, setting the initial player
   * positions.
   */
  private void addStartingPieces() {
    Cell whiteCell = new GameCell(CellType.Player);
    whiteCell.setCellToPlayer(new ComputerPlayer(PlayerType.WHITE));

    Cell blackCell = new GameCell(CellType.Player);
    blackCell.setCellToPlayer(new ComputerPlayer(PlayerType.BLACK));

    // setting initial player positions
    this.initializeCell(-1, 0, 1, PlayerType.WHITE);
    this.initializeCell(0, -1, 1, PlayerType.BLACK);
    this.initializeCell(1, -1, 0, PlayerType.WHITE);
    this.initializeCell(1, 0, -1, PlayerType.BLACK);
    this.initializeCell(0, 1, -1, PlayerType.WHITE);
    this.initializeCell(-1, 1, 0, PlayerType.BLACK);
  }

  // helper for initializing a cell to be occupied by a player
  private void initializeCell(int q, int r, int s, PlayerType type) {
    this.board.put(new PositionAxial(q, r, s), new GameCell(CellType.Player));
    this.board.get(new PositionAxial(q, r, s)).setCellToPlayer(new ComputerPlayer(type));
  }

  /**
   * Creates a deep copy of the board of this Reversi game.
   *
   * @return A deep copy of the board of this Reversi game.
   */
  @Override
  public HashMap<GamePosition, Cell> getBoardCopy() {
    HashMap<GamePosition, Cell> boardCopy = new HashMap<>();

    // iterate over the board and create a deep copy of each cell
    for (PositionAxial posn : this.board.keySet()) {
      PositionAxial posnCopy = new PositionAxial(posn.getQ(), posn.getR(), posn.getS());
      Cell cell = this.board.get(posn);
      Cell cellCopy = new GameCell(cell.getCellType());

      if (cell.sameCellType(CellType.Player)) {
        if (cell.getCellOwner().equals("X")) {
          cellCopy.setCellToPlayer(new ComputerPlayer(PlayerType.BLACK));
        } else {
          cellCopy.setCellToPlayer(new ComputerPlayer(PlayerType.WHITE));
        }
      }

      boardCopy.put(posnCopy, cellCopy);
    }

    return boardCopy;
  }

  /**
   * Adds a player's piece to the specified position on the board and updates the
   * game state.
   *
   * @param posn   The position to add the player's piece.
   * @param player The player to add the piece for.
   * @throws IllegalStateException    if the move cannot be made.
   * @throws IllegalStateException    if the game has not started.
   * @throws IllegalStateException    if the player given is not currently up for
   *                                  a move.
   * @throws IllegalArgumentException if the position does not exist in this game.
   */
  @Override
  public void addPieceToCoordinates(GamePosition posn, Player player) {

    currentTurnCorrect(player);
    gameStartedHelper();
    doesPosnExist(posn);

    // Get the list of valid positions to add a piece to on this move.
    List<PositionAxial> validTiles = this.isValidMoveForPlayer((PositionAxial) posn, player);

    if (!validTiles.isEmpty()) {

      // create a player cell to represent the player's piece.
      Cell playerCell = new GameCell(CellType.Player);

      // set the owner of the player cell to the current player.
      playerCell.setCellToPlayer(this.currentPlayer);

      // place the player cell on the game board at the specified position.
      this.board.put((PositionAxial) posn, playerCell);

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

  // return the player whose turn it currently is
  public String getCurrentTurn() {
    return this.currentPlayer.toString();
  }

  /**
   * Checks for cells that allow for valid moves for a player.
   *
   * @param givenPosn  The position to check for a valid move.
   * @param playerTurn The player whose turn it currently is.
   * @return A list of positions that represent valid moves, or an empty list if
   *         the move is invalid.
   */
  private List<PositionAxial> isValidMoveForPlayer(PositionAxial givenPosn, Player playerTurn) {
    Player otherPlayer = playerTurn.getOppositePlayer();
    ArrayList<PositionAxial> allCellsBetween = new ArrayList<>();
    List<PositionAxial> surroundingCells = this.getSurroundingCells(givenPosn);

    // iterate over the surrounding positions adjacent to the given position
    for (PositionAxial posn : surroundingCells) {

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

  /**
   * Checks and retrieves a list of positions between the given position and
   * another position that form a valid line made by the player.
   *
   * @param givenPosn  The starting position.
   * @param posn       The ending position.
   * @param playerTurn The player whose turn it currently is.
   * @return A list of positions forming a valid line between the given positions,
   *         or an empty list if no valid line exists.
   */
  private List<PositionAxial> checkValidLineMade(PositionAxial givenPosn, PositionAxial posn,
      Player playerTurn) {
    ArrayList<PositionAxial> cellsBetween = new ArrayList<>();

    // check if given and ending positions share Q coordinates
    if (givenPosn.getQ() == posn.getQ()) {

      if (givenPosn.getR() < posn.getR()) {
        // determine the range of R and S coordinates between the two positions.
        int startingPositionR = Math.min(givenPosn.getR(), posn.getR());
        int startingPositionS = Math.max(givenPosn.getS(), posn.getS());
        this.goDownLine(startingPositionR, startingPositionS, cellsBetween, "q",
            givenPosn.getQ(), true, playerTurn);
      } else {
        int startingPositionR = Math.max(givenPosn.getR(), posn.getR());
        int startingPositionS = Math.min(givenPosn.getS(), posn.getS());
        this.goDownLine(startingPositionS, startingPositionR, cellsBetween, "q",
            givenPosn.getQ(), false, playerTurn);
      }
    }

    // check if given and ending positions share R coordinates
    if (givenPosn.getR() == posn.getR()) {
      if (givenPosn.getQ() < posn.getQ()) {
        // determine the range of Q and S coordinates between the two positions.
        int startingPositionQ = Math.min(givenPosn.getQ(), posn.getQ());
        int startingPositionS = Math.max(givenPosn.getS(), posn.getS());
        this.goDownLine(startingPositionQ, startingPositionS, cellsBetween, "r",
            givenPosn.getR(), true, playerTurn);
      } else {
        int startingPositionQ = Math.max(givenPosn.getQ(), posn.getQ());
        int startingPositionS = Math.min(givenPosn.getS(), posn.getS());
        this.goDownLine(startingPositionS, startingPositionQ, cellsBetween, "r",
            givenPosn.getR(), false, playerTurn);
      }
    }

    // check if given and ending positions share S coordinates
    if (givenPosn.getS() == posn.getS()) {
      if (givenPosn.getQ() < posn.getQ()) {
        // determine the range of Q and R coordinates between the two positions.
        int startingPositionQ = Math.min(givenPosn.getQ(), posn.getQ());
        int startingPositionR = Math.max(givenPosn.getR(), posn.getR());
        this.goDownLine(startingPositionQ, startingPositionR, cellsBetween, "s",
            givenPosn.getS(), true, playerTurn);
      } else {
        int startingPositionQ = Math.max(givenPosn.getQ(), posn.getQ());
        int startingPositionR = Math.min(givenPosn.getR(), posn.getR());
        this.goDownLine(startingPositionR, startingPositionQ, cellsBetween, "s",
            givenPosn.getS(), false, playerTurn);
      }
    }

    return cellsBetween;
  }

  // traverses positions on the game board based on the direction and row type
  // and adds it to the list if it will be converted by the current player with
  // a proper move
  private void goDownLine(int incrementStartingPostion, int decrementStartingPosition,
      ArrayList<PositionAxial> cellsBetween, String row, int constantPosition,
      boolean foward, Player playerTurn) {
    // iterate through the range of coordinates and check for valid positions.
    while (true) {
      incrementStartingPostion += 1;
      decrementStartingPosition -= 1;

      // calculate current position based on direction
      PositionAxial currentPosition = this.calculateStartingPosition(foward, row,
          incrementStartingPostion, decrementStartingPosition, constantPosition);

      // check if the position exists on the board and is owned by the other player.
      // if so, clear the list
      // if not, add the current position to the list
      if (!this.board.containsKey(currentPosition)) {
        cellsBetween.clear();
        break;
      }

      // if cell is owned by player with next turn, add it to list
      if (!this.getCellAt(currentPosition).getCellOwner()
          .equals(playerTurn.getOppositePlayer().toString())) {
        if (this.getCellAt(currentPosition).getCellOwner()
            .equals(playerTurn.toString())) {
          break;
        } else {
          cellsBetween.clear();
          break;
        }
      } else {
        cellsBetween.add(currentPosition);
      }
    }
  }

  // return the starting position for the line being made, depending on direction
  // given
  private PositionAxial calculateStartingPosition(boolean foward, String row,
      int incrementStartingPostion,
      int decrementStartingPosition,
      int constantPosition) {
    // calculate current position based on forward direction
    if (foward) {
      if (row.toLowerCase().equals("s")) {
        return new PositionAxial(incrementStartingPostion, decrementStartingPosition,
            constantPosition);
      } else if (row.toLowerCase().equals("r")) {
        return new PositionAxial(incrementStartingPostion, constantPosition,
            decrementStartingPosition);
      } else if (row.toLowerCase().equals("q")) {
        return new PositionAxial(constantPosition, incrementStartingPostion,
            decrementStartingPosition);
      } else {
        throw new IllegalArgumentException("Invalid row given");
      }
    }
    // calculate current position based on backward direction
    else {
      if (row.toLowerCase().equals("s")) {
        return new PositionAxial(decrementStartingPosition, incrementStartingPostion,
            constantPosition);
      } else if (row.toLowerCase().equals("r")) {
        return new PositionAxial(decrementStartingPosition, constantPosition,
            incrementStartingPostion);
      } else if (row.toLowerCase().equals("q")) {
        return new PositionAxial(constantPosition, decrementStartingPosition,
            incrementStartingPostion);
      } else {
        throw new IllegalArgumentException("Invalid row given");
      }
    }
  }

  /**
   * Retrieves a list of surrounding positions adjacent to the given position.
   *
   * @param givenPosn The position to find surrounding positions for.
   * @return A list of surrounding positions adjacent to the given position.
   */
  private List<PositionAxial> getSurroundingCells(PositionAxial givenPosn) {
    ArrayList<PositionAxial> surroundingCells = new ArrayList<>();

    for (PositionAxial posn : this.board.keySet()) {
      if (posn.isNextTo(givenPosn)) {
        surroundingCells.add(posn);

      }
    }

    return surroundingCells;
  }

  /**
   * Changes the ownership of all cells between the specified positions to the
   * current player.
   *
   * @param posnBetween A list of positions between which cell ownership should be
   *                    changed.
   */
  private void changeAllCellsBetween(List<PositionAxial> posnBetween) {

    for (PositionAxial posn : posnBetween) {
      this.board.get(posn).setCellToPlayer(this.currentPlayer);
    }
  }

  /**
   * Passes the turn to the next player.
   *
   * @param player - the player to pass the turn for
   * 
   * @throws IllegalStateException if the game has not started or if it is not the
   *                               player's turn.
   */
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

  /**
   * Changes the active player's turn.
   */
  private void changeTurns() {
    // change the active player's turn. Enforces the invarient by
    // only changing currentPlayer to playerBlack or playerWhite.
    if (this.currentPlayer.equals(this.playerBlack)) {
      this.currentPlayer = this.playerWhite;
    } else {
      this.currentPlayer = this.playerBlack;
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
    for (PositionAxial posn : this.board.keySet()) {
      if (this.getCellAt(posn).sameCellType(CellType.Empty)
          && (!this.isValidMoveForPlayer(posn, this.playerBlack).isEmpty()
              || !this.isValidMoveForPlayer(posn, this.playerWhite).isEmpty())) {
        return false;
      }
    }

    return true;
  }

  /**
   * Checks if the current player has any valid moves.
   *
   * @return true if the current player has valid moves, false otherwise.
   */
  @Override
  public boolean doesCurrentPlayerHaveValidMoves() {
    // for every position on the board, check if the current player has any valid
    // moves
    for (PositionAxial posn : this.board.keySet()) {
      if (this.getCellAt(posn).sameCellType(CellType.Empty)
          && !(this.isValidMoveForPlayer(posn, this.currentPlayer).isEmpty())) {
        // if the current player has a valid move, return true
        return true;
      }
    }

    // if the current player does not have a valid move, return false
    return false;
  }

  /**
   * Checks if the current player has any valid moves at the given position.
   *
   * @return true if the current player has valid moves at the given position,
   *         false otherwise
   */
  @Override
  public boolean doesCurrentPlayerHaveValidMovesPosn(GamePosition posn, Player playerTurn) {
    if (this.getCellAt(posn).sameCellType(CellType.Empty)) {
      return !(this.isValidMoveForPlayer((PositionAxial) posn, playerTurn).isEmpty());
    } else {
      return false;
    }
  }

  /**
   * Gets the number of rows on the game board.
   *
   * @return The number of rows in the game board.
   */
  @Override
  public int getNumRows() {
    return this.width;
  }

  /**
   * Gets the cell at the specified coordinates on the game board using a
   * PositionAxial.
   *
   * @param posn The PositionAxial to get the cell at
   * @return The cell at the specified coordinates.
   * @throws IllegalArgumentException if the position does not exist in this game.
   */
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

  /**
   * Gets the size of the game board (number of cells).
   *
   * @return The size of the game board.
   */
  @Override
  public int getBoardSize() {
    return this.board.size();
  }

  /**
   * Gets the current score of the given PlayerType of this Reversi game. Score is
   * determined by the number of cells a player occupies.
   *
   * @return - The score of the given PlayerType in this game.
   */
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
  public int getScoreForMove(GamePosition posn) {
    // Get the list of valid positions to add a piece to on this move.
    List<PositionAxial> validTiles = this.isValidMoveForPlayer((PositionAxial) posn, this.currentPlayer);

    return validTiles.size();
  }

  // helper for making sure game is started
  private void gameStartedHelper() {
    if (!gameStarted) {
      throw new IllegalStateException("Game not started yet");
    }
  }

  // helper for making sure it is the given player's turn
  private void currentTurnCorrect(Player player) {
    if (!player.equals(this.currentPlayer)) {
      throw new IllegalStateException("Not current player's turn");
    }
  }

  // helper that handles if the given position does not exist in this game.
  private void doesPosnExist(GamePosition posn) {
    if (!board.containsKey(posn)) {
      throw new IllegalArgumentException("Nonexistant position in this game");
    }
  }

  @Override
  public boolean hasGameStarted() {
    return this.gameStarted;
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
}

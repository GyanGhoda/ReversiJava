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
public class BasicSquareReversiModel implements ReversiModel {

  // the game board, represented as a HashMap of positions to cells
  private final HashMap<GamePosition, Cell> board;
  // the players in the game
  private final Player playerBlack;
  private final Player playerWhite;
  // the width of the game board
  // INVARIENT: Width must be even and positive. Enforced by constructor at
  // construction of BasicSquareReversiModel.
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
   * Constructs a new BasicSquareReversiModel with the specified width. The game
   * can
   * only be played on a regular grid of cells, so the width needs to be an odd
   * number. For a playable game, the width needs to be at least three.
   *
   * @param width The width of the game board.
   * @throws IllegalArgumentException If the provided width is not an odd number
   *                                  or is less than three.
   */
  public BasicSquareReversiModel(int width) {
    // Enforced invarient by checking if width is even and positive.
    if (width < 0 || width % 2 != 0) {
      throw new IllegalArgumentException("Width must be even and positive.");
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
   * Constructs a new BasicSquareReversiModel with the specified width. The game
   * can
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
  public BasicSquareReversiModel(int width, Player playerBlack, Player playerWhite) {
    // Enforced invarient by checking if width is even and positive.
    if (width < 0 || width % 2 != 0) {
      throw new IllegalArgumentException("Width must be even and positive.");
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
   * Constructs a new BasicSquareReversiModel with the specified width and a
   * hashmap of
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
  public BasicSquareReversiModel(int width, HashMap<GamePosition, Cell> board, Player currentPlayer) {
    // Enforced invarient by checking if width is even and positive.
    if (width < 0 || width % 2 != 0) {
      throw new IllegalArgumentException("Width must be even and positive.");
    }

    // initialize the fields
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

    for (GamePosition posn : board.keySet()) {
      this.board.put(posn, board.get(posn));
    }
  }

  /**
   * Constructs a new BasicSquareReversiModel with a default width of 11.
   */
  public BasicSquareReversiModel() {
    this.board = new HashMap<>();
    this.playerBlack = new ComputerPlayer(PlayerType.BLACK);
    this.playerWhite = new ComputerPlayer(PlayerType.WHITE);
    this.width = 12;
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
    // iterate over the rows of the board
    for (int y = 0; y < this.width; y += 1) {
      // iterate over the columns of the board
      for (int x = 0; x < this.width; x += 1) {
        // create a new position for the current cell
        Position2D posn = new Position2D(x, y);

        // create an empty cell and add it to the board at the current poisiton
        this.board.put(posn, new GameCell(CellType.Empty));
      }
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
    this.initializeCell(this.width / 2 - 1, this.width / 2 - 1, PlayerType.BLACK);
    this.initializeCell(this.width / 2, this.width / 2 - 1, PlayerType.WHITE);
    this.initializeCell(this.width / 2, this.width / 2, PlayerType.BLACK);
    this.initializeCell(this.width / 2 - 1, this.width / 2, PlayerType.WHITE);
  }

  // helper for initializing a cell to be occupied by a player
  private void initializeCell(int x, int y, PlayerType type) {
    this.board.put(new Position2D(x, y), new GameCell(CellType.Player));
    if (type.equals(PlayerType.BLACK)) {
      this.board.get(new Position2D(x, y)).setCellToPlayer(this.playerBlack);
    } else if (type.equals(PlayerType.WHITE)) {
      this.board.get(new Position2D(x, y)).setCellToPlayer(this.playerWhite);
    }
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
    for (GamePosition posn : this.board.keySet()) {
      Position2D posn2D = (Position2D) posn;
      Position2D posnCopy = new Position2D(posn2D.getX(), posn2D.getY());
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

    // System.out.println("hy");

    currentTurnCorrect(player);
    gameStartedHelper();
    doesPosnExist(posn);

    // Get the list of valid positions to add a piece to on this move.
    List<Position2D> validTiles = this.isValidMoveForPlayer((Position2D) posn, player);

    if (!validTiles.isEmpty()) {

      // create a player cell to represent the player's piece.
      Cell playerCell = new GameCell(CellType.Player);

      // set the owner of the player cell to the current player.
      playerCell.setCellToPlayer(this.currentPlayer);

      // place the player cell on the game board at the specified position.
      this.board.put((Position2D) posn, playerCell);

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
  private List<Position2D> isValidMoveForPlayer(Position2D givenPosn, Player playerTurn) {
    Player otherPlayer = playerTurn.getOppositePlayer();
    ArrayList<Position2D> allCellsBetween = new ArrayList<>();
    List<Position2D> surroundingCells = this.getSurroundingCells(givenPosn);

    // iterate over the surrounding positions adjacent to the given position
    for (Position2D posn : surroundingCells) {
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
  private List<Position2D> checkValidLineMade(Position2D givenPosn, Position2D posn,
      Player playerTurn) {
    ArrayList<Position2D> cellsBetween = new ArrayList<>();

    // If the given and ending positions share X coordinates, check for a valid line
    if (givenPosn.getX() == posn.getX()) {
      // Go forwards along the possible line
      if (givenPosn.getY() > posn.getY()) {
        this.goDownLine(givenPosn.getY(), cellsBetween, "y", givenPosn.getX(), false, playerTurn);
      }
      // Go backwards along the possible line
      else {
        this.goDownLine(givenPosn.getY(), cellsBetween, "y", givenPosn.getX(), true, playerTurn);
      }
    }

    // If the given and ending positions share Y coordinates, check for a valid line
    if (givenPosn.getY() == posn.getY()) {
      // Go forwards along the possible line
      if (givenPosn.getX() > posn.getX()) {
        this.goDownLine(givenPosn.getX(), cellsBetween, "x", givenPosn.getY(), false, playerTurn);
      }
      // Go backwards along the possible line
      else {
        this.goDownLine(givenPosn.getX(), cellsBetween, "x", givenPosn.getY(), true, playerTurn);
      }
    }

    // Check for a valid line along the diagonal
    if (Math.abs(givenPosn.getX() - posn.getX()) == 1
        && Math.abs(givenPosn.getY() - posn.getY()) == 1) {
      if (givenPosn.getX() > posn.getX()) {
        if (givenPosn.getY() > posn.getY()) {
          this.goDownLineDiagonal(givenPosn.getX(), givenPosn.getY(), false, false, cellsBetween,
              playerTurn);
        } else {
          this.goDownLineDiagonal(givenPosn.getX(), givenPosn.getY(), false, true, cellsBetween,
              playerTurn);
        }
      } else {
        if (givenPosn.getY() > posn.getY()) {
          this.goDownLineDiagonal(givenPosn.getX(), givenPosn.getY(), true, false, cellsBetween,
              playerTurn);
        } else {
          this.goDownLineDiagonal(givenPosn.getX(), givenPosn.getY(), true, true, cellsBetween,
              playerTurn);
        }
      }
    }

    return cellsBetween;
  }

  // helper that traverses positions on the game board based on the direction and starting position
  private void goDownLineDiagonal(int startingXPosition, int startingYPosition, boolean incrementX, boolean incrementY,
      ArrayList<Position2D> cellsBetween, Player playerTurn) {
    while (true) {
      // x direction
      if (incrementX) {
        startingXPosition += 1;
      } else {
        startingXPosition -= 1;
      }

      // y direction
      if (incrementY) {
        startingYPosition += 1;
      } else {
        startingYPosition -= 1;
      }

      Position2D currentPosition = new Position2D(startingXPosition, startingYPosition);

      // check if the position exists on the board
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

  // traverses positions on the game board based on the direction and row type
  // and adds it to the list if it will be converted by the current player with
  // a proper move
  private void goDownLine(int incrementStartingPostion,
      ArrayList<Position2D> cellsBetween, String row, int constantPosition,
      boolean forward, Player playerTurn) {
    // iterate through the range of coordinates and check for valid positions.
    while (true) {
      if (forward) {
        incrementStartingPostion += 1;
      } else {
        incrementStartingPostion -= 1;
      }

      // calculate current position based on direction
      Position2D currentPosition = this.calculateStartingPosition(row,
          incrementStartingPostion, constantPosition);

      // check if the position exists on the board
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
  private Position2D calculateStartingPosition(String row,
      int incrementStartingPostion, int constantPosition) {
    // calculate current position based on row
    if (row.toLowerCase().equals("x")) {
      return new Position2D(incrementStartingPostion, constantPosition);
    } else if (row.toLowerCase().equals("y")) {
      return new Position2D(constantPosition, incrementStartingPostion);
    } else {
      throw new IllegalArgumentException("Invalid row given");
    }
  }

  /**
   * Retrieves a list of surrounding positions adjacent to the given position.
   *
   * @param givenPosn The position to find surrounding positions for.
   * @return A list of surrounding positions adjacent to the given position.
   */
  private List<Position2D> getSurroundingCells(Position2D givenPosn) {
    ArrayList<Position2D> surroundingCells = new ArrayList<>();

    for (GamePosition posn : this.board.keySet()) {
      Position2D posn2D = (Position2D) posn;

      if (posn.isNextTo(givenPosn)) {
        surroundingCells.add(posn2D);
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
  private void changeAllCellsBetween(List<Position2D> posnBetween) {

    for (Position2D posn : posnBetween) {
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
    for (GamePosition posn : this.board.keySet()) {
      Position2D posn2D = (Position2D) posn;

      if (this.getCellAt(posn).sameCellType(CellType.Empty)
          && (!this.isValidMoveForPlayer(posn2D, this.playerBlack).isEmpty()
              || !this.isValidMoveForPlayer(posn2D, this.playerWhite).isEmpty())) {
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
    for (GamePosition posn : this.board.keySet()) {
      Position2D posn2D = (Position2D) posn;
      if (this.getCellAt(posn).sameCellType(CellType.Empty)
          && !(this.isValidMoveForPlayer(posn2D, this.currentPlayer).isEmpty())) {
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
      return !(this.isValidMoveForPlayer((Position2D) posn, playerTurn).isEmpty());
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
   * Position2D.
   *
   * @param posn The Position2D to get the cell at
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
    List<Position2D> validTiles = this.isValidMoveForPlayer((Position2D) posn, this.currentPlayer);

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

package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cs3500.reversi.controller.ComputerPlayer;
import cs3500.reversi.controller.Player;
import cs3500.reversi.controller.PlayerType;

/**
 * Represents the model of a Reversi game. Manages the game board, players, and
 * game logic. In our game, the grid is made up of hexagons. The game is played
 * on a regular grid of cells.
 */
public class BasicReversiModel extends ABasicReversiModel {

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
    super(width);

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
    super(width, playerBlack, playerWhite);

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
  public BasicReversiModel(int width, HashMap<GamePosition, Cell> board, Player currentPlayer) {
    super(width, board, currentPlayer);
    // initialize the board
    this.initializeBoard();
    // add the starting pieces
    this.addStartingPieces();

    for (GamePosition posn : board.keySet()) {
      this.board.put(posn, board.get(posn));
    }
  }

  /**
   * Constructs a new BasicReversiModel with a default width of 11.
   */
  public BasicReversiModel() {
    super();
    // initialize the board
    this.initializeBoard();
    // add the starting pieces
    this.addStartingPieces();
  }

  @Override
  public HashMap<GamePosition, Cell> getBoardCopy() {
    HashMap<GamePosition, Cell> boardCopy = new HashMap<>();

    // iterate over the board and create a deep copy of each cell
    for (GamePosition posn : this.board.keySet()) {
      GamePosition posnCopy = new PositionAxial(posn.getQ(), posn.getR(), posn.getS());
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
   * Checks and retrieves a list of positions between the given position and
   * another position that form a valid line made by the player.
   *
   * @param givenPosn  The starting position.
   * @param posn       The ending position.
   * @param playerTurn The player whose turn it currently is.
   * @return A list of positions forming a valid line between the given positions,
   *         or an empty list if no valid line exists.
   */
  protected List<GamePosition> checkValidLineMade(GamePosition givenPosn, GamePosition posn,
      Player playerTurn) {
    ArrayList<GamePosition> cellsBetween = new ArrayList<>();

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
      ArrayList<GamePosition> cellsBetween, String row, int constantPosition,
      boolean foward, Player playerTurn) {
    // iterate through the range of coordinates and check for valid positions.
    while (true) {
      incrementStartingPostion += 1;
      decrementStartingPosition -= 1;

      // calculate current position based on direction
      GamePosition currentPosition = this.calculateStartingPosition(foward, row,
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
  private GamePosition calculateStartingPosition(boolean foward, String row,
      int incrementStartingPostion,
      int decrementStartingPosition,
      int constantPosition) {
    // calculate current position based on forward direction
    if (foward) {
      return getGamePosition(row, incrementStartingPostion, decrementStartingPosition,
              constantPosition);
    }
    // calculate current position based on backward direction
    else {
      return getGamePosition(row, decrementStartingPosition, incrementStartingPostion,
              constantPosition);
    }
  }

  private GamePosition getGamePosition(String row, int incrementStartingPostion,
                                       int decrementStartingPosition, int constantPosition) {
    if (row.equalsIgnoreCase("s")) {
      return new PositionAxial(incrementStartingPostion, decrementStartingPosition,
          constantPosition);
    } else if (row.equalsIgnoreCase("r")) {
      return new PositionAxial(incrementStartingPostion, constantPosition,
          decrementStartingPosition);
    } else if (row.equalsIgnoreCase("q")) {
      return new PositionAxial(constantPosition, incrementStartingPostion,
          decrementStartingPosition);
    } else {
      throw new IllegalArgumentException("Invalid row given");
    }
  }

  @Override
  protected void isWidthCorrect(int width) {
    if (width < 3 || width % 2 == 0) {
      throw new IllegalArgumentException("Width must be odd and at least three.");
    }
  }
}

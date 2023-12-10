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
public class BasicSquareReversiModel extends ABasicReversiModel {

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
    super(width);

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
    super(width, playerBlack, playerWhite);

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
   * Constructs a new BasicSquareReversiModel with a default width of 11.
   */
  public BasicSquareReversiModel() {
    super();

    // initialize the board
    this.initializeBoard();
    // add the starting pieces
    this.addStartingPieces();
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
        GamePosition posn = new Position2D(x, y);

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

    // If the given and ending positions share X coordinates, check for a valid line
    if (givenPosn.getQ() == posn.getQ()) {
      // Go along along the possible line
      this.goDownLine(givenPosn.getR(), cellsBetween, "y", givenPosn.getQ(), givenPosn.getR() <= posn.getR(), playerTurn);
    }

    // If the given and ending positions share Y coordinates, check for a valid line
    if (givenPosn.getR() == posn.getR()) {
      // Go along the possible line
      this.goDownLine(givenPosn.getQ(), cellsBetween, "x", givenPosn.getR(), givenPosn.getQ() <= posn.getQ(), playerTurn);
    }

    // Check for a valid line along the diagonal
    if (Math.abs(givenPosn.getQ() - posn.getQ()) == 1 && Math.abs(givenPosn.getR() - posn.getR()) == 1) {
      this.goDownLineDiagonal(givenPosn.getQ(), givenPosn.getR(), givenPosn.getQ() <= posn.getQ(), givenPosn.getR() <= posn.getR(), cellsBetween, playerTurn);
    }

    return cellsBetween;
  }

  private void goDownLineDiagonal(int startingXPosition, int startingYPosition, boolean incrementX, boolean incrementY,
      ArrayList<GamePosition> cellsBetween, Player playerTurn) {
    while (true) {
      if (incrementX) {
        startingXPosition += 1;
      } else {
        startingXPosition -= 1;
      }

      if (incrementY) {
        startingYPosition += 1;
      } else {
        startingYPosition -= 1;
      }

      GamePosition currentPosition = new Position2D(startingXPosition, startingYPosition);

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
      ArrayList<GamePosition> cellsBetween, String row, int constantPosition,
      boolean forward, Player playerTurn) {
    // iterate through the range of coordinates and check for valid positions.
    while (true) {
      if (forward) {
        incrementStartingPostion += 1;
      } else {
        incrementStartingPostion -= 1;
      }

      // calculate current position based on direction
      GamePosition currentPosition = this.calculateStartingPosition(row,
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
  private GamePosition calculateStartingPosition(String row,
      int incrementStartingPostion, int constantPosition) {
    // calculate current position based on row
    if (row.equalsIgnoreCase("x")) {
      return new Position2D(incrementStartingPostion, constantPosition);
    } else if (row.equalsIgnoreCase("y")) {
      return new Position2D(constantPosition, incrementStartingPostion);
    } else {
      throw new IllegalArgumentException("Invalid row given");
    }
  }

  @Override
  protected void isWidthCorrect(int width) {
    // Enforced invarient by checking if width is even and positive.
    if (width < 0 || width % 2 != 0) {
      throw new IllegalArgumentException("Width must be even and positive.");
    }
  }
}

package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cs3500.reversi.controller.GamePlayer;
import cs3500.reversi.controller.Player;
import cs3500.reversi.controller.PlayerType;

/**
 * Represents the model of a Reversi game.
 * Manages the game board, players, and game logic.
 * In our game, the grid is made up of hexagons.
 * The game is played on a regular grid of cells.
 */
public class BasicReversiModel implements ReversiModel {

    private final HashMap<PositionAxial, Cell> board;
    private final Player playerBlack;
    private final Player playerWhite;
    private final int width;
    private int currentPlayerIndex;
    private int consectivePassedTurns;
    private boolean gameStarted;

    /**
     * Constructs a new BasicReversiModel with the specified width.
     * The game can only be played on a regular grid of cells, so the
     * width needs to be an odd number. For a playable game, the width needs
     * to be at least three.
     *
     * @param width The width of the game board.
     * @throws IllegalArgumentException If the provided width is not an odd number
     *                                  or is less than three.
     */
    public BasicReversiModel(int width) {
        if (width < 3 || width % 2 == 0) {
            throw new IllegalArgumentException("Width must be odd and at least three.");
        }
        this.board = new HashMap<>();
        this.playerBlack = new GamePlayer(PlayerType.BLACK);
        this.playerWhite = new GamePlayer(PlayerType.WHITE);
        this.width = width;
        this.currentPlayerIndex = 0;
        this.consectivePassedTurns = 0;
        this.gameStarted = false;
    }

    /**
     * Constructs a new BasicReversiModel with a default width of 11.
     */
    public BasicReversiModel() {
        this.board = new HashMap<>();
        this.playerBlack = new GamePlayer(PlayerType.BLACK);
        this.playerWhite = new GamePlayer(PlayerType.WHITE);
        this.width = 11;
        this.currentPlayerIndex = 0;
        this.consectivePassedTurns = 0;
        this.gameStarted = false;
    }

    @Override
    public void startGame() {

        // initialize the board
        this.initializeBoard();
        // add the starting pieces
        this.addStartingPieces();
        // set the game to has started
        this.gameStarted = true;
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
                this.board.put(new PositionAxial(currentQ, currentR, currentS), new GameCell(CellType.Empty));
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
        whiteCell.setCellToPlayer(new GamePlayer(PlayerType.WHITE));

        Cell blackCell = new GameCell(CellType.Player);
        blackCell.setCellToPlayer(new GamePlayer(PlayerType.BLACK));

        this.initializeCell(-1, 0, 1, PlayerType.WHITE);
        this.initializeCell(0, -1, 1, PlayerType.BLACK);
        this.initializeCell(1, -1, 0, PlayerType.WHITE);
        this.initializeCell(1, 0, -1, PlayerType.BLACK);
        this.initializeCell(0, 1, -1, PlayerType.WHITE);
        this.initializeCell(-1, 1, 0, PlayerType.BLACK);

        // reset the currentPlayerIndex to ensure it starts from the first player for
        // subsequent moves.
        this.currentPlayerIndex = 0;
    }

    private void initializeCell(int q, int r, int s, PlayerType type) {
        this.board.put(new PositionAxial(q, r, s), new GameCell(CellType.Player));
        this.getCellAt(new PositionAxial(q, r, s)).setCellToPlayer(new GamePlayer(type));
    }

    @Override
    public void addPieceToCoordinates(PositionAxial posn) {

        // Get the list of valid positions to add a piece to on this move.
        List<PositionAxial> validTiles = this.isValidMoveForPlayer(posn);
        if (!(validTiles.size() == 0)) {

            // create a player cell to represent the player's piece.
            Cell playerCell = new GameCell(CellType.Player);

            // set the owner of the player cell to the current player.
            playerCell.setCellToPlayer(this.getCurrentTurn());

            // place the player cell on the game board at the specified position.
            this.board.put(posn, playerCell);

            // change the ownership of cells between the specified positions.
            this.changeAllCellsBetween(validTiles);

            // change the active player's turn and reset the consecutive passed turns
            // counter.
            this.changeTurns();
            this.consectivePassedTurns = 0;
        } else {
            throw new IllegalStateException("Move cannot be made");
        }
    }

    private Player getCurrentTurn() {
        if (this.currentPlayerIndex % 2 == 0) {
            return this.playerBlack;
        } else {
            return this.playerWhite;
        }
    }

    /**
     * Checks for cells that allow for valid moves for a player.
     *
     * @param givenPosn The position to check for a valid move.
     * @return A list of positions that represent valid moves, or an empty list if
     *         the move is invalid.
     */
    private List<PositionAxial> isValidMoveForPlayer(PositionAxial givenPosn) {
        Player otherPlayer = this.getNextTurn();
        ArrayList<PositionAxial> allCellsBetween = new ArrayList<>();
        List<PositionAxial> surroundingCells = this.getSurroundingCells(givenPosn);

        // iterate over the surrounding positions adjacent to the given position
        for (PositionAxial posn : surroundingCells) {

            // see if the cell at the surrounding position is owned by the other player
            // and if there is a valid line of cells after it, all cells in this line are
            // returned
            if (this.getCellAt(posn).getCellType().equals(CellType.Player)
                    && this.getCellAt(posn).getCellOwner().equals(otherPlayer.toString())) {
                allCellsBetween.addAll(this.checkValidLineMade(givenPosn, posn, otherPlayer));
            }
        }
        return allCellsBetween;
    }

    private Player getNextTurn() {
        if (this.currentPlayerIndex % 2 == 0) {
            return this.playerWhite;
        } else {
            return this.playerBlack;
        }
    }

    /**
     * Checks and retrieves a list of positions between the given position and
     * another position
     * that form a valid line made by the player.
     *
     * @param givenPosn   The starting position.
     * @param posn        The ending position.
     * @param otherPlayer The player to check for a valid line.
     * @return A list of positions forming a valid line between the given positions,
     *         or an empty list if no valid line exists.
     */
    private List<PositionAxial> checkValidLineMade(PositionAxial givenPosn, PositionAxial posn, Player otherPlayer) {
        ArrayList<PositionAxial> cellsBetween = new ArrayList<>();

        // check if given and ending positions share Q coordinates
        if (givenPosn.getQ() == posn.getQ()) {

            // determine the range of R and S coordinates between the two positions.
            int startingPositionR = Math.min(givenPosn.getR(), posn.getR());
            int startingPositionS = Math.max(givenPosn.getS(), posn.getS());

            this.goDownLine(startingPositionR, startingPositionS, cellsBetween, "q", givenPosn.getQ());
        }

        // check if given and ending positions share R coordinates
        if (givenPosn.getR() == posn.getR()) {

            // determine the range of Q and S coordinates between the two positions.
            int startingPositionQ = Math.min(givenPosn.getQ(), posn.getQ());
            int startingPositionS = Math.max(givenPosn.getS(), posn.getS());

            this.goDownLine(startingPositionQ, startingPositionS, cellsBetween, "r", givenPosn.getR());
        }

        // check if given and ending positions share S coordinates
        if (givenPosn.getS() == posn.getS()) {

            // determine the range of Q and R coordinates between the two positions.
            int startingPositionQ = Math.min(givenPosn.getQ(), posn.getQ());
            int startingPositionR = Math.max(givenPosn.getR(), posn.getR());

            this.goDownLine(startingPositionQ, startingPositionR, cellsBetween, "s", givenPosn.getS());
        }

        return cellsBetween;
    }

    private void goDownLine(int incrementStartingPostion, int decrementStartingPosition,
            ArrayList<PositionAxial> cellsBetween, String row, int constantPosition) {
        // iterate through the range of coordinates and check for valid positions.
        while (true) {
            incrementStartingPostion += 1;
            decrementStartingPosition -= 1;

            PositionAxial currentPosition;

            if (row.toLowerCase().equals("s")) {
                currentPosition = new PositionAxial(incrementStartingPostion, decrementStartingPosition,
                        constantPosition);
            } else if (row.toLowerCase().equals("r")) {
                currentPosition = new PositionAxial(incrementStartingPostion, constantPosition,
                        decrementStartingPosition);
            } else if (row.toLowerCase().equals("q")) {
                currentPosition = new PositionAxial(constantPosition, incrementStartingPostion,
                        decrementStartingPosition);
            } else {
                throw new IllegalArgumentException("Invalid row given");
            }

            // check if the position exists on the board and is owned by the other player.
            // if so, clear the list
            // if not, add the current position to the list
            if (!this.board.containsKey(currentPosition)) {
                cellsBetween.clear();
                break;
            }

            if (!this.getCellAt(currentPosition).getCellOwner().equals(this.getNextTurn().toString())) {
                if (this.getCellAt(currentPosition).getCellOwner().equals(this.getCurrentTurn().toString())) {
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
            this.getCellAt(posn).setCellToPlayer(this.getCurrentTurn());
        }
    }

    @Override
    public void passTurn() {
        this.consectivePassedTurns += 1;
    }

    /**
     * Changes the active player's turn.
     */
    private void changeTurns() {
        this.currentPlayerIndex += 1;
    }

    @Override
    public boolean isGameOver() {
        if (this.consectivePassedTurns >= 2) {
            return true;
        }

        for (PositionAxial posn : this.board.keySet()) {
            if (this.getCellAt(posn).sameCellType(CellType.Empty) && !(this.isValidMoveForPlayer(posn).size() == 0)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int getNumRows() {
        return this.width;
    }

    @Override
    public Cell getCellAt(int q, int r, int s) {
        return this.board.get(new PositionAxial(q, r, s));
    }

    @Override
    public Cell getCellAt(PositionAxial posn) {
        return this.board.get(posn);
    }
}
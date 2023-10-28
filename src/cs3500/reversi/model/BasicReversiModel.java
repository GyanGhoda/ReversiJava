package cs3500.reversi.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Represents the model of a Reversi game. 
 * Manages the game board, players, and game logic.
 * In our game, the grid is made up of hexagons.
 * The game is played on a regular grid of cells.
 */
public class BasicReversiModel implements ReversiModel {

    private final HashMap<PositionAxial, Cell> board;
    private final ArrayList<Player> players;
    private final int width;
    private int currentPlayerIndex;
    private int consectivePassedTurns;

    /**
     * Constructs a new BasicReversiModel with the specified width.
     *
     * @param width The width of the game board.
     */
    BasicReversiModel(int width) {
        this.board = new HashMap<>();
        this.players = new ArrayList<>();
        this.width = width;
        this.currentPlayerIndex = 0;
        this.consectivePassedTurns = 0;
    }

    /**
     * Constructs a new BasicReversiModel with a default width of 11.
     */
    BasicReversiModel() {
        this.board = new HashMap<>();
        this.players = new ArrayList<>();
        this.width = 11;
        this.currentPlayerIndex = 0;
        this.consectivePassedTurns = 0;
    }

    @Override
    public void startGame() {
        this.initializeBoard();
        this.addStartingPieces();
    }

    /**
     * Initializes the game board by creating empty cells for all positions on the board.
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

            // adjust the starting q or s coordinate for the next row based on the row index.
            // decrease q for the upper part of the board
            // decrease s for the lower part of the board
            if (!(rowsMade > middleY + 1)) {
                currentRowStartingQ -= 1;
            } else {
                currentRowStartingS -= 1;
            }

            // move to the next r coordinate for the next row
            currentR += 1;
        }
    }

    /**
     * Adds the starting pieces to the game board, setting the initial player positions.
     */
    private void addStartingPieces() {

        for (PositionAxial posn : this.board.keySet()) {

            // check if the current position meets the conditions for initial player placement.
            if (posn.containsCoordinate(1) && posn.containsCoordinate(-1)
                    && posn.containsCoordinate(0)) {
                Cell playerCell = new GameCell(CellType.Player);

                // set the owner of the player cell to one of the players based on currentPlayerIndex.
                playerCell.setCellToPlayer(this.players.get(this.currentPlayerIndex % 2));

                // place the player cell on the game board at the current position.
                this.board.put(posn, playerCell);

                // increment currentPlayerIndex to switch to the next player for the next placement.
                this.currentPlayerIndex += 1;
            }
        }

        // reset the currentPlayerIndex to ensure it starts from the first player for subsequent moves.
        this.currentPlayerIndex = 0;
    }

    @Override
    public void addPieceToCoordinates(PositionAxial posn) {

        // Get the list of valid positions to add a piece to on this move.
        List<PositionAxial> validTiles = this.isValidMoveForPlayer(posn);
        if (!(validTiles.size() == 0)) {

            // create a player cell to represent the player's piece.
            Cell playerCell = new GameCell(CellType.Player);
        
            // set the owner of the player cell to the current player.
            playerCell.setCellToPlayer(this.players.get(this.currentPlayerIndex));
        
            // place the player cell on the game board at the specified position.
            this.board.put(posn, playerCell);
        
            // change the ownership of cells between the specified positions.
            this.changeAllCellsBetween(validTiles);
        
            // change the active player's turn and reset the consecutive passed turns counter.
            this.changeTurns();
            this.consectivePassedTurns = 0;
        } else {
            throw new IllegalStateException("Move cannot be made");
        }
    }

    /**
     * Checks for cells that allow for valid moves for a player.
     *
     * @param givenPosn The position to check for a valid move.
     * @return A list of positions that represent valid moves, or an empty list if the move is invalid.
     */
    private List<PositionAxial> isValidMoveForPlayer(PositionAxial givenPosn) {
        int q = givenPosn.getQ();
        int r = givenPosn.getR();
        int s = givenPosn.getS();
        Player otherPlayer = this.players.get(currentPlayerIndex + 1);
        ArrayList<PositionAxial> allCellsBetween = new ArrayList<>();

        // iterate over the surrounding positions adjacent to the given position
        for (PositionAxial posn : this.getSurroundingCells(givenPosn)) {

            // see if the cell at the surrounding position is owned by the other player
            // and if there is a valid line of cells after it, all cells in this line are returned
            if (this.board.get(posn).getCellOwner().equals(otherPlayer)) {
                allCellsBetween.addAll(this.checkValidLineMade(givenPosn, posn, otherPlayer));
            }
        }

        return allCellsBetween;
    }

    // for (PositionAxial posn : this.board.keySet()) {
    // boolean validMove = true;

    // if ((posn.containsCoordinate(q))
    // && !posn.equals(givenPosn)) {
    // int startingPositionR = Math.min(givenPosn.getR(), posn.getR());
    // int endingPositonR = Math.max(givenPosn.getR(), posn.getR());
    // int startingPositionS = Math.max(givenPosn.getS(), posn.getS());

    // while (!(startingPositionR == endingPositonR)) {
    // startingPositionR += 1;
    // startingPositionS -= 1;
    // if (!this.board.get(new PositionAxial(q, startingPositionR,
    // startingPositionS))
    // .getCellOwner().equals(otherPlayer)) {
    // validMove = false;
    // break;
    // }
    // }

    // if (validMove) {
    // return posn;
    // }
    // }

    // if ((posn.containsCoordinate(r))
    // && !posn.equals(givenPosn)) {
    // int startingPositionQ = Math.min(givenPosn.getQ(), posn.getQ());
    // int endingPositonQ = Math.max(givenPosn.getQ(), posn.getQ());
    // int startingPositionS = Math.max(givenPosn.getS(), posn.getS());

    // while (!(startingPositionQ == endingPositonQ)) {
    // startingPositionQ += 1;
    // startingPositionS -= 1;
    // if (!this.board.get(new PositionAxial(startingPositionQ, r,
    // startingPositionS))
    // .getCellOwner().equals(otherPlayer)) {
    // validMove = false;
    // break;
    // }
    // }

    // if (validMove) {
    // return posn;
    // }
    // }

    // if ((posn.containsCoordinate(s))
    // && !posn.equals(givenPosn)) {
    // int startingPositionQ = Math.min(givenPosn.getQ(), posn.getQ());
    // int endingPositonQ = Math.max(givenPosn.getQ(), posn.getQ());
    // int startingPositionR = Math.max(givenPosn.getR(), posn.getR());

    // while (!(startingPositionQ == endingPositonQ)) {
    // startingPositionQ += 1;
    // startingPositionR -= 1;
    // if (!this.board.get(new PositionAxial(startingPositionQ, startingPositionR,
    // s))
    // .getCellOwner().equals(otherPlayer)) {
    // validMove = false;
    // break;
    // }
    // }

    // if (validMove) {
    // return posn;
    // }
    // }
    // }
    // return null;
    // }

    /**
    * Checks and retrieves a list of positions between the given position and another position
    * that form a valid line made by the player.
    *
    * @param givenPosn     The starting position.
    * @param posn          The ending position.
    * @param otherPlayer  The player to check for a valid line.
    * @return A list of positions forming a valid line between the given positions, or an empty list if no valid line exists.
    */
    private List<PositionAxial> checkValidLineMade(PositionAxial givenPosn, PositionAxial posn, Player otherPlayer) {
        ArrayList<PositionAxial> cellsBetween = new ArrayList<>();

        if (givenPosn.getQ() == posn.getQ()) {
            int startingPositionR = Math.min(givenPosn.getR(), posn.getR());
            int endingPositonR = Math.max(givenPosn.getR(), posn.getR());
            int startingPositionS = Math.max(givenPosn.getS(), posn.getS());

            while (!(startingPositionR == endingPositonR)) {
                startingPositionR += 1;
                startingPositionS -= 1;
                PositionAxial currentPosition = new PositionAxial(givenPosn.getQ(), startingPositionR, startingPositionS);

                if (!this.board.containsKey(currentPosition) || !this.board.get(currentPosition).getCellOwner().equals(otherPlayer)) {
                    cellsBetween.clear();
                    break;
                } 
                else {
                    cellsBetween.add(currentPosition);
                }
            }
        }

        if (givenPosn.getR() == posn.getR()) {
            int startingPositionQ = Math.min(givenPosn.getQ(), posn.getQ());
            int endingPositonQ = Math.max(givenPosn.getQ(), posn.getQ());
            int startingPositionS = Math.max(givenPosn.getS(), posn.getS());

            while (!(startingPositionQ == endingPositonQ)) {
                startingPositionQ += 1;
                startingPositionS -= 1;
                PositionAxial currentPosition = new PositionAxial(endingPositonQ, givenPosn.getR(), startingPositionS);

                if (!this.board.containsKey(currentPosition) || !this.board.get(currentPosition).getCellOwner().equals(otherPlayer)) {
                    cellsBetween.clear();
                    break;
                } 
                else {
                    cellsBetween.add(currentPosition);
                }
            }
        }

        if (givenPosn.getS() == posn.getS()) {
            int startingPositionQ = Math.min(givenPosn.getQ(), posn.getQ());
            int endingPositonQ = Math.max(givenPosn.getQ(), posn.getQ());
            int startingPositionR = Math.max(givenPosn.getR(), posn.getR());

            PositionAxial currentPosition = new PositionAxial(startingPositionQ, startingPositionR, givenPosn.getS());

            while (!(startingPositionQ == endingPositonQ)) {
                startingPositionQ += 1;
                startingPositionR -= 1;
                if (!this.board.containsKey(currentPosition) || !this.board.get(currentPosition).getCellOwner().equals(otherPlayer)) {
                    cellsBetween.clear();
                    break;
                } else {
                    cellsBetween.add(currentPosition);
                }
            }
        }

        return cellsBetween;
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
    * Changes the ownership of all cells between the specified positions to the current player.
    *
    * @param posnBetween A list of positions between which cell ownership should be changed.
    */
    private void changeAllCellsBetween(List<PositionAxial> posnBetween) {
        for (PositionAxial posn : posnBetween) {
            this.board.get(posn).setCellToPlayer(this.players.get(currentPlayerIndex));
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
        if (this.currentPlayerIndex > this.players.size() - 1) {
            this.currentPlayerIndex = 0;
        } else {
            this.currentPlayerIndex += 1;
        }
    }

    @Override
    public boolean isGameOver() {
        if (this.consectivePassedTurns == this.players.size()) {
            return true;
        }

        for (PositionAxial posn : this.board.keySet()) {
            if (this.board.get(posn).sameCellType(CellType.Empty) && !(this.isValidMoveForPlayer(posn) == null)) {
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
}
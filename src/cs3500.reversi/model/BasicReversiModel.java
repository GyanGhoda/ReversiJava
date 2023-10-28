package model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class BasicReversiModel implements ReversiModel {

    private final HashMap<PositionAxial, Cell> board;
    private final ArrayList<Player> players;
    private final int width;
    private int currentPlayerIndex;
    private int consectivePassedTurns;

    BasicReversiModel(int width) {
        this.board = new HashMap<>();
        this.players = new ArrayList<>();
        this.width = width;
        this.currentPlayerIndex = 0;
        this.consectivePassedTurns = 0;
    }

    BasicReversiModel() {
        this.board = new HashMap<>();
        this.players = new ArrayList<>();
        this.width = 11;
        this.currentPlayerIndex = 0;
        this.consectivePassedTurns = 0;
    }

    public void startGame() {
        this.initializeBoard();
        this.addStartingPieces();
    }

    private void initializeBoard() {
        int middleY = (this.width - 1) / 2;
        int currentRowStartingQ = 0;
        int currentRowStartingS = middleY;
        int currentR = -middleY;

        for (int rowsMade = 0; rowsMade < width; rowsMade += 1) {
            int currentQ = currentRowStartingQ;

            for (int currentS = currentRowStartingS; currentS >= currentRowStartingQ; currentS -= 1) {
                this.board.put(new PositionAxial(currentQ, currentR, currentS), new GameCell(CellType.Empty));
                currentQ += 1;
            }

            if (!(rowsMade > middleY + 1)) {
                currentRowStartingQ -= 1;
            } else {
                currentRowStartingS -= 1;
            }
            currentR += 1;
        }
    }

    private void addStartingPieces() {

        for (PositionAxial posn : this.board.keySet()) {
            if (posn.containsCoordinate(1) && posn.containsCoordinate(-1)
                    && posn.containsCoordinate(0)) {
                Cell playerCell = new GameCell(CellType.Player);
                playerCell.setCellToPlayer(this.players.get(this.currentPlayerIndex % 2));
                this.board.put(posn, playerCell);
                this.currentPlayerIndex += 1;
            }
        }

        this.currentPlayerIndex = 0;
    }

    public void addPieceToCoordinates(PositionAxial posn) {
        List<PositionAxial> validTiles = this.isValidMoveForPlayer(posn);
        if (!(validTiles.size() == 0)) {
            Cell playerCell = new GameCell(CellType.Player);
            playerCell.setCellToPlayer(this.players.get(this.currentPlayerIndex));
            this.board.put(posn, playerCell);
            this.changeAllCellsBetween(validTiles);
            this.changeTurns();
            this.consectivePassedTurns = 0;
        } else {
            throw new IllegalStateException("Move cannot be made");
        }
    }

    private List<PositionAxial> isValidMoveForPlayer(PositionAxial givenPosn) {
        int q = givenPosn.getQ();
        int r = givenPosn.getR();
        int s = givenPosn.getS();
        Player otherPlayer = this.players.get(currentPlayerIndex + 1);
        ArrayList<PositionAxial> allCellsBetween = new ArrayList<>();

        for (PositionAxial posn : this.getSurroundingCells(givenPosn)) {
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

    private List<PositionAxial> checkValidLineMade(PositionAxial givenPosn, PositionAxial posn, Player otherPlayer) {
        ArrayList<PositionAxial> cellsBetween = new ArrayList<>();

        if (givenPosn.getQ() == posn.getQ()) {
            int startingPositionQ = Math.min(givenPosn.getQ(), posn.getQ());
            int endingPositonQ = Math.max(givenPosn.getQ(), posn.getQ());
            int startingPositionS = Math.max(givenPosn.getS(), posn.getS());

            while (!(startingPositionQ == endingPositonQ)) {
                startingPositionQ += 1;
                startingPositionS -= 1;
                if (!this.board.get(new PositionAxial(startingPositionQ, givenPosn.getR(), startingPositionS))
                        .getCellOwner().equals(otherPlayer)) {
                    cellsBetween.clear();
                    break;
                } else {
                    cellsBetween.add(new PositionAxial(startingPositionQ, givenPosn.getR(), startingPositionS));
                }
            }
        }

        if (givenPosn.getS() == posn.getS()) {
            int startingPositionQ = Math.min(givenPosn.getQ(), posn.getQ());
            int endingPositonQ = Math.max(givenPosn.getQ(), posn.getQ());
            int startingPositionR = Math.max(givenPosn.getR(), posn.getR());

            while (!(startingPositionQ == endingPositonQ)) {
                startingPositionQ += 1;
                startingPositionR -= 1;
                if (!this.board.get(new PositionAxial(startingPositionQ, startingPositionR, givenPosn.getS()))
                        .getCellOwner().equals(otherPlayer)) {
                    cellsBetween.clear();
                    break;
                } else {
                    cellsBetween.add(new PositionAxial(startingPositionQ, startingPositionR, givenPosn.getS()));
                }
            }
        }

        return cellsBetween;
    }

    private List<PositionAxial> getSurroundingCells(PositionAxial givenPosn) {
        ArrayList<PositionAxial> surroundingCells = new ArrayList<>();

        for (PositionAxial posn : this.board.keySet()) {
            if (posn.isNextTo(givenPosn)) {
                surroundingCells.add(posn);

            }
        }

        return surroundingCells;
    }

    private void changeAllCellsBetween(List<PositionAxial> posnBetween) {
        for (PositionAxial posn : posnBetween) {
            this.board.get(posn).setCellToPlayer(this.players.get(currentPlayerIndex));
        }
    }

    public void passTurn() {
        this.consectivePassedTurns += 1;
    }

    private void changeTurns() {
        if (this.currentPlayerIndex > this.players.size() - 1) {
            this.currentPlayerIndex = 0;
        } else {
            this.currentPlayerIndex += 1;
        }
    }

    private boolean isGameOver() {
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
}
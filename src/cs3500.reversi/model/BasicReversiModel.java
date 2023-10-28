package model;

import java.util.ArrayList;
import java.util.HashMap;

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

    public void playGame() {
        this.initializeBoard();
        this.addStartingPieces();

        this.isGameOver();
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
        PositionAxial validTile = this.isValidMoveForPlayer(posn);
        if (!(validTile == null)) {
            Cell playerCell = new GameCell(CellType.Player);
            playerCell.setCellToPlayer(this.players.get(this.currentPlayerIndex));
            this.board.put(posn, playerCell);
            this.changeAllCellsBetween(posn, validTile);
            this.changeTurns();
            this.consectivePassedTurns = 0;
        } else {
            throw new IllegalStateException("Move cannot be made");
        }
    }

    private PositionAxial isValidMoveForPlayer(PositionAxial givenPosn) {
        int q = givenPosn.getQ();
        int r = givenPosn.getR();
        int s = givenPosn.getS();
        Player otherPlayer = this.players.get(currentPlayerIndex + 1);

        for (PositionAxial posn : this.board.keySet()) {
            boolean validMove = true;

            if ((posn.containsCoordinate(q))
                    && !posn.equals(givenPosn)) {
                int startingPositionR = Math.min(givenPosn.getR(), posn.getR());
                int endingPositonR = Math.max(givenPosn.getR(), posn.getR());
                int startingPositionS = Math.max(givenPosn.getS(), posn.getS());

                while (!(startingPositionR == endingPositonR)) {
                    startingPositionR += 1;
                    startingPositionS -= 1;
                    if (!this.board.get(new PositionAxial(q, startingPositionR, startingPositionS))
                            .getCellOwner().equals(otherPlayer)) {
                        validMove = false;
                        break;
                    }
                }

                if (validMove) {
                    return posn;
                }
            }

            if ((posn.containsCoordinate(r))
                    && !posn.equals(givenPosn)) {
                int startingPositionQ = Math.min(givenPosn.getQ(), posn.getQ());
                int endingPositonQ = Math.max(givenPosn.getQ(), posn.getQ());
                int startingPositionS = Math.max(givenPosn.getS(), posn.getS());

                while (!(startingPositionQ == endingPositonQ)) {
                    startingPositionQ += 1;
                    startingPositionS -= 1;
                    if (!this.board.get(new PositionAxial(startingPositionQ, r, startingPositionS))
                            .getCellOwner().equals(otherPlayer)) {
                        validMove = false;
                        break;
                    }
                }

                if (validMove) {
                    return posn;
                }
            }

            if ((posn.containsCoordinate(s))
                    && !posn.equals(givenPosn)) {
                int startingPositionQ = Math.min(givenPosn.getQ(), posn.getQ());
                int endingPositonQ = Math.max(givenPosn.getQ(), posn.getQ());
                int startingPositionR = Math.max(givenPosn.getR(), posn.getR());

                while (!(startingPositionQ == endingPositonQ)) {
                    startingPositionQ += 1;
                    startingPositionR -= 1;
                    if (!this.board.get(new PositionAxial(startingPositionQ, startingPositionR, s))
                            .getCellOwner().equals(otherPlayer)) {
                        validMove = false;
                        break;
                    }
                }

                if (validMove) {
                    return posn;
                }
            }
        }
        return null;
    }

    private void changeAllCellsBetween(PositionAxial firstPosn, PositionAxial secondPosn) {
        if (firstPosn.commonCoordinate(secondPosn).equals("q")) {
            int startingPositionR = Math.min(firstPosn.getR(), secondPosn.getR());
            int endingPositonR = Math.max(firstPosn.getR(), secondPosn.getR());
            int startingPositionS = Math.max(firstPosn.getS(), secondPosn.getS());

            while (!(startingPositionR == endingPositonR)) {
                startingPositionR += 1;
                startingPositionS -= 1;
                this.board.get(new PositionAxial(firstPosn.getQ(), startingPositionR, startingPositionS))
                        .setCellToPlayer(this.players.get(currentPlayerIndex));
            }
        }

        if (firstPosn.commonCoordinate(secondPosn).equals("r")) {
            int startingPositionQ = Math.min(firstPosn.getQ(), secondPosn.getQ());
            int endingPositonQ = Math.max(firstPosn.getQ(), secondPosn.getQ());
            int startingPositionS = Math.max(firstPosn.getS(), secondPosn.getS());

            while (!(startingPositionQ == endingPositonQ)) {
                startingPositionQ += 1;
                startingPositionS -= 1;
                this.board.get(new PositionAxial(startingPositionQ, firstPosn.getR(), startingPositionS))
                        .setCellToPlayer(this.players.get(currentPlayerIndex));
            }
        }

        if (firstPosn.commonCoordinate(secondPosn).equals("s")) {
            int startingPositionQ = Math.min(firstPosn.getQ(), secondPosn.getQ());
            int endingPositonQ = Math.max(firstPosn.getQ(), secondPosn.getQ());
            int startingPositionR = Math.max(firstPosn.getR(), secondPosn.getR());

            while (!(startingPositionQ == endingPositonQ)) {
                startingPositionQ += 1;
                startingPositionR -= 1;
                this.board.get(new PositionAxial(startingPositionQ, firstPosn.getR(), startingPositionR))
                        .setCellToPlayer(this.players.get(currentPlayerIndex));
            }
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
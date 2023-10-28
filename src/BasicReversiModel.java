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

        for (PositionAxial coordinate : this.board.keySet()) {
            if (coordinate.containsCoordinate(1) && coordinate.containsCoordinate(-1)
                    && coordinate.containsCoordinate(0)) {
                Cell playerCell = new GameCell(CellType.Player);
                playerCell.setCellToPlayer(this.players.get(this.currentPlayerIndex % 2));
                this.board.put(coordinate, playerCell);
                this.currentPlayerIndex += 1;
            }
        }

        this.currentPlayerIndex = 0;
    }

    public void addPieceToCoordinates(int q, int r, int s) {
        if (this.isValidMoveForPlayer(q, r, s)) {
            Cell playerCell = new GameCell(CellType.Player);
            playerCell.setCellToPlayer(this.players.get(this.currentPlayerIndex));
            this.board.put(new PositionAxial(q, r, s), playerCell);
            this.changeTurns();
            this.passedTurns = 0;
        } else {
            throw new IllegalStateException("Move cannot be made");
        }
    }

    private boolean isValidMoveForPlayer(int q, int r, int s) {
        return true;
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

        for (PositionAxial coordinate : this.board.keySet()) {
            if (this.board.get(coordinate).getCellType().equals(CellType.Empty)) {
                return false;
            }
        }

        return true;
    }
}
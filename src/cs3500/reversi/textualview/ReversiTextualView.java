package cs3500.reversi.textualview;

import cs3500.reversi.model.CellType;
import cs3500.reversi.model.GameCell;
import cs3500.reversi.model.PositionAxial;
import cs3500.reversi.model.ReversiModel;

public class ReversiTextualView implements TextualView {
    private final ReversiModel model;

    ReversiTextualView(ReversiModel model) {
        this.model = model;
    }

    @Override
    public String toString() {
        String fullBoard = "";
        int currentStartingQ = 0;
        int currentStartingS = (this.model.getNumRows() - 1) / 2;

        for (int currentRow = 0; currentRow < this.model.getNumRows(); currentRow += 1) {
            fullBoard = fullBoard.concat(this.rowToString(currentStartingQ, currentRow, currentStartingS)).concat("\n");

            if (!(currentRow > ((this.model.getNumRows() - 1) / 2) + 1)) {
                currentStartingQ -= 1;
            } else {
                currentStartingS -= 1;
            }
        }

        return fullBoard;
    }

    private String rowToString(int currentGivenQ, int currentRow, int currentGivenS) {
        String rowString = "";
        int currentQ = currentGivenQ;
        int currentS = currentGivenS;

        for (int count = 0; count < Math.abs(currentRow); count += 1) {
            rowString = rowString.concat(" ");
        }

        for (int count = 0; count <= (this.model.getNumRows() - 1) / 2; count += 1) {
            rowString = rowString.concat(this.model.getCellAt(currentQ, currentRow, currentS).toString()).concat(" ");
            currentQ += 1;
            currentS -= 1;
        }

        if (rowString.endsWith(" ")) {
            rowString = rowString.substring(0, rowString.length() - 1);
        }

        return rowString;
    }
}

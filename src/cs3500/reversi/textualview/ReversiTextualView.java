package cs3500.reversi.textualview;

import cs3500.reversi.model.ReversiModel;

/**
 * Represents a textual view of a Reversi game board.
 */
public class ReversiTextualView implements TextualView {

    private final ReversiModel model;

    /**
     * Constructs a ReversiTextualView with the given ReversiModel.
     *
     * @param model The ReversiModel to be displayed in the textual view.
     */
    public ReversiTextualView(ReversiModel model) {
        this.model = model;
    }

    /**
     * Generates a string representation of the entire Reversi game board.
     *
     * @return A string representing the Reversi game board.
     */
    @Override
    public String toString() {
        String fullBoard = "";
        int currentStartingQ = 0;
        int maxR = (this.model.getNumRows() - 1) / 2;
        int currentStartingS = maxR;

        // goes row by row and adds the textual views to the full board
        for (int currentRow = -maxR; currentRow <= maxR; currentRow += 1) {
            fullBoard = fullBoard.concat(this.rowToString(currentStartingQ, currentRow, currentStartingS)).concat("\n");

            if (!(currentRow > maxR + 1)) {
                currentStartingQ -= 1;
            } else {
                currentStartingS -= 1;
            }
        }

        return fullBoard;
    }

    /**
     * Generates a string representation of a single row of the Reversi game board.
     *
     * @param currentGivenQ The initial Q-coordinate for the row.
     * @param currentRow    The current row number.
     * @param currentGivenS The initial S-coordinate for the row.
     * @return A string representing a row of the Reversi game board.
     */
    private String rowToString(int currentGivenQ, int currentRow, int currentGivenS) {
        String rowString = "";
        int currentQ = currentGivenQ;
        int currentS = currentGivenS;

        // add spaces to beginning of each row as necessary where there are no cells to
        // display
        for (int count = 0; count < Math.abs(currentRow); count += 1) {
            rowString = rowString.concat(" ");
        }

        // go through the cells in a row and append each cell to the view
        for (int count = 0; count <= (this.model.getNumRows() - 1) / 2; count += 1) {
            rowString = rowString.concat(this.model.getCellAt(currentQ, currentRow, currentS).toString()).concat(" ");
            currentQ += 1;
            currentS -= 1;
        }

        // removes trailing space if applicable
        if (rowString.endsWith(" ")) {
            rowString = rowString.substring(0, rowString.length() - 1);
        }

        return rowString;
    }
}

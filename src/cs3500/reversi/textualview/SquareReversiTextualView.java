package cs3500.reversi.textualview;

import cs3500.reversi.model.Position2D;
import cs3500.reversi.model.PositionAxial;
import cs3500.reversi.model.ReversiModel;

public class SquareReversiTextualView implements TextualView {
    // the ReversiModel to be displayed in the textual view
    private final ReversiModel model;

    /**
     * Constructs a ReversiTextualView with the given ReversiModel.
     *
     * @param model The ReversiModel to be displayed in the textual view.
     */
    public SquareReversiTextualView(ReversiModel model) {
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

        for (int y = 0; y < this.model.getNumRows(); y++) {
            for (int x = 0; x < this.model.getNumRows(); x++) {
                fullBoard = fullBoard
                        .concat(" ".concat(this.model.getCellAt(new Position2D(x, y)).toString().concat(" ")));
            }
            fullBoard = fullBoard.concat("\n");
        }
        return fullBoard;
    }
}

import java.util.ArrayList;
import java.util.HashMap;

public class BasicReversiModel implements ReversiModel {

    HashMap<PositionAxial, Cell> board = new HashMap<>();
    ArrayList<Player> players = new ArrayList<>();


    BasicReversiModel(int width) {
        this.initializeBoard(width);
    }

    BasicReversiModel() {
        this.initializeBoard(11);
    }

    public void playGame() {
        
    }

    private void initializeBoard(int width) {
        int middleY = (width - 1) / 2;
        int currentRowQ = 0;
        int currentR = -middleY;

        for (int rowsMade = 0; rowsMade < width; rowsMade += 1) {
            int currentQ = currentRowQ;
            for (int currentS = middleY; currentS >= currentRowQ; currentS -= 1) {
                this.board.put(new PositionAxial(currentQ, currentR, currentS), new GameCell(CellType.Empty));
                currentQ += 1;
            }

            if (!(rowsMade > middleY + 1)) {
                currentRowQ -= 1;
            }
            currentR += 1;
        }
    }
}
import java.util.HashMap;

public class BasicReversiModel implements ReversiModel {

    HashMap<Posn, Cell> board = new HashMap<>();

    BasicReversiModel(int height, int width) {
        this.initializeBoard(height, width);
    }

    public void playGame() {

    }

    private void initializeBoard(int height, int width) {

    }
}
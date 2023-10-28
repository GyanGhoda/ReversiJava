package cs3500.reversi.model;;

public class HumanPlayer implements Player {
    private final PlayerType type;

    public HumanPlayer(PlayerType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        if (this.type.equals(PlayerType.BLACK)) {
            return "X";
        } else {
            return "O";
        }
    }
}

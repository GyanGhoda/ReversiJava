package cs3500.reversi.controller;

/**
 * Represents a human player in a Reversi game. It implements the Player
 * interface.
 */
public class GamePlayer implements Player {

    private final PlayerType type;

    /**
     * Constructs a new HumanPlayer with the given player type.
     *
     * @param type The type of the player, which can be BLACK or WHITE.
     */
    public GamePlayer(PlayerType type) {
        this.type = type;
    }

    /**
     * Returns a string representation of the player.
     *
     * @return The string representation of the player, "X" for BLACK, and "O" for
     *         WHITE.
     */
    @Override
    public String toString() {
        if (this.type.equals(PlayerType.BLACK)) {
            return "X";
        } else {
            return "O";
        }
    }
}
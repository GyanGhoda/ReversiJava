package cs3500.reversi.controller;

import java.util.Objects;

import cs3500.reversi.model.PositionAxial;
import cs3500.reversi.model.ReversiModel;

/**
 * Represents a human player in the game of Reversi.
 */
public class HumanPlayer implements Player {
    // The type of the player, which can be BLACK or WHITE.
    final PlayerType type;

    /**
     * Constructs a new ComputerPlayer with the given player type.
     *
     * @param type The type of the player, which can be BLACK or WHITE.
     */
    public HumanPlayer(PlayerType type) {
        this.type = Objects.requireNonNull(type);
    }

    /**
     * requests a move from the player. Since this is a human player, return the
     * posn that they selected.
     *
     * @param posn  - The position selected by the player.
     * @param model - The model to move to
     * 
     * @return the position that is chosen to move to
     */
    @Override
    public PositionAxial requestMove(ReversiModel model, PositionAxial posn) {
        return posn;
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

    /**
     * Overrides the equals method to check if the given object is the same Player.
     *
     * @return true if the given object is the same Player, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ComputerPlayer)) {
            return false;
        } else {
            ComputerPlayer that = (ComputerPlayer) other;
            return this.type.equals(that.type);
        }
    }

    /**
     * Overrides the hashCode method to return the hashCode of the Player.
     *
     * @return the hashCode of the Player
     */
    @Override
    public int hashCode() {
        if (this.type.equals(PlayerType.BLACK)) {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * Returns a Player that is the the other player.
     *
     * @return the opposite player
     */
    public Player getOppositePlayer() {
        if (this.type.equals(PlayerType.BLACK)) {
            return new ComputerPlayer(PlayerType.WHITE);
        } else {
            return new ComputerPlayer(PlayerType.BLACK);
        }
    }

    @Override
    public PositionAxial notifyYourTurn(ReversiModel model) {
        return new PositionAxial(0, 0, 0);
        // Do nothing as this is a human player and the controller is simply waiting on
        // player input.
    }

}

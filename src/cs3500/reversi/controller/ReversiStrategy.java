package cs3500.reversi.controller;

import cs3500.reversi.model.PositionAxial;
import cs3500.reversi.model.ReversiModel;

/**
 * Represents a strategy for playing Reversi.
 */
public interface ReversiStrategy {

    /**
     * Chooses a move for the player to player.
     * 
     * @param model      the model to choose a move from
     * @param playerTurn the player whose turn it is
     * @return the position that is chosen to move to
     */
    public PositionAxial chooseMove(ReversiModel model, PlayerType playerTurn);
}

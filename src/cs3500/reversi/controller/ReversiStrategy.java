package cs3500.reversi.controller;

import cs3500.reversi.model.PositionAxial;
import cs3500.reversi.model.ReversiModel;

public interface ReversiStrategy {

    /**
     * Chooses a move for the player to player.
     * 
     * @return the position that is chosen to move to
     */
    public PositionAxial chooseMove(ReversiModel model, PlayerType playerTurn);
}

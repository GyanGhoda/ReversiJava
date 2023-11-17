package cs3500.reversi.controller;

import cs3500.reversi.model.PositionAxial;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.visualview.ReversiVisualView;

public class BasicReversiController implements Features {
    private final ReversiModel model;
    private final Player player;
    private final ReversiVisualView view;

    public BasicReversiController(ReversiModel model, Player player, ReversiVisualView view) {
        this.model = model;
        this.player = player;
        this.view = view;
    }

    /**
     * Moves the current player's piece to the given position.
     *
     * @param posn the position to move to
     */
    @Override
    public void moveToCoordinate(PositionAxial posn) {
        this.model.addPieceToCoordinates(posn, player);
        System.out.println("User has requested to move to:\nQ: " + posn.getQ() + "\nR: "
                + posn.getR() + "\nS: " + posn.getS());
    }

    /**
     * Passes the turn to the next player.
     */
    @Override
    public void passTurn() {
        this.model.passTurn();
        System.out.println("User has requested to pass turn.");
    }

    public void notifyToRefresh(String currentTurn) {
        this.view.refresh(currentTurn.equals(this.player.toString()));
    }

    @Override
    public String getPlayer() {
        return this.player.toString();
    }
}

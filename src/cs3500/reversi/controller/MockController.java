package cs3500.reversi.controller;

import cs3500.reversi.model.PositionAxial;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.visualview.ReversiVisualView;

/**
 * Represents a controller for the game of Reversi.
 */
public class MockController implements PlayerActionFeatures, ModelStatusFeatures {
    private final ReversiModel model;
    private final Player player;
    private final ReversiVisualView view;
    // the log of the controller
    final StringBuilder log;

    /**
     * Constructs a new BasicReversiController with the given model, player, and view.
     *
     * @param model  the model to use
     * @param player the player to use
     * @param view   the view to use
     */
    public MockController(ReversiModel model, Player player, ReversiVisualView view) {
        this.model = model;
        this.player = player;
        this.view = view;
        this.log = new StringBuilder();
    }

    /**
     * Moves the current player's piece to the given position.
     *
     * @param posn the position to move to
     */
    @Override
    public void moveToCoordinate(PositionAxial posn) {

      this.log.append("Requested move to coordinate: " + posn.toString() + "\n");

        // if the game is not over, add the piece to the board.
        // if (!this.model.isGameOver()) {
        //     PositionAxial posnToMove = this.player.requestMove(this.model, posn);
        //     if (posnToMove.containsCoordinate(this.model.getBoardSize())) {
        //         this.passTurn();
        //     } else {
        //         this.model.addPieceToCoordinates(posnToMove, this.player);
        //     }
        //     System.out.println("User has requested to move to:\nQ: " + posn.getQ() + "\nR: "
        //             + posn.getR() + "\nS: " + posn.getS());
        // }
    }

    /**
     * Passes the turn to the next player.
     */
    @Override
    public void passTurn() {

      this.log.append("Requested to pass turn.\n");

      // if (!this.model.isGameOver()) {
      //   this.model.passTurn(this.player);
      //   System.out.println("User has requested to pass turn.");
      // }
        
    }

    @Override
    public void notifyToRefresh(String currentTurn) {

      this.log.append("Notified to refresh.\n");
        
        // // repaints the appropriate frame 
        // this.view.refresh(currentTurn.equals(this.player.toString()));

        // // if it is the player's turn, notify them
        // if (currentTurn.equals(this.player.toString())) {
        //     if (!this.player.notifyYourTurn(model).equals(new PositionAxial(0, 0, 0))) {
        //         this.moveToCoordinate(new PositionAxial(0, 0, 0));
        //     }
        // }
    }

    @Override
    public String getPlayer() {

      return this.player.toString();
    }
    
    /**
     * Gets the log of the game.
     *
     * @return The log of the game.
     */
    public String getLog() {
      return this.log.toString();
    }
}

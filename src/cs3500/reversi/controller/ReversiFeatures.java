package cs3500.reversi.controller;

import cs3500.reversi.model.PositionAxial;

/**
 * Represents the features that the controller can use to interact with the model.
 */
public class ReversiFeatures implements Features {

  /**
   * Moves the current player;s piece to the given position.
   *
   * @param posn the position to move to
   */
  @Override
  public void moveToCoordinate(PositionAxial posn) {
    // Code to actually move the piece will be added with the controller. Temporary
    // print statment added
    System.out.println("User has requested to move to:\nQ: " + posn.getQ() + "\nR: "
            + posn.getR() + "\nS: " + posn.getS());
  }

  /**
   * Passes the turn to the next player.
   */
  @Override
  public void passTurn() {
    // Code to actually pass the turn will be added with the controller. Temporary
    // print statment added
    System.out.println("User has requested to pass turn.");
  }

}

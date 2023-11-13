package cs3500.reversi.controller;

import cs3500.reversi.model.PositionAxial;

public class ReversiFeatures implements Features {

    @Override
    public void moveToCoordinate(PositionAxial posn) {
        // Code to actually move the piece will be added with the controller. Temporary
        // print statment added
        System.out.println("User has requested to move to:\nQ: " + posn.getQ() + "\nR: "
                + posn.getR() + "\nS: " + posn.getS());
    }

    @Override
    public void passTurn() {
        // Code to actually pass the turn will be added with the controller. Temporary
        // print statment added
        System.out.println("User has requested to pass turn.");
    }

}

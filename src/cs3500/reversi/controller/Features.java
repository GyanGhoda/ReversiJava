package cs3500.reversi.controller;

import javax.swing.text.Position;

import cs3500.reversi.model.PositionAxial;

public interface Features {
    public void moveToCoordinate(PositionAxial posn);

    public void passTurn();
}

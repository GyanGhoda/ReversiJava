package model;

public interface ReversiModel {

  //
  void startGame();

  int getNumRows();

  boolean isGameOver();

  Cell getCellAt(int q, int r, int s);
}

package cs3500.reversi.model;

public interface ReversiModel {

  //
  void startGame();

  int getNumRows();

  boolean isGameOver();

  Cell getCellAt(int q, int r, int s);
}

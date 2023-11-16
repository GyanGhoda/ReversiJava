package cs3500.reversi;

import cs3500.reversi.model.BasicReversiModel;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.visualview.HexagonalFrame;
import cs3500.reversi.visualview.ReversiVisualView;

/**
 * Main class that instantiates a model and view.
 */
public final class Reversi {

  /**
   * Main class that instantiates a model and view.
   */
  public static void main(String[] args) {
    ReversiModel model = new BasicReversiModel(7);

    ReversiVisualView view = new HexagonalFrame(model);

    view.makeVisible();

  }
}
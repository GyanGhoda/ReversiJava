package cs3500.reversi;

import cs3500.reversi.model.BasicReversiModel;
import cs3500.reversi.model.ReadOnlyReversiModel;
import cs3500.reversi.visualview.HexagonalFrame;
import cs3500.reversi.visualview.ReversiVisualView;

public final class Reversi {
  public static void main(String[] args) {
    ReadOnlyReversiModel model = new BasicReversiModel(7);

    ReversiVisualView view = new HexagonalFrame(model);
    // YourModel model = ...create an example model...
    // YourView view = new YourView(model);

    view.makeVisible();
  }
}
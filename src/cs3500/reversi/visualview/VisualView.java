package cs3500.reversi.visualview;

import cs3500.reversi.model.ReversiModel;

public class VisualView implements ReversiVisualView {
    ReversiModel model;

    public VisualView(ReversiModel model) {
        this.model = model;
    }

    public void render() {
        HexagonalFrame frame = new HexagonalFrame(this.model);
    }
}

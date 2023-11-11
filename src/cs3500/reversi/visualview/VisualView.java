package cs3500.reversi.visualview;

import cs3500.reversi.model.ReadOnlyReversiModel;

public class VisualView {
    ReadOnlyReversiModel model;

    public VisualView(ReadOnlyReversiModel model) {
        this.model = model;
    }

    public void render() {
        HexagonalFrame frame = new HexagonalFrame(this.model);
    }
}

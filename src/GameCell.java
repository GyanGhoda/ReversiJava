public class GameCell implements Cell {
    private CellType type;

    GameCell(CellType type) {
        this.type = type;
    }

    @Override
    public Player getCellPlayer() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCellPlayer'");
    };
}

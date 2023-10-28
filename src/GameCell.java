public class GameCell implements Cell {
    private CellType type;
    private Player cellOwner;

    GameCell(CellType type) {
        this.type = type;
    }

    @Override
    public Player getCellPlayer() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCellPlayer'");
    };

    public void setCellToPlayer(Player player) {
        this.cellOwner = player;
    }

    public CellType getCellType() {
        return this.CellType;
    }
}

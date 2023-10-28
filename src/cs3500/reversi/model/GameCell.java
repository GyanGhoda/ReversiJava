package cs3500.reversi.model;;

public class GameCell implements Cell {
    private CellType type;
    private Player cellOwner;

    public GameCell(CellType type) {
        this.type = type;
    }

    @Override
    public void setCellToPlayer(Player player) {
        this.cellOwner = player;
    }

    @Override
    public CellType getCellType() {
        return this.type;
    }

    @Override
    public boolean sameCellType(CellType Othertype) {
        return this.type.equals(Othertype);
    }

    @Override
    public Player getCellOwner() {
        return this.cellOwner;
    }

    @Override
    public String toString() {
        return this.cellOwner.toString();
    }
}

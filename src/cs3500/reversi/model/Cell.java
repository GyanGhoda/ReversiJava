package cs3500.reversi.model;;

public interface Cell {
    Player getCellOwner();

    public void setCellToPlayer(Player player);

    public CellType getCellType();

    public boolean sameCellType(CellType Othertype);
}

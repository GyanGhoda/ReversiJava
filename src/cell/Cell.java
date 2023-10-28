package cell;

public interface Cell {
    player.Player getCellOwner();

    public void setCellToPlayer(player.Player player);

    public CellType getCellType();

    public boolean sameCellType(CellType Othertype);
}

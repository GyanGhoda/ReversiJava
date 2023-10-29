package cs3500.reversi.model;;

/**
 * Represents a cell in a Reversi game board. Each cell can be owned by a player
 * and has a cell type.
 */
public interface Cell {

    /**
     * Get the owner of the cell.
     *
     * @return The player that owns the cell, or null if the cell is empty.
     */
    Player getCellOwner();

    /**
     * Set the owner of the cell.
     *
     * @param player The player to set as the owner of the cell.
     */
    public void setCellToPlayer(Player player);

    /**
     * Get the type of the cell.
     *
     * @return The type of the cell.
     */
    public CellType getCellType();

    /**
     * Check if the cell has the same type as the given CellType.
     *
     * @param otherType The CellType to compare with.
     * @return true if the cell has the same type as the given CellType, false otherwise.
     */
    public boolean sameCellType(CellType Othertype);
}
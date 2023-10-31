package cs3500.reversi.model;

import java.util.Objects;

import cs3500.reversi.controller.Player;

/**
 * The GameCell class represents a cell in a Reversi game board. It implements
 * the Cell interface.
 */
public class GameCell implements Cell {

    // The type of the cell, which can be Empty or Player.
    private CellType type;
    // The player that owns the cell.
    private Player cellOwner;

    /**
     * Constructs a new GameCell with the given CellType.
     *
     * @param type The type of the cell, which can be Empty or Player.
     */
    public GameCell(CellType type) {
        this.type = Objects.requireNonNull(type);
    }

    /**
     * Set the owner of the cell. Updates the cell type to Player.
     *
     * @param player The player to set as the owner of the cell.
     */
    @Override
    public void setCellToPlayer(Player player) {
        // Update the cell owner to the given player
        this.cellOwner = Objects.requireNonNull(player);
        // Update the cell type to Player
        this.type = CellType.Player;
    }

    /**
     * Get the type of the cell.
     *
     * @return The type of the cell.
     */
    @Override
    public CellType getCellType() {
        return this.type;
    }

    /**
     * Check if the cell has the same type as the given CellType.
     *
     * @param otherType The CellType to compare with.
     * @return true if the cell has the same type as the given CellType, false
     *         otherwise.
     */
    @Override
    public boolean sameCellType(CellType Othertype) {
        return this.type.equals(Othertype);
    }

    /**
     * Get the owner of the cell.
     *
     * @return The player that owns the cell, or "" if the cell is empty as a
     *         String.
     */
    @Override
    public String getCellOwner() {
        if (this.type.equals(CellType.Player)) {
            return this.cellOwner.toString();
        } else {
            return "";
        }
    }

    /**
     * The string representation of the cell. If the cell is empty, it returns "_"
     * 
     * @return The string representation of the cell, which is either "_" or the
     *         cellOwner as a String
     */
    @Override
    public String toString() {
        if (this.type.equals(CellType.Player)) {
            return this.cellOwner.toString();
        } else {
            return "_";
        }
    }
}

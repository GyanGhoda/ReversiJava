package cs3500.reversi.model;

;

/**
 * The GameCell class represents a cell in a Reversi game board. It implements
 * the Cell interface.
 */
public class GameCell implements Cell {

    private CellType type;
    private Player cellOwner;

    /**
     * Constructs a new GameCell with the given CellType.
     *
     * @param type The type of the cell, which can be Empty or Player.
     */
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
        if (this.type.equals(CellType.Player)) {
            return this.cellOwner.toString();
        } else {
            return "_";
        }
    }
}

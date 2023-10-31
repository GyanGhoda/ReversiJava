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

  @Override
  public void setCellToPlayer(Player player) {
    this.cellOwner = Objects.requireNonNull(player);
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
  public String getCellOwner() {
    if (this.type.equals(CellType.Player)) {
      return this.cellOwner.toString();
    } else {
      return "";
    }
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

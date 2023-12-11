package cs3500.reversi.model;

/**
 * Represents a position in a Reversi game board. A position can be either 2D or
 * Axial.
 */
public interface GamePosition {
  /**
   * Get the 'x' coordinate of this Position2D.
   *
   * @return The 'x' coordinate.
   */
  int getQ();

  /**
   * Get the 'y' coordinate of this Position2D.
   *
   * @return The 'y' coordinate.
   */
  int getR();

  /**
   * Get the 's' coordinate of this GamePosition. Throws an InvalidStateException if used on a
   * Postion2D.
   *
   * @return The 's' coordinate.
   */
  int getS();

  /**
   * Checks if this PositionAxial contains the specified coordinate value.
   *
   * @param coordinate The coordinate value to check.
   * @return true if this PositionAxial contains the given coordinate; false
   * otherwise.
   */
  boolean containsCoordinate(int coordinate);

  /**
   * Determines the common coordinate between this Position2D and another
   * Position2D.
   *
   * @param other The other GamePosition to compare with.
   * @return The common coordinate, which can be "q," "r," or "s." If there is no
   * common coordinate, it returns "NoCommonCoordinate."
   */
  String commonCoordinate(GamePosition other);

  /**
   * Determines the common coordinate between this Position2D and another
   * Position2D.
   *
   * @param other The other Position2D to compare with.
   * @return The common coordinate, which can be "x" or "y." If there is no common
   * coordinate,
   * it returns "NoCommonCoordinate."
   */
  String commonCoordinate2D(Position2D other);

  /**
   * Determines the common coordinate between this PositionAxial and another
   * PositionAxial.
   *
   * @param other The other PositionAxial to compare with.
   * @return The common coordinate, which can be "q," "r," or "s." If there is no
   * common coordinate,
   * it returns "NoCommonCoordinate."
   */
  String commonCoordinateAxial(PositionAxial other);

  /**
   * Checks if this Position2D is adjacent to another Position2D. Adjacency
   * means sharing a side (not a corner).
   *
   * @param other The other Position2D to check adjacency with.
   * @return true if this Position2D is adjacent to the other Position2D;
   * false otherwise.
   */
  boolean isNextTo(GamePosition other);

  /**
   * Checks if this Position2D is adjacent to another Position2D.
   *
   * @param other The other Position2D to check adjacency with.
   * @return true if this Position2D is adjacent to the other Position2D;
   */
  boolean isNextTo2D(Position2D other);

  /**
   * Checks if this PositionAxial is adjacent to another PositionAxial.
   *
   * @param other The other PositionAxial to check adjacency with.
   * @return true if this PositionAxial is adjacent to the other PositionAxial;
   */
  boolean isNextToAxial(PositionAxial other);

  /**
   * Checks if this GamePosition is next to the corner of the board.
   *
   * @param numRows - the number of rows in the board
   * @return true if this GamePosition is next to the corner of the board
   */
  boolean checkNextToCorner(int numRows);

  /**
   * Checks if this GamePosition is a corner of the board.
   *
   * @param numRows - the number of rows in the board
   * @return true if this GamePosition is a corner of the board
   */
  boolean checkCorner(int numRows);

  /**
   * Checks if this GamePosition is the leftuppermost position on the board.
   *
   * @param other - the other GamePosition to compare with
   * @return the leftuppermost GamePosition
   */
  GamePosition checkLeftUpperMost(GamePosition other);

  /**
   * Checks if this Position2D is the leftuppermost position on the board.
   *
   * @param other - the other Position2D to compare with
   * @return the leftuppermost Position2D
   */
  GamePosition checkLeftUpperMost2D(Position2D other);

  /**
   * Checks if this PositionAxial is the leftuppermost position on the board.
   *
   * @param other - the other PositionAxial to compare with
   * @return the leftuppermost PositionAxial
   */
  GamePosition checkLeftUpperMostAxial(PositionAxial other);
}

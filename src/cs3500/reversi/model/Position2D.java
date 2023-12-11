package cs3500.reversi.model;

/**
 * This class represents a 2D position using axial coordinates.
 * A position is defined by the values of x and y.
 */
public final class Position2D implements GamePosition {

  // The axial coordinates of this Position2D
  private final int x;
  private final int y;

  /**
   * Constructs a new Position2D object with the given axial coordinates.
   *
   * @param x The 'x' coordinate.
   * @param y The 'y' coordinate.
   */
  public Position2D(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Checks if this Position2D is equal to the given object by comparing their
   * axial coordinates.
   *
   * @param obj The object to compare with.
   * @return true if the given object is a Position2D and has the same 'q,'
   *         'r,' and 's' values; false otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    // If the given object is an instance of Position2D
    if (obj instanceof Position2D) {
      // Return whether they have the same value and symbol
      return ((Position2D) obj).x == this.x && ((Position2D) obj).y == this.y;
    }
    // Return false if the given object is not a Position2D
    return false;
  }

  /**
   * Returns a unique hashcode for every unique non-equal instance of a
   * Position2D.
   *
   * @return the unique hashcode of this object as an int.
   */
  @Override
  public int hashCode() {
    return Integer.parseInt(Integer.toString(this.x * this.y - this.x - this.y));
  }

  /**
   * Checks if this Position2D contains the specified coordinate value.
   *
   * @param coordinate The coordinate value to check.
   * @return true if this Position2D contains the given coordinate; false
   *         otherwise.
   */
  public boolean containsCoordinate(int coordinate) {
    return this.x == coordinate || this.y == coordinate;
  }

  /**
   * Get the 'x' coordinate of this Position2D.
   *
   * @return The 'x' coordinate.
   */
  public int getQ() {
    return this.x;
  }

  /**
   * Get the 'y' coordinate of this Position2D.
   *
   * @return The 'y' coordinate.
   */
  public int getR() {
    return this.y;
  }

  /**
   * Get the 's' coordinate of this Position2D. Does not exist so throw an error
   *
   * @return - An IllegalStateException as there is no 's' coordinate
   */
  public int getS() {
    throw new IllegalStateException("Invalid coordinate requested");
  }

  /**
   * Determines the common coordinate between this Position2D and another
   * Position2D.
   *
   * @param other The other GamePosition to compare with.
   * @return The common coordinate, which can be "q," "r," or "s." If there is no
   *         common coordinate, it returns "NoCommonCoordinate."
   */
  public String commonCoordinate(GamePosition other) {
    return other.commonCoordinate2D(this);
  }

  /**
   * Determines the common coordinate between this Position2D and another.
   * 
   * @param other The other Position2D to compare with.
   * @return The common coordinate, which can be "x" or "y." If there is no common
   *         coordinate,
   *         it returns "NoCommonCoordinate."
   */
  public String commonCoordinate2D(Position2D other) {
    if (this.getQ() == other.getQ()) {
      return "x";
    }

    if (this.getR() == other.getR()) {
      return "y";
    }

    return "NoCommonCoordinate";
  }

  /**
   * Determines the common coordinate between this Position2D and another
   * PositionalAxial.
   * 
   * @param other The other PositionAxial to compare with.
   * @return NoCommonCoordinate as this Position2D is not a PositionAxial.
   */
  public String commonCoordinateAxial(PositionAxial other) {
    return "NoCommonCoordinate";
  }

  /**
   * Checks if this Position2D is adjacent to another Position2D. Adjacency
   * means sharing a side (not a corner).
   *
   * @param other The other Position2D to check adjacency with.
   * @return true if this Position2D is adjacent to the other Position2D;
   *         false otherwise.
   */
  public boolean isNextTo(GamePosition other) {
    return other.isNextTo2D(this);
  }

  /**
   * Checks if this Position2D is adjacent to another Position2D.
   * 
   * @param other The other Position2D to check adjacency with.
   * @return true if this Position2D is adjacent to the other Position2D;
   */
  public boolean isNextTo2D(Position2D other) {
    if (Math.abs(this.x - other.x) <= 1 && Math.abs(this.y - other.y) <= 1) {
      return this.x != other.x || this.y != other.y;
    }

    return false;
  }

  /**
   * Checks if this Position2D is adjacent to another PositionAxial.
   * 
   * @param other The other PositionAxial to check adjacency with.
   * @return false as this Position2D is not a PositionAxial.
   */
  public boolean isNextToAxial(PositionAxial other) {
    return false;
  }

  /**
   * Returns the axial coordinates of this Position2D as a String in Q R S format.
   *
   * @return The axial coordinates of this Position2D as a String.
   */
  public String toString() {
    return "X: " + this.x + ", Y: " + this.y;
  }

  /**
   * Checks if this Position2D is in the corner of the board.
   * 
   * @param numRows - the number of rows in the board
   * @return true if this Position2D is in the corner of the board
   */
  @Override
  public boolean checkNextToCorner(int numRows) {
    return ((Math.abs(this.x) == numRows - 2) || this.x == 1)
            && ((Math.abs(this.y) == numRows - 2) || this.y == 1);
  }

  /**
   * Checks if this GamePosition is a corner of the board.
   * 
   * @param numRows - the number of rows in the board
   * @return true if this GamePosition is a corner of the board
   */
  @Override
  public boolean checkCorner(int numRows) {
    return ((Math.abs(this.x) == numRows - 1) || this.x == 0)
            && ((Math.abs(this.y) == numRows - 1) || this.y == 0);
  }

  /**
   * Checks if this GamePosition is the leftuppermost position on the board.
   * 
   * @param other - the other GamePosition to compare with
   * @return the leftuppermost GamePosition
   */
  @Override
  public GamePosition checkLeftUpperMost(GamePosition other) {
    return other.checkLeftUpperMost2D(this);
  }

  /**
   * Checks if this Position2D is the leftuppermost position on the board.
   * 
   * @param other - the other Position2D to compare with
   * @return the leftuppermost Position2D
   */
  @Override
  public GamePosition checkLeftUpperMost2D(Position2D other) {
    if (this.x < other.x) {
      return this;
    } else if (this.x > other.x) {
      return other;
    } else {
      if (this.y < other.y) {
        return this;
      } else {
        return other;
      }
    }
  }

  /**
   * Checks if this PositionAxial is the leftuppermost position on the board.
   * 
   * @param other - the other PositionAxial to compare with
   * @return the leftuppermost PositionAxial
   */
  @Override
  public GamePosition checkLeftUpperMostAxial(PositionAxial other) {
    throw new IllegalArgumentException("Position2D given for PositionAxial method");
  }
}

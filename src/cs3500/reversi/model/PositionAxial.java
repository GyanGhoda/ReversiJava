package cs3500.reversi.model;

/**
 * This class represents a 2D position using axial coordinates.
 * A position is defined by the values of 'q,' 'r,' and 's.'
 */
public final class PositionAxial {

  // The axial coordinates of this PositionAxial
  private final int q;
  private final int r;
  private final int s;

  /**
   * Constructs a new PositionAxial object with the given axial coordinates.
   *
   * @param q The 'q' coordinate.
   * @param r The 'r' coordinate.
   * @param s The 's' coordinate.
   */
  public PositionAxial(int q, int r, int s) {
    this.q = q;
    this.r = r;
    this.s = s;
  }

  /**
   * Checks if this PositionAxial is equal to the given object by comparing their
   * axial coordinates.
   *
   * @param obj The object to compare with.
   * @return true if the given object is a PositionAxial and has the same 'q,'
   * 'r,' and 's' values; false otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    // If the given object is an instance of PositionAxial
    if (obj instanceof PositionAxial) {
      // Return whether they have the same value and symbol
      return ((PositionAxial) obj).q == this.q && ((PositionAxial) obj).r == this.r
              && ((PositionAxial) obj).s == this.s;
    }
    // Return false if the given object is not a PositionAxial
    return false;
  }

  /**
   * Returns a unique hashcode for every unique non-equal instance of a
   * PositionAxial.
   *
   * @return the unique hashcode of this object as an int.
   */
  @Override
  public int hashCode() {
    return Integer.valueOf(Integer.toString(this.q * this.r * this.s - this.q - this.r - this.s));
  }

  /**
   * Checks if this PositionAxial contains the specified coordinate value.
   *
   * @param coordinate The coordinate value to check.
   * @return true if this PositionAxial contains the given coordinate; false
   * otherwise.
   */
  public boolean containsCoordinate(int coordinate) {
    return this.q == coordinate || this.r == coordinate || this.s == coordinate;
  }

  /**
   * Get the 'q' coordinate of this PositionAxial.
   *
   * @return The 'q' coordinate.
   */
  public int getQ() {
    return this.q;
  }

  /**
   * Get the 'r' coordinate of this PositionAxial.
   *
   * @return The 'r' coordinate.
   */
  public int getR() {
    return this.r;
  }

  /**
   * Get the 's' coordinate of this PositionAxial.
   *
   * @return The 's' coordinate.
   */
  public int getS() {
    return this.s;
  }

  /**
   * Determines the common coordinate between this PositionAxial and another
   * PositionAxial.
   *
   * @param other The other PositionAxial to compare with.
   * @return The common coordinate, which can be "q," "r," or "s." If there is no
   * common coordinate, it returns "NoCommonCoordinate."
   */
  public String commonCoordinate(PositionAxial other) {
    if (this.getQ() == other.getQ()) {
      return "q";
    }

    if (this.getR() == other.getR()) {
      return "r";
    }

    if (this.getS() == other.getS()) {
      return "s";
    }

    return "NoCommonCoordinate";
  }

  /**
   * Checks if this PositionAxial is adjacent to another PositionAxial. Adjacency
   * means sharing a side (not a corner).
   *
   * @param other The other PositionAxial to check adjacency with.
   * @return true if this PositionAxial is adjacent to the other PositionAxial;
   * false otherwise.
   */
  public boolean isNextTo(PositionAxial other) {
    if (this.q == other.q && Math.abs(this.r - other.r) == 1 && Math.abs(this.s - other.s) == 1) {
      return true;
    }

    if (this.r == other.r && Math.abs(this.q - other.q) == 1 && Math.abs(this.s - other.s) == 1) {
      return true;
    }

    return this.s == other.s && Math.abs(this.q - other.q) == 1 && Math.abs(this.r - other.r) == 1;
  }

  /**
   * Returns the axial coordinates of this PositionAxial as a String in Q R S format.
   *
   * @return The axial coordinates of this PositionAxial as a String.
   */
  public String toString() {
    return "Q: " + this.q + ", R: " + this.r + ", S: " + this.s;
  }
}

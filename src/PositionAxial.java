import java.util.Objects;

/**
 * This class represents a 2D position
 */
public final class PositionAxial {
  private final double q;
  private final double r;
  private final double s;

  PositionAxial(double q, double r, double s) {
    this.q = q;
    this.r = r;
    this.s = s;
  }

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

  public boolean containsCoordinate(int coordinate) {
    return this.q == coordinate || this.r == coordinate || this.s == coordinate;
  }
}

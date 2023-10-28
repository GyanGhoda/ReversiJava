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
}

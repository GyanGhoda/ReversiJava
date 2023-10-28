package model;

/**
 * This class represents a 2D position
 */
public final class PositionAxial {
    private final int q;
    private final int r;
    private final int s;

    public PositionAxial(int q, int r, int s) {
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

    public int getQ() {
        return this.q;
    }

    public int getR() {
        return this.r;
    }

    public int getS() {
        return this.s;
    }

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
}

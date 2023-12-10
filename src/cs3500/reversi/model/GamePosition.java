package cs3500.reversi.model;

public interface GamePosition {
    /**
     * Checks if this Position contains the specified coordinate value.
     *
     * @param coordinate The coordinate value to check.
     * @return true if this Position contains the given coordinate; false
     *         otherwise.
     */
    public boolean containsCoordinate(int coordinate);

    /**
     * Determines the common coordinate between this Position2D and another
     * Position2D.
     *
     * @param other The other GamePosition to compare with.
     * @return The common coordinate.  If there is no
     *         common coordinate, it returns "NoCommonCoordinate."
     */
    public String commonCoordinate(GamePosition other);

    /**
     * Determines the common coordinate between this Position2D and another
     * Position2D.
     * 
     * @param other The other Position2D to compare with.
     * @return The common coordinate, which can be "x" or "y." If there is no common
     *         coordinate,
     *         it returns "NoCommonCoordinate."
     */
    public String commonCoordinate2D(Position2D other);

    /**
     * Determines the common coordinate between this PositionAxial and another
     * PositionAxial.
     * 
     * @param other The other PositionAxial to compare with.
     * @return The common coordinate, which can be "q," "r," or "s." If there is no
     *         common coordinate,
     *         it returns "NoCommonCoordinate."
     */
    public String commonCoordinateAxial(PositionAxial other);

    /**
     * Checks if this PositionAxial is adjacent to another PositionAxial. Adjacency
     * means sharing a side (not a corner).
     *
     * @param other The other Position to check adjacency with.
     * @return true if this Position is adjacent to the other Position2D;
     *         false otherwise.
     */
    public boolean isNextTo(GamePosition other);

    /**
     * Checks if this Position2D is adjacent to another Position2D.
     * 
     * @param other The other Position2D to check adjacency with.
     * @return true if this Position2D is adjacent to the other Position2D;
     */
    public boolean isNextTo2D(Position2D other);

    /**
     * Checks if this PositionAxial is adjacent to another PositionAxial.
     * 
     * @param other The other PositionAxial to check adjacency with.
     * @return true if this PositionAxial is adjacent to the other PositionAxial;
     */
    public boolean isNextToAxial(PositionAxial other);

    /**
     * Checks if this GamePosition is next to the corner of the board
     * 
     * @param numRows - the number of rows in the board
     * @return true if this GamePosition is next to the corner of the board
     */
    public boolean checkNextToCorner(int numRows);

    /**
     * Checks if this GamePosition is a corner of the board
     * 
     * @param numRows - the number of rows in the board
     * @return true if this GamePosition is a corner of the board
     */
    public boolean checkCorner(int numRows);

    /**
     * Checks if this GamePosition is the leftuppermost position on the board
     * 
     * @param other - the other GamePosition to compare with
     * @return the leftuppermost GamePosition
     */
    public GamePosition checkLeftUpperMost(GamePosition other);

    /**
     * Checks if this Position2D is the leftuppermost position on the board
     * 
     * @param other - the other Position2D to compare with
     * @return the leftuppermost Position2D
     */
    public GamePosition checkLeftUpperMost2D(Position2D other);

    /**
     * Checks if this PositionAxial is the leftuppermost position on the board
     * 
     * @param other - the other PositionAxial to compare with
     * @return the leftuppermost PositionAxial
     */
    public GamePosition checkLeftUpperMostAxial(PositionAxial other);
}

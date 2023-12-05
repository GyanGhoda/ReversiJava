package cs3500.reversi.model;

public interface GamePosition {
    /**
     * Checks if this PositionAxial contains the specified coordinate value.
     *
     * @param coordinate The coordinate value to check.
     * @return true if this PositionAxial contains the given coordinate; false
     *         otherwise.
     */
    public boolean containsCoordinate(int coordinate);

    /**
     * Determines the common coordinate between this Position2D and another
     * Position2D.
     *
     * @param other The other GamePosition to compare with.
     * @return The common coordinate, which can be "q," "r," or "s." If there is no
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
     * Checks if this Position2D is adjacent to another Position2D. Adjacency
     * means sharing a side (not a corner).
     *
     * @param other The other Position2D to check adjacency with.
     * @return true if this Position2D is adjacent to the other Position2D;
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
     * Checks if this GamePosition is in the corner of the board
     * 
     * @param numRows - the number of rows in the board
     * @return true if this GamePosition is in the corner of the board
     */
    public boolean checkCorner(int numRows);
}

package cs3500.reversi.visualview;

import javax.swing.*;

import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.PositionAxial;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.HashMap;

/**
 * Represents a panel of hexagonal buttons.
 */
public class HexagonalPanel extends JPanel {
    HashMap<PositionAxial, HexagonSpace> hexagonButtons = new HashMap<PositionAxial, HexagonSpace>();
    ReversiModel model;
    int width;
    int height;

    /**
     * Constructs a new HexagonalPanel with the given number of rows and columns.
     *
     * @param numRows The number of rows.
     * @param numCols The number of columns.
     */
    public HexagonalPanel(ReversiModel model, int width, int height) {
        setLayout(null); // Use absolute positioning
        this.model = model;
        this.width = width;
        this.height = height;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        int distance = Math.min(this.width, this.height) / this.model.getNumRows();

        int buttonSize = (int) (distance / Math.sqrt(3));

        System.out.println(buttonSize);

        // calculate the middle row of the game board
        int middleY = (this.model.getNumRows() - 1) / 2;

        // initialize starting q and s coordinates for the current row
        int currentRowStartingQ = 0;
        int currentRowStartingS = middleY;

        // initialize the r coordinate for the current row
        int currentR = -middleY;

        for (int rowsMade = 0; rowsMade < this.model.getNumRows(); rowsMade += 1) {
            // initialize q coordinate for current position
            int currentQ = currentRowStartingQ;

            double startingX = (((buttonSize * Math.sqrt(3)) / 2) * Math.abs(currentR))
                    + (((int) (buttonSize * Math.sqrt(3))) / 2);
            double startingY = ((buttonSize * 3) / 2) * (rowsMade + 1);

            for (int currentS = currentRowStartingS; currentS >= currentRowStartingQ; currentS -= 1) {
                HexagonSpace hexagon = new HexagonSpace(buttonSize, startingX, startingY);

                g2d.setColor(Color.LIGHT_GRAY); // Set the color of the hexagon
                g2d.fill(hexagon);

                g2d.setColor(Color.BLACK); // Set the color of the border
                g2d.draw(hexagon);

                // create empty cell and add it to the board at the current position
                this.hexagonButtons.put(new PositionAxial(currentQ, currentR, currentS),
                        hexagon);

                // move to the next q coordinate in the row
                hexagon.moveTo(startingX, startingY);

                startingX += Math.sqrt(3) * buttonSize;
                currentQ += 1;
            }

            // adjust the starting q or s coordinate for the next row based on the row
            // index.
            // decrease q for the upper part of the board
            // decrease s for the lower part of the board
            if (rowsMade < middleY) {
                currentRowStartingQ -= 1;
            } else {
                currentRowStartingS -= 1;
            }

            // move to the next r coordinate for the next row
            currentR += 1;
        }
    }
}
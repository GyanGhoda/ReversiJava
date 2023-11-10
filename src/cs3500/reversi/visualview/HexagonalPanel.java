package cs3500.reversi.visualview;

import javax.swing.*;

import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.CellType;
import cs3500.reversi.model.GameCell;
import cs3500.reversi.model.PositionAxial;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a panel of hexagonal buttons.
 */
public class HexagonalPanel extends JPanel {
    HashMap<PositionAxial, HexagonButton> hexagonButtons = new HashMap<PositionAxial, HexagonButton>();
    ReversiModel model;

    /**
     * Constructs a new HexagonalPanel with the given number of rows and columns.
     *
     * @param numRows The number of rows.
     * @param numCols The number of columns.
     */
    public HexagonalPanel(ReversiModel model) {
        setLayout(null); // Use absolute positioning
        this.model = model;
        this.initalizeGameBoard();

        // for (int i = 0; i < numCells; i += 1) {
        // HexagonButton button = new HexagonButton("Button " + (i + 1));
        // hexagonButtons.add(button);
        // add(button);
        // }

        // // Create and add hexagonal buttons
        // for (int row = 0; row < numRows; row++) {
        // for (int col = 0; col < numCols; col++) {
        // HexagonButton button = new HexagonButton("Button " + (row * numCols + col +
        // 1));
        // hexagonButtons[row][col] = button;
        // add(button);
        // }
        // }
    }

    private void initalizeGameBoard() {

        int buttonSize = 50;// this.getWidth() / this.model.getNumRows(); // The size of each button

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

            int startingX = (buttonSize / 2) * Math.abs(currentR);
            int startingY = (buttonSize / 2) * (rowsMade + 1);

            for (int currentS = currentRowStartingS; currentS >= currentRowStartingQ; currentS -= 1) {
                // create empty cell and add it to the board at the current position
                this.hexagonButtons.put(new PositionAxial(currentQ, currentR, currentS),
                        new HexagonButton("Button " + (currentQ) + " in row " + (currentR + 1), buttonSize));
                // move to the next q coordinate in the row
                this.hexagonButtons.get(new PositionAxial(currentQ, currentR, currentS)).setBounds(startingX,
                        startingY, buttonSize, buttonSize);
                add(hexagonButtons.get(new PositionAxial(currentQ, currentR, currentS)));
                startingX += buttonSize;
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

    // @Override
    // public void paintComponent(Graphics g) {
    // super.paintComponent(g);

    // int buttonSize = this.getWidth() / this.model.getNumRows(); // The size of
    // each button
    // int currentX = (buttonSize / 2) * Math.abs(this.model.getNumRows() / 2);
    // int currentY = buttonSize * (((this.model.getNumRows() - 1) / 2) -
    // Math.abs(this.model.getNumRows() / 2));
    // }

    // int buttonSize = Math.min(getWidth() / (this.model.getNumRows() *
    // this.numCells), getHeight() / (this.model.getNumRows() * 3)); // The size of
    // each button
    // int startX = getWidth() / 2 - (int) (numCols * 1.5 * buttonSize);
    // int startY = getHeight() / 2 - (int) (numRows * Math.sqrt(3) * buttonSize /
    // 2);

    // // Set the bounds of each button
    // for (int row = 0; row < numRows; row++) {
    // for (int col = 0; col < numCols; col++) {
    // HexagonButton button = hexagonButtons[row][col];
    // int x = startX + (int) (col * 3 * buttonSize);
    // int y = startY + (int) (row * Math.sqrt(3) * buttonSize);
    // button.setBounds(x, y, buttonSize, buttonSize);
    // }
    // }
    // }
}
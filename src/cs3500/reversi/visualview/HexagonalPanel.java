package cs3500.reversi.visualview;

import javax.swing.*;

import cs3500.reversi.model.ReversiModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a panel of hexagonal buttons.
 */
public class HexagonalPanel extends JPanel {
    ArrayList<HexagonButton> hexagonButtons = new ArrayList<HexagonButton>();
    ReversiModel model;
    int numCells;
    int rowNumber;

    /**
     * Constructs a new HexagonalPanel with the given number of rows and columns.
     *
     * @param numRows The number of rows.
     * @param numCols The number of columns.
     */
    public HexagonalPanel(int numCells, int rowNumber, ReversiModel model) {
        setLayout(null); // Use absolute positioning
        this.numCells = numCells;
        this.rowNumber = rowNumber;
        this.model = model;
        this.constructRow();

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

    public void constructRow() {
        for (int i = 0; i < this.numCells; i += 1) {
            HexagonButton button = new HexagonButton("Button " + (i + 1) + " in row " + this.rowNumber);
            hexagonButtons.add(button);
            add(button);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int buttonSize = this.getWidth() / this.model.getNumRows(); // The size of each button
        int startX = (buttonSize / 2) * Math.abs(this.rowNumber);
        int startY = buttonSize * (((this.model.getNumRows() - 1) / 2) - Math.abs(this.rowNumber));

        for (int i = 0; i < this.numCells; i += 1) {
            System.out.println("size: " + buttonSize);
            System.out.println("x: " + startX + " y: " + startY + "");
            HexagonButton button = hexagonButtons.get(i);
            button.setBounds(startX, startY, buttonSize, buttonSize);
            startX += (buttonSize / 2);

        }
    }

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
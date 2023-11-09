package cs3500.reversi.visualview;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a panel of hexagonal buttons.
 */
public class HexagonalPanel extends JPanel {


    private HexagonButton[][] hexagonButtons;

    /**
     * Constructs a new HexagonalPanel with the given number of rows and columns.
     *
     * @param numRows The number of rows.
     * @param numCols The number of columns.
     */
    public HexagonalPanel(int numRows, int numCols) {
        setLayout(null); // Use absolute positioning

        hexagonButtons = new HexagonButton[numRows][numCols];

        // Create and add hexagonal buttons
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                HexagonButton button = new HexagonButton("Button " + (row * numCols + col + 1));
                hexagonButtons[row][col] = button;
                add(button);
            }
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int numRows = hexagonButtons.length;
        int numCols = hexagonButtons[0].length;
        int buttonSize = Math.min(getWidth() / (numCols * 3), getHeight() / (numRows * 3)); // The size of each button
        int startX = getWidth() / 2 - (int) (numCols * 1.5 * buttonSize); 
        int startY = getHeight() / 2 - (int) (numRows * Math.sqrt(3) * buttonSize / 2);

        // Set the bounds of each button
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                HexagonButton button = hexagonButtons[row][col];
                int x = startX + (int) (col * 3 * buttonSize);
                int y = startY + (int) (row * Math.sqrt(3) * buttonSize);
                button.setBounds(x, y, buttonSize, buttonSize);
            }
        }
    }
}
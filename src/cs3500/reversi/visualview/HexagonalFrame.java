package cs3500.reversi.visualview;

import javax.swing.*;

import cs3500.reversi.model.ReversiModel;

/**
 * Represents a frame of hexagonal buttons.
 */
public class HexagonalFrame extends JFrame {

    ReversiModel model;

    /**
     * Constructs a new HexagonalFrame.
     */
    public HexagonalFrame() {
        setTitle("2 Player Reversi Game"); // Set the title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the program when the frame is closed
        setSize(5000, 5000); // Set the size of the frame
        setLocationRelativeTo(null); // Center the frame

        // HexagonalPanel panel = new HexagonalPanel(3, 3); // Create a panel of
        // hexagonal buttons
        // add(panel);

        setVisible(true); // Make the frame visible
    }

    public HexagonalFrame(ReversiModel model) {
        this.model = model;
        setTitle("2 Player Reversi Game"); // Set the title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the program when the frame is closed
        setSize(1000, 1000); // Set the size of the frame
        setLocationRelativeTo(null); // Center the frame
        setVisible(true); // Make the frame visible

        this.constructVisualBoard();
    }

    private void constructVisualBoard() {
        int maxRowNumber = (this.model.getNumRows() - 1) / 2;

        for (int i = -maxRowNumber; i < maxRowNumber; i += 1) {
            System.out.println("yoyo");
            HexagonalPanel panel = new HexagonalPanel(this.model.getNumRows() - Math.abs(i),
                    i, this.model); // Create a panel of hexagonal buttons
            add(panel);
        }
    }

}

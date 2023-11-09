package cs3500.reversi.visualview;

import javax.swing.*;

/**
 * Represents a frame of hexagonal buttons.
 */
public class HexagonalFrame extends JFrame {

    /**
     * Constructs a new HexagonalFrame.
     */
    public HexagonalFrame() {
        setTitle("Experimenting"); // Set the title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the program when the frame is closed
        setSize(500, 500); // Set the size of the frame
        setLocationRelativeTo(null); // Center the frame

        HexagonalPanel panel = new HexagonalPanel(3, 3); // Create a panel of hexagonal buttons
        add(panel);

        setVisible(true); // Make the frame visible
    }

}

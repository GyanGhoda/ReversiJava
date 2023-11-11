package cs3500.reversi.visualview;

import java.awt.BorderLayout;

import javax.swing.*;

import cs3500.reversi.model.ReadOnlyReversiModel;
import cs3500.reversi.model.ReversiModel;

/**
 * Represents a frame of hexagonal buttons.
 */
public class HexagonalFrame extends JFrame implements ReversiVisualView {

    ReadOnlyReversiModel model;

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

    public HexagonalFrame(ReadOnlyReversiModel model) {
        this.model = model;
        setTitle("2 Player Reversi Game"); // Set the title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the program when the frame is closed
        setSize(800, 800); // Set the size of the frame
        setLocationRelativeTo(null); // Center the frame
        setVisible(true); // Make the frame visible

        this.render(); // Render the frame
    }

    public void render() {
        HexagonalPanel panel = new HexagonalPanel(this.model, 800, 800); // Create a panel of hexagonal buttons
        panel.setBounds(400, 400, 800, 800);
        add(panel, BorderLayout.CENTER);
    }

    public void makeVisible() {
        this.setVisible(true);
    }
}

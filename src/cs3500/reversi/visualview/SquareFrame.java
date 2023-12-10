package cs3500.reversi.visualview;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import cs3500.reversi.controller.PlayerActionFeatures;
import cs3500.reversi.model.ReadOnlyReversiModel;
import cs3500.reversi.model.ReversiModel;

/**
 * Represents a frame of square buttons.
 */
public class SquareFrame extends JFrame implements ReversiVisualView {
    private final SquarePanel panel;

    /**
     * Constructs a frame of square buttons.
     *
     * @param model the model to render
     */
    public SquareFrame(ReadOnlyReversiModel model) {
        setTitle("2 Player Reversi Game"); // Set the title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the program when the frame is closed
        setSize(800, 800); // Set the size of the frame
        setLocationRelativeTo(null); // Center the frame
        setVisible(true); // Make the frame visible

        this.panel = new SquarePanel(model, 800, 800);
        this.render(); // Render the frame
    }

    /**
     * Constructs a frame of square buttons.
     *
     * @param model the model to render
     */
    public SquareFrame(ReversiModel model) {
        setTitle("2 Player Reversi Game"); // Set the title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the program when the frame is closed
        setSize(800, 800); // Set the size of the frame
        setLocationRelativeTo(null); // Center the frame
        setVisible(true); // Make the frame visible

        this.panel = new SquarePanel(model, 800, 800);
        this.render(); // Render the frame
    }

    /**
     * Renders the frame.
     */
    private void render() {
        this.panel.setBounds(400, 400, 800, 800);
        add(panel, BorderLayout.CENTER);
    }

    /**
     * Makes the frame visible.
     */
    @Override
    public void makeVisible() {
        this.setVisible(true);
    }

    /**
     * Refreshes the view.
     *
     * @param currentTurn - whether or not it is the current player's turn
     */
    @Override
    public void refresh(boolean currentTurn) {
        this.panel.refresh(currentTurn);
    }

    /**
     * Sets up the features of the view.
     *
     * @param features - the features to set up
     */
    @Override
    public void setUpFeatures(PlayerActionFeatures features) {
        this.panel.setUpFeatures(features);
    }
}

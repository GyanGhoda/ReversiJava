package cs3500.reversi.visualview;

import javax.swing.*;

import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.PositionAxial;
import cs3500.reversi.model.ReadOnlyReversiModel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.*;
import java.util.HashMap;

/**
 * Represents a panel of hexagonal buttons.
 */
public class HexagonalPanel extends JPanel implements MouseListener {
    HashMap<PositionAxial, HexagonSpace> hexagonButtons = new HashMap<PositionAxial, HexagonSpace>();
    ReadOnlyReversiModel model;
    int width;
    int height;

    /**
     * Constructs a new HexagonalPanel with the given number of rows and columns.
     *
     * @param numRows The number of rows.
     * @param numCols The number of columns.
     */
    public HexagonalPanel(ReadOnlyReversiModel model, int width, int height) {
        setLayout(null); // Use absolute positioning
        if (model == null) {
            throw new IllegalArgumentException("Model cannot be null");
        }
        this.model = model;
        this.width = width;
        this.height = height;
        addMouseListener(this);

        this.initializeHexagons();
    }

    /**
     * Initializes the hexagons in the panel.
     */
    private void initializeHexagons() {
        int distance = Math.min(this.width, this.height) / this.model.getNumRows();
        double buttonSize = (distance / Math.sqrt(3));

        // calculate the middle row of the game board
        int middleY = (this.model.getNumRows() - 1) / 2;

        // initialize starting q and s coordinates for the current row
        int currentRowStartingQ = 0;
        int currentRowStartingS = middleY;

        // initialize r coordinate for current row
        int currentR = -middleY;

        for (int rowsMade = 0; rowsMade < this.model.getNumRows(); rowsMade += 1) {

            // initialize q coordinate for current position
            int currentQ = currentRowStartingQ;

            double startingX = (((buttonSize * Math.sqrt(3)) / 2) * Math.abs(currentR))
                    + (((int) (buttonSize * Math.sqrt(3))) / 2);
            double startingY = ((buttonSize * 3) / 2) * (rowsMade + 1);

            for (int currentS = currentRowStartingS; currentS >= currentRowStartingQ; currentS -= 1) {
                // create a new position for the current hexagon
                PositionAxial posn = new PositionAxial(currentQ, currentR, currentS);

                System.out.println("Q: " + posn.getQ() + " R: " + posn.getR() + " S: " + posn.getS());

                // create a new hexagon button
                HexagonSpace hexagon = new HexagonSpace(buttonSize, startingX, startingY, this.model.getCellAt(posn));

                // create empty cell and add it to the board at the current poisiton
                hexagonButtons.put(posn, hexagon);

                // move the hexagon to the correct position
                hexagon.moveTo(startingX, startingY);

                // move to the next set of coordinates for the next hexagon
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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.darkGray);
        g2d.fill(new Rectangle(this.width, this.height));

        for (HexagonSpace hexagon : hexagonButtons.values()) {
            g2d.setColor(hexagon.getColor());
            g2d.fill(hexagon);
            g2d.setColor(Color.BLACK);
            g2d.draw(hexagon);

            hexagon.drawSpaceOwner(g2d);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        for (HashMap.Entry<PositionAxial, HexagonSpace> entry : hexagonButtons.entrySet()) {
            HexagonSpace hexagon = entry.getValue();
            if (hexagon.contains(mouseX, mouseY)) {
                System.out.println("Clicked on hexagon at:\nQ: " + entry.getKey().getQ() + "\nR: "
                        + entry.getKey().getR() + "\nS: " + entry.getKey().getS());
                toggleHexagonColor(hexagon);
            } else {
                hexagon.setState(false);
            }
        }
        repaint();
    }

    private void toggleHexagonColor(HexagonSpace hexagon) {
        hexagon.setState(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
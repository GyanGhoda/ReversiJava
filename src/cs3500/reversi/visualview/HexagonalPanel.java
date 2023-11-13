package cs3500.reversi.visualview;

import javax.swing.*;

import cs3500.reversi.controller.Features;
import cs3500.reversi.controller.ReversiFeatures;
import cs3500.reversi.model.PositionAxial;
import cs3500.reversi.model.ReadOnlyReversiModel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.*;
import java.awt.Taskbar.Feature;
import java.util.HashMap;

/**
 * Represents a panel of hexagonal buttons.
 */
public class HexagonalPanel extends JPanel implements ReversiPanel {
    private final HashMap<PositionAxial, HexagonSpace> hexagonButtons = new HashMap<PositionAxial, HexagonSpace>();
    private final ReadOnlyReversiModel model;
    private final int width;
    private final int height;
    private final Features features;

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
        this.features = new ReversiFeatures();

        addMouseListener(new MouseListenerReversi());
        addKeyListener(new KeyListenerReversi());

        setFocusable(true);
        requestFocusInWindow();

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

    private class MouseListenerReversi implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            int mouseX = e.getX();
            int mouseY = e.getY();

            for (HashMap.Entry<PositionAxial, HexagonSpace> entry : hexagonButtons.entrySet()) {
                HexagonSpace hexagon = entry.getValue();
                if (hexagon.contains(mouseX, mouseY) && !hexagon.getState()) {
                    System.out.println("Clicked on hexagon at:\nQ: " + entry.getKey().getQ() + "\nR: "
                            + entry.getKey().getR() + "\nS: " + entry.getKey().getS());
                    hexagon.setState(!hexagon.getState());
                } else {
                    hexagon.setState(false);
                }
            }
            repaint();
        }

        // Other mouse event methods...
        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub
        }
    }

    private class KeyListenerReversi implements KeyListener {

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_M) {
                for (HashMap.Entry<PositionAxial, HexagonSpace> entry : hexagonButtons.entrySet()) {
                    HexagonSpace hexagon = entry.getValue();
                    if (hexagon.getState()) {
                        features.moveToCoordinate(entry.getKey());
                    }
                }
            }

            if (e.getKeyCode() == KeyEvent.VK_P) {
                features.passTurn();
            }

            repaint();
        }

        @Override
        public void keyTyped(KeyEvent e) {
            // TODO Auto-generated method stub
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub
        }

    }
}
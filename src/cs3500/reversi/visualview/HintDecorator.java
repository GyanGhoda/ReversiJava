package cs3500.reversi.visualview;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import cs3500.reversi.controller.PlayerActionFeatures;

public class HintDecorator extends JPanel implements ReversiPanel {
  private final HexagonalPanel decoratedPanel;
  private boolean showHints;

  public HintDecorator(HexagonalPanel panel) {
    this.decoratedPanel = panel;
    this.showHints = false;
    this.setLayout(new BorderLayout());
    this.add(decoratedPanel, BorderLayout.CENTER);

    this.setFocusable(true);
    this.requestFocusInWindow();
    addKeyListener(new KeyListenerReversi());
    addComponentListener(new ComponentListenerReversi());
    addMouseListener(new MouseListenerReversi());
  }

  public void toggleHints() {
    showHints = !showHints;
  }

  @Override
  public void resizeComponent() {
    this.decoratedPanel.resizeComponent();
    refresh();
  }

  @Override
  public void mouseClickUpdateView(int mouseX, int mouseY) {
    this.decoratedPanel.mouseClickUpdateView(mouseX, mouseY);
    refresh();
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);

    if (showHints) {
      System.out.println("hey");
      FontMetrics fm = g.getFontMetrics();
      int score = this.decoratedPanel.getScore();
      double centerX = this.decoratedPanel.getCurrentX() - fm.stringWidth(Integer.toString(score));
      double centerY = this.decoratedPanel.getCurrentY() + (double) fm.getHeight() / 4;
      int fontSize = Math.min(this.decoratedPanel.getHeight(), this.decoratedPanel.getWidth()) / 30;
      g.setFont(new Font("Serif", Font.BOLD, fontSize));
      g.drawString(Integer.toString(score), (int) centerX, (int) centerY);
    }
  }

  protected void refresh() {
    this.repaint();
  }

  @Override
  public void setUpFeatures(PlayerActionFeatures features) {
    this.decoratedPanel.setUpFeatures(features);
  }

  @Override
  public void refresh(boolean currentTurn) {
    this.decoratedPanel.refresh(currentTurn);
    this.repaint();
  }

  @Override
  public double getCurrentX() {
    return this.decoratedPanel.getCurrentX();
  }

  @Override
  public double getCurrentY() {
    return this.decoratedPanel.getCurrentY();
  }

  @Override
  public int getScore() {
    return this.decoratedPanel.getScore();
  }

  public void keyClickUpdateView(int keyCode) {
    this.decoratedPanel.keyClickUpdateView(keyCode);
  }

  /**
   * Updates the panel according to key actions.
   */
  private class KeyListenerReversi implements KeyListener {

    /**
     * Handles key presses.
     *
     * @param e The key event.
     */
    @Override
    public void keyPressed(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_H) {
        toggleHints();
        refresh();
      }
      else {
        keyClickUpdateView(e.getKeyCode());
        refresh();
      }
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

  private class ComponentListenerReversi implements ComponentListener {

    @Override
    public void componentResized(ComponentEvent e) {
      resizeComponent();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
      // TODO Auto-generated method stub
    }

    @Override
    public void componentShown(ComponentEvent e) {
      // TODO Auto-generated method stub
    }

    @Override
    public void componentHidden(ComponentEvent e) {
      // TODO Auto-generated method stub
    }
  }

  /**
   * Updates the panel according to mouse actions.
   */
  private class MouseListenerReversi implements MouseListener {

    /**
     * Handles mouse clicks.
     *
     * @param e The mouse event.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
      mouseClickUpdateView(e.getX(), e.getY());
      refresh();
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
}
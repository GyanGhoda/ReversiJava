package cs3500.reversi.visualview;

import java.awt.*;

import javax.swing.JPanel;

import cs3500.reversi.controller.PlayerActionFeatures;

public class HintDecorator extends JPanel implements ReversiPanel {
  private HexagonalPanel decoratedPanel;
  private boolean showHints;

  public HintDecorator(HexagonalPanel panel) {
    this.decoratedPanel = panel;
    this.showHints = false;
    this.setLayout(new BorderLayout());
    this.add(decoratedPanel, BorderLayout.CENTER);
  }

  public void toggleHints() {
    showHints = !showHints;
  }

  @Override
  protected void paintComponent(Graphics g) {
    this.decoratedPanel.paintComponent(g);

    if (showHints) {
      FontMetrics fm = g.getFontMetrics();
      int score = this.decoratedPanel.getScore();
      double centerX = this.decoratedPanel.getCurrentX() - fm.stringWidth(Integer.toString(score));
      double centerY = this.decoratedPanel.getCurrentY() + (double) fm.getHeight() / 4;
      g.setFont(new Font("Serif", Font.BOLD, (int) (this.decoratedPanel.getSize().getHeight() * this.decoratedPanel.getSize().getWidth() * 2 / 3)));
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
}

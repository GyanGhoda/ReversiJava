package cs3500.reversi.controller;

/**
 * Represents the features that the controller can use to update the view according to the model.
 */
public interface ModelStatusFeatures {

  /**
   * Notifies the controller to refresh the view.
   *
   * @param currentTurn - the current turn.
   */
  public void notifyToRefresh(String currentTurn);
}

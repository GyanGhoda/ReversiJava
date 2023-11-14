package cs3500.reversi;

import org.junit.Assert;
import org.junit.Test;
import cs3500.reversi.controller.CaptureMostPieces;
import cs3500.reversi.controller.PlayerType;
import cs3500.reversi.controller.ReversiStrategy;
import cs3500.reversi.model.BasicReversiModel;
import cs3500.reversi.model.BasicReversiModelMockTranscript;
import cs3500.reversi.model.PositionAxial;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.textualview.ReversiTextualView;
import cs3500.reversi.textualview.TextualView;

public class TestStrategies {

  @Test
  public void testCaptureMostStrategyTieStartGame() {

    BasicReversiModelMockTranscript model = new BasicReversiModelMockTranscript();

    ReversiStrategy strat = new CaptureMostPieces();

    PositionAxial stratPosn = strat.chooseMove(model, PlayerType.BLACK);

    Assert.assertEquals(model.getLog(), "getScoreForMove(Q: 1, R: -2, S: 1)\n" +
        "getScoreForMove(Q: -2, R: 1, S: 1)\n" + 
        "getScoreForMove(Q: 1, R: 1, S: -2)\n" + 
        "getScoreForMove(Q: -1, R: -1, S: 2)\n" + 
        "getScoreForMove(Q: 2, R: -1, S: -1)\n" +
        "getScoreForMove(Q: -1, R: 2, S: -1)\n");
    Assert.assertEquals(stratPosn, new PositionAxial(-1, -1, 2));
  }  
}

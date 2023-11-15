## Overview

This codebase implements a Reversi (Othello) game following the Model-View-Controller (MVC) architecture. The game logic, user interaction, and textual representation are separated into distinct components to promote modularity and ease of maintenance.

## Testing

The codebase includes a test suite inside the test/cs3500/reversi directory. The test suite includes test cases for the model, controller, and textual view components. No package level tests were included in the test suite even at package level due to no protected methods existing at the current time. 

## Quick Start

```
// Test that can be used to play the game for the time being. An example move is provided along with an example pass turn.
// More moves and passes can be played by the user in order to play a full game.
@Test
public void testModelExampleGame() {
    ReversiModel model = new BasicReversiModel(7);  // Create a BasicReversiModel with a board size of your choosing.
                                                    // A board size of 7 is used in this example.

    model.startGame(); // Start the game.

    model.addPieceToCoordinates(new PositionAxial(1, -2, 1)); // Add a piece to the board at the specified coordinates.
                                                              // The piece will be added to the cell at the specified
                                                              // coordinates.

    model.passTurn(); // Pass the turn to the next player.

    TextualView modelView = new ReversiTextualView(model); // Create a textual view of the model.

    System.out.println(modelView.toString()); // Print the textual view of the model to see the game.
}
```

## Key Components and Subcomponents

### Model (`src/cs3500/reversi/model`)
- `BasicReversiModel`: Implements game logic and state management. Methods include `addPieceToCoordinates` for making a move and `getCellAt` for retrieving the state of a specific cell.
- `ReversiModel`: Interface defining essential game model methods, excluding observation methods.
- 'BsaicReversiModelMockTranscript': Mock class used to test strategies.
- `GameCell`: Represents a cell on the game board, implements `Cell`, and adds game-specific properties.
- `CellType`: Enumeration of cell types (e.g., EMPTY, PLAYER).
- `PositionAxial`: Represents positions in an axial coordinate system.
- `Cell`: Represents a generic cell.
- 'ReadOnlyReversiModel': Interface that contains observation methods for the model.

### Textual View (`src/cs3500/reversi/textualview`)
- `ReversiTextualView`: Renders a textual view of the Reversi game. Implements `TextualView`.
- `TextualView`: Interface for textual views.

### Visual (GUI) View (`src/cs3500/reversi/visualview`)
- `HexagonalFrame`: Represents the frame of hexagonal buttons.
- `HexagonalPanel`: Represents the panel of hexagonal buttons.
- `HexagonSpace`: Represents a singular hexagon.
- `ReversiPanel`: Interface representing the panel for the game.
- `ReversiVisualView`: Interface representing the visual view for the game.

### Controller (`src/cs3500/reversi/controller`)
- `GamePlayer`: Manages players in the game, handling turns and interactions. Implements or extends `Player`.
- `Player`: Represents a player, encapsulating player-specific data and behaviors.
- `PlayerType`: Enumeration of player types (e.g., HUMAN, AI).
- 'Features': Interface representing the features that the controller can use to interact with the model.
- 'ReversiFeatures': Class that allows controller to execute methods interacting with the model.

### Strategies (`src/cs3500/reversi/strategies`)
- `AvoidCellsNextToCorner`: Strategy that avoids cells next to the corner and breaks ties by choosing the uppermost-leftmost move.
- `CaptureCellsInCorner`: Strategy that only focuses on capturing cells in the corner.
- `CaptureMostPieces`: Strategy that captures the most pieces and breaks ties by choosing the uppermost-leftmost move.
- `MinimizeNextOpponentMove`: Strategy that leaves opponent with no good moves.
- `ReversiStrategy`: Interface representing a strategy for playing Reversi.
- `TryTwoStrategies`: Chains strategies together to form strategies of varying sophistication.

### Test (`test/cs3500/reversi/`)
- `Examples`: Example objects for the model, controller, and textual view components.
- `TestModel`: Test cases for the model component.
- `TestController`: Test cases for the controller component.
- `TestTextualView`: Test cases for the textual view component.
- 'TestStrategies': Test cases for strategies.

Reversi.java: main class that instantiates a model, instantiates a view using that model, and tells the view to get started

## Source Organization

The source code is organized as follows:
- `src/cs3500/reversi/model`: Contains classes related to the game model.
- `src/cs3500/reversi/textualview`: Contains classes related to the game textual view.
- `src/cs3500/reversi/controller`: Contains classes related to the game controller.
- `src/cs3500/reversi/strategies`: Contains classes related to the strategies of gameplay.
- `src/cs3500/reversi/visualview`: Contains classes related to the game GUI.
- `src/cs3500/reversi/Reversi.java`: Main class.

## Changes for Part 2

## Extra credit: found in the strategies package
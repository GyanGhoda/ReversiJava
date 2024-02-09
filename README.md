Hexagonal Human vs Human:

<img src="https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExdHEzejNsYTFucHd1OXl5cHZzbjFqZWZ5ZzJnanFtNGt2dWFheHlnYiZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/TWwQI3MA8Es3RT5ckB/giphy.gif" alt="drawing" width="500"/>

Hexagonal Human vs AI:

<img src="https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExMm5xN20yZjl2b2wwdzJzNzIyc2J5cms3aDltcmw5cnBqb3owN2sydSZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/JGala05DYQm67IpavL/giphy.gif" alt="drawing" width="500"/>

Square Human vs Human:

<img src="https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExN3V0Y3I3aTVwY25qZXd1dG52OXg1eHB1ajBhY2tnc2g3ZnlvZTl3MyZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/MVcpXmC0eKn1MdavXR/giphy.gif" alt="drawing" width="500"/>

Square Human vs AI:

<img src="https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExY3VkcDMyN2J2bTVkeW1ycDhobGoyZXVzZWJjcXV2ZW1mZHB2aWwwcyZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/jFpfkPMWkR6ssid3Ft/giphy.gif" alt="drawing" width="500"/>

## Part 4 Extra Credit Features

Level 0: Hints were added for the hexagonal model. They can be completely
disabled in the command line by typing in "hintsoff" as the first argument.
This change was made in the Reversi.java class. If this argument isn't specified,
hints are turned on and can be toggled for each player by pressing the 'h' key.
The decorator pattern was used in order to make HintsDecorator.java. Several
observation methods were added in the ReversiPanel interface. No rendering code was
modified in HexagonalPanel.java. For HexagonalFrame the constructor was also edited
in order to accommodate HintsDecorator.

Level 1: We created a new GamePosition interface and a new position class called Position2D
in order to visualize 2-dimensional coordinates. The already existing PositionAxial also implements
this interface. This allowed us to create an abstract class called ABasicReversiModel that allowed
us to abstract all methods that weren't related to board initialization and move logic. A new model
for square reversi was created called BasicSquareReversiModel. They both coexist in our code at the same
time and do not need to be commented out. A square game can be created through the command line by
typing in "square" as the second argument if you specified hintsoff. If hintsoff wasn't included,
"square" must be the first argument. Immediately after this argument, you can specify the size length
of the board as an argument. It must be even and above 0. Tested in TestSquareModel.java.

Level 2: To implement the square-grid visual view, we created a SquareFrame class, which extends
JFrame and implements ReversiVisualView. We also created SquarePanel, which extends JPanel and implements
ReversiPanel. We also created SquareSpace, which represents a square on the grid and extends
Path2D.Double. Since SquareSpace and HexagonSpace share a lot of similar code, we created an abstract
class called AShape that they both extend. They coexist properly with the hexagonal view in our code.

Level 3: Due to the way we designed the GamePosition interface and how our views implement the same
interfaces, there were no changes to the controller needed to get it to work with a square reversi
game other than abstracting all the positions to GamePositions. Tested in TestController.java.

Level 4: Due to the way we designed the GamePosition interface and how our views implement the same
interfaces, there were no changes to the strategies needed to get them to work with a square reversi
game other than abstracting all the positions to GamePositions. Tested in TestStrategies.java.

## Changes for Part 3 from initial design:

No changes were made to the initial design. All other changes involved adding new classes, interfaces, and packages as per the assignment instructions. The details of those can be viewed below in the Key Components and Subcomponents section.

## Main class command line explanation:
Optionally, a board size can be entered as the first argument. It must be an integer that is odd and at least 3.
If no board size is chosen, a default size of 7 is assumed. After this, you must specify player one. Also optional
and next, you can enter 'human' for a human player and 'computer' for a computer player. If not entered, a two
human player game is created. If the computer player is chosen, you can then list strategies that you want the
computer to follow, as seen below.
Strategies:
- 'strategy1' - CaptureMostPieces
- 'strategy2' - AvoidCellsNextToCorner
- 'strategy3' - CaptureCellsInCorner
- 'strategy4' - MinimizeNextOpponentMove
Multiple strategies can be chosen by simply listing them after each other. The primary strategy will be the first one
entered, and so on. For instance, 'computer strategy2 strategy3 strategy1' represents an AI player that primarily attempts strategy2,
and it will default to strategy3 then strategy1 in that order. If no strategies are specified, an AI player following strategy1 will
be created. After this if player one was specified, player two must be specified using the same 'human' or 'computer' with strategies
arguments.

Examples:
- '' - two player game (two humans) with board size of 7
- '9' - two player game (two humans) with board size of 9
- 'human human' - two player game (two humans) with board size of 7
- '15 human human' - two player game (two humans) with board size of 15
- 'human computer strategy4 strategy3 strategy2 strategy1' - one human player and one computer player utilizing all strategies with board size 7
- 'computer strategy4 strategy3 strategy2 strategy1 computer strategy4 strategy3 strategy2 strategy1' - two computer players both utilizing all strategies with board size 7

## Extra credit:

### Strategies (`src/cs3500/reversi/strategies`)
- `AvoidCellsNextToCorner`: Strategy that avoids cells next to the corner and breaks ties by choosing the uppermost-leftmost move.
- `CaptureCellsInCorner`: Strategy that only focuses on capturing cells in the corner.
- `CaptureMostPieces`: Strategy that captures the most pieces and breaks ties by choosing the uppermost-leftmost move.
- `MinimizeNextOpponentMove`: Strategy that minimizes the effectiveness of opponents future moves.
- `ReversiStrategy`: Interface representing a strategy for playing Reversi.
- `TryTwoStrategies`: Chains strategies together to form strategies of varying sophistication.

## Changes for Part 2 from initial design:

### Model (`src/cs3500/reversi/model`)
- 'ReversiModel': Added four new observation methods to the interface: 'getBoardSize', 'doesCurrentPlayerHaveValidMovesPosn', 'doesCurrentPlayerHaveValidMoves', and 'getBoardCopy'.

### Controller (`src/cs3500/reversi/controller`)
- 'GamePlayer': Overrode 'equals' method to compare the player type only. Overrode hashCode as well.

No other changes were made to the initial design. All other changes involved adding new classes, interfaces, and packages as per the assignment instructions. The details of those can be viewed below in the Key Components and Subcomponents section.

## Overview

This codebase implements a Reversi (Othello) game following the Model-View-Controller (MVC) architecture. The game logic, user interaction, and textual representation are separated into distinct components to promote modularity and ease of maintenance.

## Testing

The codebase includes a test suite inside the test/cs3500/reversi directory. The test suite includes test cases for the model, controller, and textual view components. No package level tests were included in the test suite even at package level due to no protected methods existing at the current time. 

## Quick Start

Using the jar file provided in the root directory, you can run the jar file and play the game in its current state without a controller. The hexagons you click on will have their coordinates printed out onto the console and you can press "m" to move and "p" to pass. Both of these actions will also print to the console. Currently, without a controller, the keys do not do anything other than print out to the console.

## Key Components and Subcomponents

### Model (`src/cs3500/reversi/model`)
- `BasicReversiModel`: Implements game logic and state management. Methods include `addPieceToCoordinates` for making a move and `getCellAt` for retrieving the state of a specific cell.
- `ReversiModel`: Interface defining essential game model methods, excluding observation methods.
- 'BasicReversiModelMockTranscript': Mock class used to test strategies.
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
- 'HexagonPanelMock': Represents a mock of the hexagonal panel to test key and mouse events.
- `HexagonSpace`: Represents a singular hexagon.
- `ReversiPanel`: Interface representing the panel for the game.
- `ReversiVisualView`: Interface representing the visual view for the game.

### Controller (`src/cs3500/reversi/controller`)
- 'BasicReversiController': Controller for playing Reversi.
- 'ComputerPlayer': Represents AI computer players in a game of Reversi.
- 'HumanPlayer': Represents human players in a game of Reversi.
- 'MockController': Used for testing to make sure controller calls methods properly.
- 'ModelStatusFeatures': Represents the features that the controller can use to update the view according to the model.
- 'PlayerActionFeatures': Represents the features that the controller can use to interact with the model.
- `Player`: Represents a player, encapsulating player-specific data and behaviors.
- `PlayerType`: Enumeration of player types (e.g., HUMAN, AI).

### Strategies (`src/cs3500/reversi/strategies`)
- `AvoidCellsNextToCorner`: Strategy that avoids cells next to the corner and breaks ties by choosing the uppermost-leftmost move on the grid.
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
- 'TestVisualView': Test key events and mouse events in the visual view.

Reversi.java: main class that instantiates a model, instantiates a view using that model, and tells the view to get started

## Source Organization

The source code is organized as follows:
- `src/cs3500/reversi/model`: Contains classes related to the game model.
- `src/cs3500/reversi/textualview`: Contains classes related to the game textual view.
- `src/cs3500/reversi/controller`: Contains classes related to the game controller.
- `src/cs3500/reversi/strategies`: Contains classes related to the strategies of gameplay.
- `src/cs3500/reversi/visualview`: Contains classes related to the game GUI.
- `src/cs3500/reversi/Reversi.java`: Main class.

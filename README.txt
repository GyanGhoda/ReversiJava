## Overview

This codebase implements a Reversi (Othello) game following the Model-View-Controller (MVC) architecture. The game logic, user interaction, and textual representation are separated into distinct components to promote modularity and ease of maintenance.

## Quick Start


## Key Components and Subcomponents

### Model (`src/cs3500/reversi/model`)
- `BasicReversiModel`: Implements game logic and state management. Methods include `makeMove` for making a move and `getCellAt` for retrieving the state of a specific cell.
- `ReversiModel`: Interface defining essential game model methods.
- `GameCell`: Represents a cell on the game board, extends `Cell`, and adds game-specific properties.
- `CellType`: Enumeration of cell types (e.g., EMPTY, BLACK, WHITE).
- `PositionAxial`: Represents positions in an axial coordinate system.
- `Cell`: Represents a generic cell.

### View (`src/cs3500/reversi/textualview`)
- `ReversiTextualView`: Renders a textual view of the Reversi game. Extends or implements `TextualView`.
- `TextualView`: Interface or abstract class for textual views.

### Controller (`src/cs3500/reversi/controller`)
- `GamePlayer`: Manages players in the game, handling turns and interactions. Implements or extends `Player`.
- `Player`: Represents a player, encapsulating player-specific data and behaviors.
- `PlayerType`: Enumeration of player types (e.g., HUMAN, AI).

### Test (`src/cs3500/reversi/test`)
- `TestModel`: Test cases for the model component.
- `TestController`: Test cases for the controller component.
- `TestTextualView`: Test cases for the textual view component.

## Source Organization

The source code is organized as follows:
- `src/cs3500/reversi/model`: Contains classes related to the game model.
- `src/cs3500/reversi/view`: Contains classes related to the game view.
- `src/cs3500/reversi/controller`: Contains classes related to the game controller.
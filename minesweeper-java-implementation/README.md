# Java Minesweeper Implementation

## Overview
This project is an implementation of the classic Minesweeper game logic in Java. It focuses on the core game mechanics including bomb placement, hint generation, and gameplay functionality.

## Features
- Minefield creation from text input
- Hint calculation showing number of adjacent bombs
- Game state tracking (revealing tiles, handling bomb encounters)
- Visibility management of the game board

## How to Play
Minesweeper is a puzzle game where you need to clear a board containing hidden mines without detonating any of them. The numbers on revealed squares indicate how many mines are adjacent to that square.

For detailed rules, visit: https://cardgames.io/minesweeper/

## Technical Implementation
This implementation includes:
- 2D array representation of the game board
- Efficient neighbor-checking algorithms
- Status tracking for each cell (visible, hidden, flagged)
- Clean object-oriented design


## Project Structure
- `src/main/java/minesweeper/Minesweeper.java` - Core game implementation
- `minefields.txt` - Sample minefield configurations for testing

## Development
This was developed as part of Software Engineering coursework at the University of Salzburg, focusing on proper design principles and clean code practices.

## Technologies Used
- Java
- Maven build system

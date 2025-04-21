package minesweeper;

public class Minesweeper {
    // Game board representation
    private char[][] minefield;     // Stores bomb locations ('*') and empty cells
    private boolean[][] visibility;  // Tracks which cells are revealed
    private int rows;
    private int cols;
    
    public Minesweeper() {
        // Default constructor
    }
    
    // Initializes minefield from string input
    public boolean setMinefield(String s) {
        String[] lines = s.split("\n");
        rows = lines.length;
        cols = lines[0].length();
        
        // Validate minefield dimensions
        for (String line : lines) {
            if (line.length() != cols || rows > cols) {
                return false;
            }
        }
        
        minefield = new char[rows][cols];
        visibility = new boolean[rows][cols];
        
        // Create minefield from input
        for (int i = 0; i < rows; i++) {
            minefield[i] = lines[i].toCharArray();
        }
        return true;
    }
    
    // Counts total bombs in the field
    public int getNumberOfBombs() {
        if (minefield == null) {
            return -1;
        }
        int bombCount = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (minefield[i][j] == '*') {
                    bombCount++;
                }
            }
        }
        return bombCount;
    }
    
    // Returns number of bombs adjacent to this cell
    public int getHintAt(int row, int col) {
        if (minefield == null || row < 0 || col < 0 || row >= rows || col >= cols || minefield[row][col] == '*') {
            return -1;
        }
        
        int bombCount = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < rows && j >= 0 && j < cols && minefield[i][j] == '*') {
                    bombCount++;
                }
            }
        }
        return bombCount;
    }
    
    // Checks if a cell has been revealed
    public boolean isVisible(int row, int col) {
        if (row < 0 || col < 0 || row >= rows || col >= cols) {
            return false;
        }
        return visibility[row][col];
    }
    
    // Handles player clicking on a cell
    public boolean clickOn(int row, int col) {
        if (minefield == null || row < 0 || col < 0 || row >= rows || col >= cols) {
            return false;
        }
        if (minefield[row][col] == '*') {
            // Reveal all bombs when a bomb is clicked
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (minefield[i][j] == '*') {
                        visibility[i][j] = true;
                    }
                }
            }
            return false;
        } else {
            visibility[row][col] = true;
            return true;
        }
    }
    
    // Gets field height
    public int getHeight() {
        return rows;
    }
    
    // Gets field width
    public int getWidth() {
        return cols;
    }
}

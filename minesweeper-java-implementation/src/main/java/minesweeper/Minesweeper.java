package minesweeper;

public class Minesweeper {
    private char[][] minefield;
    private boolean[][] visibility;
    private int rows;
    private int cols;

    public Minesweeper() {
    }

    public boolean setMinefield(String s) {
        String[] lines = s.split("\n");
        rows = lines.length;
        cols = lines[0].length();
        
        for (String line : lines) {
            if (line.length() != cols || rows > cols) {
                return false;
            }
        }
        
        minefield = new char[rows][cols];
        visibility = new boolean[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            minefield[i] = lines[i].toCharArray();
        }
        return true;
    }

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

    public boolean isVisible(int row, int col) {
        if (row < 0 || col < 0 || row >= rows || col >= cols) {
            return false;
        }
        return visibility[row][col];
    }

    public boolean clickOn(int row, int col) {
        if (minefield == null || row < 0 || col < 0 || row >= rows || col >= cols) {
            return false;
        }
        if (minefield[row][col] == '*') {
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

    public int getHeight() {
        return rows;
    }

    public int getWidth() {
        return cols;
    }
}


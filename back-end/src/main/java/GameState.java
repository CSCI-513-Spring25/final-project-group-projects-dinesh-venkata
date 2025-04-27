
import java.util.Arrays;

public class GameState {

    private final Cell[] cells;
    private final String winner;

    private GameState(Cell[] cells, String winner) {
        this.cells = cells;
        this.winner = winner;
    }

    public static GameState forGame(Game game) {
        Cell[] cells = getCells(game);
        String winner = game.getWinner();
        return new GameState(cells, winner);
    }

    public Cell[] getCells() {
        return this.cells;
    }

    public String getWinner() {
        return this.winner;
    }

    @Override
    public String toString() {
        return """
                { 
                    "cells": %s,
                    "winner": %s 
                }
                """.formatted(
                    Arrays.toString(this.cells),
                    this.winner == null ? "null" : "\"" + this.winner + "\""
                );
    }

    private static Cell[] getCells(Game game) {
        Cell cells[] = new Cell[400];
        char[][] grid = game.getGrid();
        for (int x = 0; x <= 19; x++) {
            for (int y = 0; y <= 19; y++) {
                String text = "";
                if (grid[x][y] != Character.MIN_VALUE)
                    text = String.valueOf(grid[x][y]);
                cells[20 * x + y] = new Cell(x, y, text);
            }
        }
        return cells;
    }
}

class Cell {
    private final int x;
    private final int y;
    private final String text;

    Cell(int x, int y, String text) {
        this.x = x;
        this.y = y;
        this.text = text;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getText() {
        return this.text;
    }

    @Override
    public String toString() {
        return """
                {
                    "text": "%s",                    
                    "x": %d,
                    "y": %d 
                }
                """.formatted(this.text, this.x, this.y);
    }
}
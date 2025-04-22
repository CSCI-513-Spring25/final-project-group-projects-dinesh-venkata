import java.util.Arrays;

public class GameState {

    private final Cell[] cells;
    private GameState(Game game) {
        this.cells = getCells(game);  
    }

    public static GameState forGame(Game game) {
        // Cell[] cells = getCells(game);        
        //return new GameState(cells);
        return new GameState(game);
    }

    public Cell[] getCells() {
        return this.cells;
    }

    /**
     * toString() of GameState will return the string representing
     * the GameState in JSON format.
     */
    @Override
    public String toString() {
        return """
                { "cells": %s}
                """.formatted(Arrays.toString(this.cells));
    }

    private static Cell[] getCells(Game game) {
        Cell cells[] = new Cell[400];
        char[][] grid=game.getGrid();
        for (int x = 0; x <= 19; x++) {
            for (int y = 0; y <= 19; y++) {
                String text = "";
                if(grid[x][y]!=Character.MIN_VALUE)
                    text=String.valueOf(grid[x][y]);
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
                """.formatted(this.text,this.x, this.y);
    }
}
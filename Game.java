import java.util.Arrays;
import java.util.Random;

public class Game {
    private static char[][] grid = new char[20][20];
    private ColumbusShip ship;
    private Random random;
    PirateFactory slowPirateFactory;
    PirateFactory fasPirateFactory;

    public Game() {
        ship = new ColumbusShip();
        slowPirateFactory = new SlowPirateShipFactory();
        fasPirateFactory = new FastPirateShipFactory();
        random = new Random();
        initializeGrid();
    }

    public void updateGrid(int x, int y, char value) {
        grid[x][y] = value;
    }

    public char[][] initializeGrid() {
        // Clear grid
        for (char[] row : grid) {
            Arrays.fill(row, ' ');
        }
        
        // Place CC
        grid[ship.getX()][ship.getY()] = 'C';
        
        // Add islands and pirates
        addIslands();
        addPirateShips();
        
        return grid;
    }

    public static char[][] getGrid() {
        return grid;
    }

    public ColumbusShip getColumbusShip() {
        return ship;
    }

    public Game play(int keyEvent) {
        // Store old position
        int oldX = ship.getX();
        int oldY = ship.getY();
        
        // Move CC
        switch(keyEvent) {
            case 37: ship.moveWest(this); break;
            case 38: ship.moveNorth(this); break;
            case 39: ship.moveEast(this); break;
            case 40: ship.moveSouth(this); break;
        }
        
        // Update grid if position changed
        if (oldX != ship.getX() || oldY != ship.getY()) {
            grid[oldX][oldY] = ' ';
            grid[ship.getX()][ship.getY()] = 'C';
        }
        
        return this;
    }

    private void addPirateShips() {
        // Add 5 slow pirates
        addPirates(5, slowPirateFactory);
        // Add 5 fast pirates
        addPirates(5, fasPirateFactory);
    }

    private void addPirates(int count, PirateFactory factory) {
        while (count > 0) {
            int x = random.nextInt(20);
            int y = random.nextInt(20);
            if (grid[x][y] == ' ') {
                grid[x][y] = 'P';
                PirateShip pirate = factory.createPirateShip(x, y);
                ship.addObserver(pirate);
                count--;
            }
        }
    }

    private void addIslands() {
        int count = 8;
        while (count > 0) {
            int x = random.nextInt(20);
            int y = random.nextInt(20);
            if (grid[x][y] == ' ') {
                grid[x][y] = 'I';
                count--;
            }
        }
    }
}
import static org.junit.Assert.*;

import java.awt.Point;
import java.awt.geom.Point2D;

import org.junit.Test;
public class ColumbusShipTest {

    @Test
    public void moveEastTest(){
        Game game = new Game();
        game.addColumbusShip(10, 15);
        ColumbusShip ship = game.getColumbusShip();
        Point2D point=ship.moveEast(game);
        assertEquals(point,new Point2D.Float(10,16));
    }
    @Test
    public void moveEastWithObstacleTest(){
        Game game = new Game();
        game.addColumbusShip(10, 15);
        ColumbusShip ship = game.getColumbusShip();
        game.addIslands(10, 16);
        Point2D point = ship.moveEast(game);
        assertEquals(point, new Point2D.Float(10,15));
    }
    @Test
    public void moveEastWithWhirlPoolTest(){
        Game game = new Game();
        game.addColumbusShip(10,15);
        game.addWhirlpool(10,16);
        game.addWhirlpool(15, 2);
        ColumbusShip ship = game.getColumbusShip();
        Point2D point = ship.moveEast(game);
        assertNotEquals(point, new Point(10,15));
        assertNotEquals(point, new Point(10,17));
        assertNotEquals(point, new Point(9,16));
        assertNotEquals(point, new Point(11,16));
    }
    @Test
    public void columbusWinsTest(){
        Game game = new Game();
        char[][] grid = game.getGrid();
        grid[10][10] = 'T';
        game.addColumbusShip(10, 11);
        ColumbusShip ship = game.getColumbusShip();
        ship.moveWest(game);
        assertEquals(game.getWinner(),"Columbus");
    }
    @Test
    public void columbusEntersWhirlpoolWithObstaclesTest(){
        Game game = new Game();
        game.addColumbusShip(10,15);
        game.addWhirlpool(10, 14);
        game.addWhirlpool(15, 10);
        game.addIslands(16, 10);
        game.addIslands(14, 10);
        game.addIslands(15, 11);
        ColumbusShip ship = game.getColumbusShip();
        ship.moveWest(game);
        assertEquals(new Point2D.Float(ship.getX(), ship.getY()),new Point2D.Float(15,9));
    }
}

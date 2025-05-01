import static org.junit.Assert.*;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.InputMismatchException;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
public class FastPirateShipTest {
@Rule
  public final ExpectedException exception = ExpectedException.none();
    @Test
    public void movePirateShipTest(){
        Game game = Game.getInstance();
        game.addColumbusShip(10,12);
        PirateShip pirate = game.addPirateShips(8, 15,'P');
        ColumbusShip ship = game.getColumbusShip();
        ship.moveNorth(game);
        Point2D pirateLocation=pirate.getPirateLocation();        
        assertEquals(pirateLocation,new Point2D.Float(9,14));
    }
    @Test
    public void movePirateShipWithObstacleTest(){
        Game game = Game.getInstance();
        game.addColumbusShip(10,12);
        PirateShip pirate = game.addPirateShips(15,17,'P');
        ColumbusShip ship = game.getColumbusShip();
        game.addIslands(14,16);
        ship.moveEast(game);
        Point2D pirateLocation = pirate.getPirateLocation();
        assertNotEquals(pirateLocation,new Point2D.Float(14,16));
    }
    @Test
    public void createPirateShipTest(){
        Game game = Game.getInstance();
        exception.expect(InputMismatchException.class);        
        game.addPirateShips(20, 2,'P');
    }
    @Test
    public void avoidPirateShipCollisionTest(){
        Game game = Game.getInstance();
        game.addColumbusShip(8,10);
        PirateShip pirate1 = game.addPirateShips(10,10,'P');
        PirateShip pirate2 = game.addPirateShips(10,11,'P');
        ColumbusShip ship = game.getColumbusShip();
        ship.moveNorth(game);
        Point2D pirate1Location = pirate1.getPirateLocation();
        Point2D pirate2Location = pirate2.getPirateLocation();
        assertNotEquals(pirate1Location,pirate2Location);
    }
    @Test
    public void captureColumbusTest(){
        Game game = Game.getInstance();
        game.addColumbusShip(13,17);
        PirateShip pirateShip = game.addPirateShips(13, 16,'P');
        ColumbusShip ship = game.getColumbusShip();
        ship.moveSouth(game);
        assertEquals(game.getWinner(),"Pirate");
    }
    @Test
    public void pirateEntersWhirlpoolTest(){
        Game game = Game.getInstance();
        game.addColumbusShip(17,9);
        PirateShip pirate = game.addPirateShips(9, 15, 'P');
        game.addWhirlpool(10, 16);
        ColumbusShip ship = game.getColumbusShip();
        ship.moveWest(game);
        Point2D pirateLocation = pirate.getPirateLocation();
        assertNotEquals(new Point2D.Float(10,16),pirateLocation);
        assertNotEquals(new Point2D.Float(11,16),pirateLocation);
        assertNotEquals(new Point2D.Float(12,16),pirateLocation);
        assertNotEquals(new Point2D.Float(10,17),pirateLocation);
        assertNotEquals(new Point2D.Float(10,15),pirateLocation);
    }
    @Test
    public void pirateEntersSeaCreatureTest(){
        Game game = Game.getInstance();
        game.addColumbusShip(2,19);
        PirateShip pirate = game.addPirateShips(2, 8,'P');
        game.addCreatures(2,9);
        ColumbusShip ship = game.getColumbusShip();
        ship.moveEast(game);
        ship.moveEast(game);
        List<PirateShip> pirateShips = game.getPirateShips();
        assertEquals(-1,pirateShips.indexOf(pirate));
    }
    @Test
    public void pirateEntersWhirlpoolWithObstaclesTest(){
        Game game = Game.getInstance();
        game.addColumbusShip(17,9);
        PirateShip pirate = game.addPirateShips(9, 15, 'P');
        game.addWhirlpool(10, 14);
        game.addWhirlpool(15, 10);
        game.addIslands(16, 10);
        game.addIslands(14, 10);
        game.addIslands(15, 11);
        ColumbusShip ship = game.getColumbusShip();
        ship.moveWest(game);
        Point2D pirateLocation = pirate.getPirateLocation();
        assertEquals(pirateLocation,new Point2D.Float(15,9));
    }
}

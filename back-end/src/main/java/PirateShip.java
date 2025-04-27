
import java.awt.geom.Point2D;
import java.util.Observer;
// Strategy interface
interface PirateShip extends Observer, VisiteeInterface{
    // strategy method.
    void movePirateShip(char[][] oceanGrid,ColumbusShip ship,Game game);
    Point2D getPirateLocation();
}

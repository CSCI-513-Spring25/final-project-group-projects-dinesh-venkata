
import java.awt.geom.Point2D;
import java.util.Observer;
interface PirateShip extends Observer, VisiteeInterface{
    void movePirateShip(char[][] oceanGrid,ColumbusShip ship,Game game);
    Point2D getPirateLocation();
}

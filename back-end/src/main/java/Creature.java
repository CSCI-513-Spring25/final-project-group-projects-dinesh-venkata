import java.awt.geom.Point2D;

interface Creature {
    void move(Game game);
    Point2D getLocation();
}

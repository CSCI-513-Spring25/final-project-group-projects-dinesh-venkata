import java.awt.geom.Point2D;
// Component Class in Composite design pattern
interface Creature {
    void move(Game game);
    Point2D getLocation();
}
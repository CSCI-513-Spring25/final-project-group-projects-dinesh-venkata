import java.awt.geom.Point2D;

public class ConcretePirateShipFactory extends PirateFactory{
    @Override
    public PirateShip createPirateShip(int x, int y,char type) {
        // TODO Auto-generated method stub
        if(type=='P')return new PirateShip(new FastPirateShipStrategy(),new Point2D.Float(x, y)); // If user selected Fast Pirate Ship
        else if (type=='Q')return new PirateShip(new SlowPirateShipStrategy(),new Point2D.Float(x, y)); // If user selected Slow Pirate Ship
        return null;
    }
}

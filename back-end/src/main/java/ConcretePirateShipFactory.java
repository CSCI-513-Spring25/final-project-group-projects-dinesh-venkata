
public class ConcretePirateShipFactory extends PirateFactory{
    @Override
    public PirateShip createPirateShip(int x, int y,char type) {
        // TODO Auto-generated method stub
        if(type=='P')return new FastPirateShip(x, y); // If user selected Fast Pirate Ship
        else if (type=='Q')return new SlowPirateShip(x, y); // If user selected Slow Pirate Ship
        return null;
    }
}

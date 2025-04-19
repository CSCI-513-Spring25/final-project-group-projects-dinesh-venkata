
public class ConcretePirateShipFactory extends PirateFactory{
    @Override
    PirateShip createPirateShip(int x, int y,char type) {
        // TODO Auto-generated method stub
        if(type=='P')return new FastPirateShip(x, y);
        else if (type=='Q')return new SlowPirateShip(x, y);
        return null;
    }
}

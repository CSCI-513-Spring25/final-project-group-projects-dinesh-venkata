public class SlowPirateShipFactory extends PirateFactory{
    @Override
    PirateShip createPirateShip(int x, int y) {
        // TODO Auto-generated method stub
        return new SlowPirateShip(x,y);
    }
}

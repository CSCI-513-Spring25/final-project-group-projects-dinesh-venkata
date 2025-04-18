public class FastPirateShipFactory extends PirateFactory{

    @Override
    PirateShip createPirateShip(int x,int y) {
        // TODO Auto-generated method stub
        return new FastPirateShip(x, y);
    }
}

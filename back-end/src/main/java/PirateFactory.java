abstract class PirateFactory{
    PirateShip getNewPirateShip(int x,int y){
        return createPirateShip(x,y);
    }
    abstract PirateShip createPirateShip(int x,int y);
}

abstract class PirateFactory{
    PirateShip getNewPirateShip(int x,int y,char type){
        return createPirateShip(x,y,type);
    }
    abstract PirateShip createPirateShip(int x,int y,char type);
}
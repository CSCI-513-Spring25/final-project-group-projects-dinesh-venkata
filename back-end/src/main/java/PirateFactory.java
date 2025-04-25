
abstract class PirateFactory{
    PirateShip getNewPirateShip(int x,int y,char type,Game game){
        PirateShip pirate = createPirateShip(x, y, type);
        game.getColumbusShip().addObserver(pirate);
        game.getPirateShips().add(pirate);
        return createPirateShip(x,y,type);
    }
    abstract PirateShip createPirateShip(int x,int y,char type);
}
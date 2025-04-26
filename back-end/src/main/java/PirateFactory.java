
abstract class PirateFactory{
    public PirateShip getNewPirateShip(int x,int y,char type,Game game){
        PirateShip pirate = createPirateShip(x, y, type);
        game.getColumbusShip().addObserver(pirate);
        game.getPirateShips().add(pirate);
        return pirate;
    }
    public abstract PirateShip createPirateShip(int x,int y,char type);
}
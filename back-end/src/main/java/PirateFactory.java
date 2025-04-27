// Abstract class for Factory Method pattern
abstract class PirateFactory{
    public PirateShip getNewPirateShip(int x,int y,char type,Game game){
        PirateShip pirate = createPirateShip(x, y, type); 
        game.getColumbusShip().addObserver(pirate);// subscribe to CC location
        game.getPirateShips().add(pirate);
        return pirate;
    }
    // Abstract method which concrete factory classes will implement
    public abstract PirateShip createPirateShip(int x,int y,char type);
}
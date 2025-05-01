
import java.awt.geom.Point2D;
import java.util.Observable;
import java.util.Observer;
// Concrete Pirate class
class PirateShip implements Observer, VisiteeInterface{
    PirateStrategy strategy;
    Point2D pirateLocation;
    // change Pirate ship strategy
    public void setStrategy(PirateStrategy strategy){
        this.strategy=strategy;
    }
    public void setPirateLocation(Point2D pirateLocation){
        this.pirateLocation=pirateLocation;
    }
    public PirateShip(PirateStrategy strategy, Point2D location){
        this.strategy=strategy;this.pirateLocation=location;
    }
    // getter for pirate location
    Point2D getPirateLocation(){
        return pirateLocation;
    }
    public PirateStrategy getPirateStrategy(){
        return strategy;
    }
    // Once CC location is updated, it notifies Pirate Ship by calling this method
    @Override
    public void update(Observable ship,Object game){
        if(ship instanceof ColumbusShip){
            ColumbusShip columbusShip=(ColumbusShip)ship;
            strategy.move(Game.getGrid(),columbusShip,(Game)game, this);// update location of Pirate Ship
        }
    }
    // method inherited from VisiteeInterface to call visit method of Visitor
	@Override
    public boolean accept(VisitorInterface shark, Game game){
        return shark.visit(this,game);
    }
}

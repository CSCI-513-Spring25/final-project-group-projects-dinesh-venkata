
import java.util.Observable;
import java.util.Observer;
import java.awt.geom.Point2D;
// Concrete Strategy Pirate Ship that chases CC
class FastPirateShip implements PirateShip{
    private Point2D pirateLocation;// stores the current location of Pirate ship
    public FastPirateShip(int x,int y){
        pirateLocation=new Point2D.Float(x,y);
    }
	// Once CC location is updated, it notifies Pirate Ship by calling this method
    @Override
    public void update(Observable ship,Object game){
        if(ship instanceof ColumbusShip){
            ColumbusShip columbusShip=(ColumbusShip)ship;
            movePirateShip(Game.getGrid(),columbusShip,(Game)game);// update location of Pirate Ship
        }
    }
	public Point2D getPirateLocation(){
		return this.pirateLocation;
	}  
	// Method to handle pirate collision with CC/Shark/ enters whirlpool
    public void movePirateShip(char[][] oceanGrid,ColumbusShip ship,Game game){
		if(game.getWinner()!=null)return;
        int px = (int)pirateLocation.getX();
		int py = (int)pirateLocation.getY();
		int cx = (int)ship.getX();
		int cy = (int)ship.getY();		
		oceanGrid[px][py]=Character.MIN_VALUE;
		//If Columbus Ship is below the Pirate ship
		if(px-cx<0 &&game.noObstaclesForPirate(px+1, py))px++;
		// If Columbus Ship is above Pirate ship
		else if(px-cx>0 && game.noObstaclesForPirate(px-1, py))
		    px--;
		//If Columbus ship is towards right side of the Pirate ship
		if(py-cy<0 && game.noObstaclesForPirate(px, py+1))
		    py++;		
		//If Columbus ship is towards left side of the Pirate Ship
		else if(py-cy>0 && game.noObstaclesForPirate(px, py-1))
		    py--;
		pirateLocation.setLocation(px, py);	
		pirateLocation=game.pirateColumbusCollisionCheck(px, py);// check if pirate collides with CC
		if(pirateLocation == null)oceanGrid[cx][cy]='C'; 
		if(pirateLocation!=null&&oceanGrid[px][py]=='W')// check if pirate enters whirlpool
			pirateLocation.setLocation(game.newRandomLocation(px, py));
		if(pirateLocation!=null)
		{
			oceanGrid[(int)pirateLocation.getX()][(int)pirateLocation.getY()]=(accept(game.getCreatures(),game))?'M':'P';// check if pirate collides with shark
		}
		
		return;
    }
	// method inherited from VisiteeInterface to call visit method of Visitor
	@Override
    public boolean accept(VisitorInterface shark, Game game){
        return shark.visit(this,game);
        }
}

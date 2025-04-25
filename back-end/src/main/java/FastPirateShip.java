
import java.util.Observable;
import java.util.Observer;
import java.awt.geom.Point2D;

class FastPirateShip implements PirateShip{
    private Point2D pirateLocation;
    public FastPirateShip(int x,int y){
        pirateLocation=new Point2D.Float(x,y);
    }
    @Override
    public void update(Observable ship,Object game){
        if(ship instanceof ColumbusShip){
            ColumbusShip columbusShip=(ColumbusShip)ship;
            movePirateShip(Game.getGrid(),columbusShip,(Game)game);
        }
    }
	public Point2D getPirateLocation(){
		return this.pirateLocation;
	}  
    public Point2D movePirateShip(char[][] oceanGrid,ColumbusShip ship,Game game){
		if(game.getWinner()!=null)return pirateLocation;
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
		pirateLocation=game.pirateColumbusCollisionCheck(px, py);
		if(pirateLocation!=null&&oceanGrid[px][py]=='W')
			pirateLocation.setLocation(game.newRandomLocation(px, py));
		if(pirateLocation!=null)
		{
			oceanGrid[(int)pirateLocation.getX()][(int)pirateLocation.getY()]=(accept(game.getCreatures(),game))?'M':'P';
		}		
		return pirateLocation;
    }
	@Override
    public boolean accept(VisitorInterface shark, Game game){
        return shark.visit(this,game);
        }
}

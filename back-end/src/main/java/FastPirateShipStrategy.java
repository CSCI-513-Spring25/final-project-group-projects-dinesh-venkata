import java.awt.geom.Point2D;

public class FastPirateShipStrategy implements PirateStrategy{
    @Override
    public void move(char[][] oceanGrid,ColumbusShip ship,Game game, PirateShip pirateShip) {
        // TODO Auto-generated method stub
        if(game.getWinner()!=null)return;
        Point2D pirateLocation = pirateShip.getPirateLocation();
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
		if(pirateLocation == null){
            oceanGrid[cx][cy]='C'; return;
        }
		if(pirateLocation!=null&&oceanGrid[px][py]=='W')// check if pirate enters whirlpool
			pirateLocation.setLocation(game.newRandomLocation(px, py));
        px=(int)pirateLocation.getX();py=(int)pirateLocation.getY();
        if(oceanGrid[px][py]=='H'){ // If Pirate ship goes to Pirate island, change strategy and move one more step           
            pirateShip.setPirateLocation(pirateLocation);
            pirateShip.setStrategy(new SlowPirateShipStrategy());
            pirateShip.getPirateStrategy().move(oceanGrid, ship, game, pirateShip);return;            
        }
		if(pirateLocation!=null)
		{
			oceanGrid[(int)pirateLocation.getX()][(int)pirateLocation.getY()]=(pirateShip.accept(game.getCreatures(),game))?'M':'P';// check if pirate collides with shark
		}
        pirateShip.setPirateLocation(pirateLocation);
		return;
    }
}

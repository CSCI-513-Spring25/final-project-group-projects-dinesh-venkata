import java.awt.geom.Point2D;
import java.util.Random;

public class SlowPirateShipStrategy implements PirateStrategy {
     // concrete strategy method for slow pirate ship. Slow pirate ship randomly moves in the ocean grid
    @Override
    public void move(char[][] oceanGrid,ColumbusShip ship,Game game, PirateShip pirateShip) {
        // TODO Auto-generated method stub
        if(game.getWinner()!=null)return;
        Point2D pirateLocation = pirateShip.getPirateLocation();
        Random random = new Random();
        int nx=(int)pirateLocation.getX();int ny=(int)pirateLocation.getY();
        pirateLocation=game.pirateColumbusCollisionCheck(nx, ny); // before pirate moves check if CC comes to current Pirate location and kill if necessary
        if(game.getWinner()!=null){
            game.updateGrid(nx, ny, 'Q');// if pirate captures CC update winner and stop game
            return;
        }
        if(pirateLocation==null){ // If pirate gets killed if CC moves to pirate location with a shield, remove pirate from game
            game.updateGrid(nx, ny, 'C');return;
        }
        int rDirection = random.nextInt(0,4);// slow pirate moves in random direction
        switch (rDirection)
        {
            case 0: pirateLocation = moveUp(oceanGrid,game,pirateLocation);break;
            case 1: pirateLocation = moveRight(oceanGrid,game,pirateLocation);break;
            case 2: pirateLocation = moveDown(oceanGrid,game,pirateLocation);break;
            case 3: pirateLocation = moveLeft(oceanGrid,game,pirateLocation);break;
        }
        nx=(int)pirateLocation.getX();ny=(int)pirateLocation.getY();
        pirateLocation=game.pirateColumbusCollisionCheck(nx, ny);// check if pirate collides with CC
        if(pirateLocation!=null&&oceanGrid[nx][ny]=='W')
			pirateLocation.setLocation(game.newRandomLocation(nx, ny));// check if pirate moves into Whirlpool
        nx=(int)pirateLocation.getX();ny=(int)pirateLocation.getY();
        if(oceanGrid[nx][ny]=='H'){ // If Pirate ship goes to Pirate island, change strategy and move one more step           
            pirateShip.setPirateLocation(pirateLocation);
            pirateShip.setStrategy(new FastPirateShipStrategy());
            pirateShip.getPirateStrategy().move(oceanGrid, ship, game, pirateShip);return;            
        }
        if(pirateLocation!=null)// check if pirate collides with shark
		{            
            oceanGrid[(int)pirateLocation.getX()][(int)pirateLocation.getY()]=pirateShip.accept(game.getCreatures(), game)?'M':'Q';
        }  
        pirateShip.setPirateLocation(pirateLocation);
        return;
    }
    public Point2D moveUp(char[][] oceanGrid,Game game, Point2D pirateLocation)
    {
        int px = (int)pirateLocation.getX();
		int py = (int)pirateLocation.getY();
        oceanGrid[px][py]=Character.MIN_VALUE;
        if(game.noObstaclesForPirate(px-1,py))px--;        
        return new Point2D.Float(px,py);
    }
    public Point2D moveRight(char[][] oceanGrid,Game game, Point2D pirateLocation)
    {
        int px = (int)pirateLocation.getX();
		int py = (int)pirateLocation.getY();
        oceanGrid[px][py]=Character.MIN_VALUE;
        if(game.noObstaclesForPirate(px,py+1))py++;
        return new Point2D.Float(px,py);
    }
    public Point2D moveDown(char[][] oceanGrid,Game game, Point2D pirateLocation)
    {
        int px = (int)pirateLocation.getX();
		int py = (int)pirateLocation.getY();
        oceanGrid[px][py]=Character.MIN_VALUE;
        if(game.noObstaclesForPirate(px+1,py))px++;
        return new Point2D.Float(px,py);
    }
    public Point2D moveLeft(char[][] oceanGrid,Game game, Point2D pirateLocation)
    {
        int px = (int)pirateLocation.getX();
		int py = (int)pirateLocation.getY();
        oceanGrid[px][py]=Character.MIN_VALUE;
        if(game.noObstaclesForPirate(px,py-1))py--;        
        return new Point2D.Float(px,py);
    }
}


import java.awt.geom.Point2D;
import java.util.Observable;
import java.util.Random;
// Concrete Strategy and observer of CC
class SlowPirateShip implements PirateShip{
    private Point2D pirateLocation;// temporarily stores pirate location
    public SlowPirateShip(int x,int y){
        pirateLocation=new Point2D.Float(x,y);
    }
    // This method is invoked whenever CC notifies observers about location changes
    @Override
    public void update(Observable ship, Object game) {
        // TODO Auto-generated method stub
        if(ship instanceof ColumbusShip){
            ColumbusShip columbusShip=(ColumbusShip)ship;
            movePirateShip(Game.getGrid(),columbusShip,(Game)game);
        }        
    }
    public Point2D getPirateLocation(){
        return this.pirateLocation;
    }
    // concrete strategy method for slow pirate ship. Slow pirate ship randomly moves in the ocean grid
    @Override
    public void movePirateShip(char[][] oceanGrid, ColumbusShip ship,Game game) {
        // TODO Auto-generated method stub
        if(game.getWinner()!=null)return;
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
            case 0: pirateLocation = moveUp(oceanGrid,game);break;
            case 1: pirateLocation = moveRight(oceanGrid,game);break;
            case 2: pirateLocation = moveDown(oceanGrid,game);break;
            case 3: pirateLocation = moveLeft(oceanGrid,game);break;
        }
        nx=(int)pirateLocation.getX();ny=(int)pirateLocation.getY();
        pirateLocation=game.pirateColumbusCollisionCheck(nx, ny);// check if pirate collides with CC
        if(pirateLocation!=null&&oceanGrid[nx][ny]=='W')
			pirateLocation.setLocation(game.newRandomLocation(nx, ny));// check if pirate moves into Whirlpool
        if(pirateLocation!=null)
		{
            // check if pirate collides with shark
            oceanGrid[(int)pirateLocation.getX()][(int)pirateLocation.getY()]=accept(game.getCreatures(), game)?'M':'Q';
        }        
        return;
    }
    public Point2D moveUp(char[][] oceanGrid,Game game)
    {
        int px = (int)pirateLocation.getX();
		int py = (int)pirateLocation.getY();
        oceanGrid[px][py]=Character.MIN_VALUE;
        if(game.noObstaclesForPirate(px-1,py))px--;        
        return new Point2D.Float(px,py);
    }
    public Point2D moveRight(char[][] oceanGrid,Game game)
    {
        int px = (int)pirateLocation.getX();
		int py = (int)pirateLocation.getY();
        oceanGrid[px][py]=Character.MIN_VALUE;
        if(game.noObstaclesForPirate(px,py+1))py++;
        return new Point2D.Float(px,py);
    }
    public Point2D moveDown(char[][] oceanGrid,Game game)
    {
        int px = (int)pirateLocation.getX();
		int py = (int)pirateLocation.getY();
        oceanGrid[px][py]=Character.MIN_VALUE;
        if(game.noObstaclesForPirate(px+1,py))px++;
        return new Point2D.Float(px,py);
    }
    public Point2D moveLeft(char[][] oceanGrid,Game game)
    {
        int px = (int)pirateLocation.getX();
		int py = (int)pirateLocation.getY();
        oceanGrid[px][py]=Character.MIN_VALUE;
        if(game.noObstaclesForPirate(px,py-1))py--;        
        return new Point2D.Float(px,py);
    }
    // Accept method used to invoke visit method of visitor(Creature container)
    @Override
    public boolean accept(VisitorInterface shark, Game game){
        return shark.visit(this, game);
        }
}
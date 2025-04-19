
import java.awt.geom.Point2D;
import java.util.Observable;
import java.util.Random;
class SlowPirateShip implements PirateShip{
    private Point2D pirateLocation;
    public SlowPirateShip(int x,int y){
        pirateLocation=new Point2D.Float(x,y);
    }
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
    @Override
    public Point2D movePirateShip(char[][] oceanGrid, ColumbusShip ship,Game game) {
        // TODO Auto-generated method stub
        if(game.getWinner()!=null)return pirateLocation;
        Random random = new Random();
        int rDirection = random.nextInt(0,4);
        switch (rDirection)
        {
            case 0: pirateLocation = moveUp(oceanGrid,game);break;
            case 1: pirateLocation = moveRight(oceanGrid,game);break;
            case 2: pirateLocation = moveDown(oceanGrid,game);break;
            case 3: pirateLocation = moveLeft(oceanGrid,game);break;
        }
        int nx=(int)pirateLocation.getX();int ny=(int)pirateLocation.getY();
        if(game.getColumbusShip().getX()==nx&&game.getColumbusShip().getY()==ny){
            game.setWinner("Pirate");
            game.setColumbusShip(null);
            System.out.println("Pirate captured Columbus");
        }
        if(oceanGrid[nx][ny]=='W'){
            pirateLocation = game.newRandomLocation(nx, ny);
        }
        oceanGrid[(int)pirateLocation.getX()][(int)pirateLocation.getY()]='Q';
        return pirateLocation;
    }
    public Point2D moveUp(char[][] oceanGrid,Game game)
    {
        int px = (int)pirateLocation.getX();
		int py = (int)pirateLocation.getY();
        oceanGrid[px][py]=Character.MIN_VALUE;
        if(px-1>=0 && oceanGrid[px-1][py]!='I'&&oceanGrid[px-1][py]!='T'&&oceanGrid[px-1][py]!='P'&&oceanGrid[px-1][py]!='Q')px--;        
        return new Point2D.Float(px,py);
    }
    public Point2D moveRight(char[][] oceanGrid,Game game)
    {
        int px = (int)pirateLocation.getX();
		int py = (int)pirateLocation.getY();
        oceanGrid[px][py]=Character.MIN_VALUE;
        if(py+1<20 && oceanGrid[px][py+1]!='I'&&oceanGrid[px][py+1]!='T'&&oceanGrid[px][py+1]!='P'&&oceanGrid[px][py+1]!='Q')py++;
        return new Point2D.Float(px,py);
    }
    public Point2D moveDown(char[][] oceanGrid,Game game)
    {
        int px = (int)pirateLocation.getX();
		int py = (int)pirateLocation.getY();
        oceanGrid[px][py]=Character.MIN_VALUE;
        if(px+1<20 && oceanGrid[px+1][py]!='I'&&oceanGrid[px+1][py]!='T'&&oceanGrid[px+1][py]!='P'&&oceanGrid[px+1][py]!='Q')px++;
        return new Point2D.Float(px,py);
    }
    public Point2D moveLeft(char[][] oceanGrid,Game game)
    {
        int px = (int)pirateLocation.getX();
		int py = (int)pirateLocation.getY();
        oceanGrid[px][py]=Character.MIN_VALUE;
        if(py-1>=0 && oceanGrid[px][py-1]!='I'&&oceanGrid[px][py-1]!='T'&&oceanGrid[px][py-1]!='P'&&oceanGrid[px][py-1]!='Q')py--;        
        return new Point2D.Float(px,py);
    }
}
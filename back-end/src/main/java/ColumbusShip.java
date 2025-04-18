import java.awt.geom.Point2D;
import java.util.Observable;
import java.util.Random;
public class ColumbusShip extends Observable{
    private Point2D coordinate;  
    private Random random;  
    public ColumbusShip(int x,int y){
        // random=new Random();
        // coordinate=new Point2D.Float(random.nextInt(0,10),random.nextInt(0,10));
        coordinate = new Point2D.Float(x,y);
    }
    public int getX(){
        return (int)coordinate.getX();
    }
    public int getY(){
        return (int)coordinate.getY();
    }
    private void setCoordinate(Point2D point,Game game){
        coordinate=point;
        char[][] grid=game.getGrid();
        int cx=(int)point.getX();int cy=(int)point.getY();
        if(grid[cx][cy]=='T'){
            game.setWinner("Columbus");return;
        }
        System.out.println("Columbus coordinates: X: "+cx+", Y: "+cy);
        if(grid[cx][cy]=='W'){
            coordinate = game.newRandomLocation(cx, cy);
            System.out.println("Entering WhirlPool");
        }
        grid[(int)coordinate.getX()][(int)coordinate.getY()]='C';        
        setChanged();
        notifyObservers(game);
    }
    public void moveEast(Game game){
        char[][] grid=game.getGrid();
        int xCoordinate = getX();
        int yCoordinate = getY(); 
        grid[xCoordinate][yCoordinate]=Character.MIN_VALUE;       
        if(yCoordinate+1<20&&game.noObstacles(xCoordinate, yCoordinate+1)){                                  
            setCoordinate(new Point2D.Float(xCoordinate,yCoordinate+1),game);            
        }
    }
    public void moveWest(Game game)
    {
        char[][] grid=game.getGrid();
        int xCoordinate = getX();
        int yCoordinate = getY();      
        grid[xCoordinate][yCoordinate]=Character.MIN_VALUE;  
        if(yCoordinate-1>=0 && game.noObstacles(xCoordinate, yCoordinate-1)){                     
            setCoordinate(new Point2D.Float(xCoordinate,yCoordinate-1),game);
        }
    }
    public void moveNorth(Game game){
        char[][] grid=game.getGrid();
        int xCoordinate = getX();
        int yCoordinate = getY();        
        grid[xCoordinate][yCoordinate]=Character.MIN_VALUE;
        if(xCoordinate-1>=0 && game.noObstacles(xCoordinate-1, yCoordinate)){           
            setCoordinate(new Point2D.Float(xCoordinate-1,yCoordinate),game);
        }        
    }
    public void moveSouth(Game game){
        char[][] grid=game.getGrid();
        int xCoordinate = getX();
        int yCoordinate = getY();   
        grid[xCoordinate][yCoordinate]=Character.MIN_VALUE;     
        if(xCoordinate+1<=19 && game.noObstacles(xCoordinate+1, yCoordinate)){           
            setCoordinate(new Point2D.Float(xCoordinate+1,yCoordinate),game);
        }
    }
}

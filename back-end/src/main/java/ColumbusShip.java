
import java.awt.geom.Point2D;
import java.util.Observable;
import java.util.Random;
public class ColumbusShip extends Observable{
    private Point2D coordinate;  
    private Random random;  
    public ColumbusShip(int x,int y){
        coordinate = new Point2D.Float(x,y);
    }
    public int getX(){
        return (int)coordinate.getX();
    }
    public int getY(){
        return (int)coordinate.getY();
    }
    private Point2D setCoordinate(Point2D point,Game game){
        coordinate=point;
        char[][] grid=game.getGrid();
        int cx=(int)point.getX();int cy=(int)point.getY();
        if(grid[cx][cy]=='T'){
            game.setWinner("Columbus");return coordinate;
        }
        System.out.println("Columbus coordinates: X: "+cx+", Y: "+cy);
        if(grid[cx][cy]=='W'){
            coordinate = game.newRandomLocation(cx, cy);
            System.out.println("Entering WhirlPool");
        }
        grid[(int)coordinate.getX()][(int)coordinate.getY()]='C';        
        setChanged();
        notifyObservers(game);
        return coordinate;
    }
    public Point2D moveEast(Game game){
        char[][] grid=game.getGrid();
        int xCoordinate = getX();
        int yCoordinate = getY();
        grid[xCoordinate][yCoordinate]=Character.MIN_VALUE;       
        if(yCoordinate+1<20&&game.noObstacles(xCoordinate, yCoordinate+1)){                                  
            setCoordinate(new Point2D.Float(xCoordinate,yCoordinate+1),game);            
        }
        return coordinate;
    }
    public Point2D moveWest(Game game)
    {
        char[][] grid=game.getGrid();
        int xCoordinate = getX();
        int yCoordinate = getY();      
        grid[xCoordinate][yCoordinate]=Character.MIN_VALUE;  
        if(yCoordinate-1>=0 && game.noObstacles(xCoordinate, yCoordinate-1)){                     
            setCoordinate(new Point2D.Float(xCoordinate,yCoordinate-1),game);
        }
        return coordinate;
    }
    public Point2D moveNorth(Game game){
        char[][] grid=game.getGrid();
        int xCoordinate = getX();
        int yCoordinate = getY();        
        grid[xCoordinate][yCoordinate]=Character.MIN_VALUE;
        if(xCoordinate-1>=0 && game.noObstacles(xCoordinate-1, yCoordinate)){           
            setCoordinate(new Point2D.Float(xCoordinate-1,yCoordinate),game);
        } 
        return coordinate;       
    }
    public Point2D moveSouth(Game game){
        char[][] grid=game.getGrid();
        int xCoordinate = getX();
        int yCoordinate = getY();   
        grid[xCoordinate][yCoordinate]=Character.MIN_VALUE;     
        if(xCoordinate+1<=19 && game.noObstacles(xCoordinate+1, yCoordinate)){           
            setCoordinate(new Point2D.Float(xCoordinate+1,yCoordinate),game);
        }
        return coordinate;
    }
}


import java.awt.geom.Point2D;
import java.util.Observable;
import java.util.Random;
public class ColumbusShip extends Observable implements Defense,VisiteeInterface{
    private Point2D coordinate;  
    private Random random;  
    private Defense shield;
    public ColumbusShip(int x,int y){
        coordinate = new Point2D.Float(x,y);
    }
    public Defense getDefense(){
        return shield;
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
            game.setWinner("Columbus");
            game.updateGrid(cx, cy, 'C');
            return coordinate;
        }
        System.out.println("Columbus coordinates: X: "+cx+", Y: "+cy);
        if(grid[cx][cy]=='W'){
            coordinate = game.newRandomLocation(cx, cy);
            System.out.println("Entering WhirlPool");
        }
        if(grid[cx][cy]=='S'){
            addShield();
        }
        boolean killed=accept(game.getCreatures(), game);
        grid[(int)coordinate.getX()][(int)coordinate.getY()]=killed?' ':'C';        
        if(killed){
            game.setWinner("Shark");return coordinate;
        }
        setChanged();
        notifyObservers(game);
        return coordinate;
    }
    public Point2D moveEast(Game game){
        char[][] grid=game.getGrid();
        int xCoordinate = getX();
        int yCoordinate = getY();
        grid[xCoordinate][yCoordinate]=Character.MIN_VALUE;       
        if(yCoordinate+1<20&&game.noObstacles(xCoordinate, yCoordinate+1))                    
            setCoordinate(new Point2D.Float(xCoordinate,yCoordinate+1),game); 
        else setCoordinate(coordinate, game);           
        return coordinate;
    }
    public Point2D moveWest(Game game)
    {
        char[][] grid=game.getGrid();
        int xCoordinate = getX();
        int yCoordinate = getY();      
        grid[xCoordinate][yCoordinate]=Character.MIN_VALUE;  
        if(yCoordinate-1>=0 && game.noObstacles(xCoordinate, yCoordinate-1))                     
            setCoordinate(new Point2D.Float(xCoordinate,yCoordinate-1),game);
        else setCoordinate(coordinate, game);
        return coordinate;
    }
    public Point2D moveNorth(Game game){
        char[][] grid=game.getGrid();
        int xCoordinate = getX();
        int yCoordinate = getY();        
        grid[xCoordinate][yCoordinate]=Character.MIN_VALUE;
        System.out.println("xCoordinate: "+ xCoordinate);
        if(xCoordinate-1>=0 && game.noObstacles(xCoordinate-1, yCoordinate))           
            setCoordinate(new Point2D.Float(xCoordinate-1,yCoordinate),game);
        else setCoordinate(coordinate, game);
        return coordinate;       
    }
    public Point2D moveSouth(Game game){
        char[][] grid=game.getGrid();
        int xCoordinate = getX();
        int yCoordinate = getY();   
        grid[xCoordinate][yCoordinate]=Character.MIN_VALUE;     
        if(xCoordinate+1<=19 && game.noObstacles(xCoordinate+1, yCoordinate))          
            setCoordinate(new Point2D.Float(xCoordinate+1,yCoordinate),game);
        else setCoordinate(coordinate, game);
        return coordinate;
    }
    @Override
    public void addShield() {
        // TODO Auto-generated method stub
        if(this.shield!=null)
            shield.addShield();
        else
            this.shield = new Shield();
    }
    @Override
    public void reduceShield() {
        // TODO Auto-generated method stub
        if(this.shield.getDefense()!=null)
            shield.reduceShield();
        else
            shield = null; 
    }
    @Override
    public boolean accept(VisitorInterface shark, Game game) {
        // TODO Auto-generated method stub
        return shark.visit(this, game);
    }
}

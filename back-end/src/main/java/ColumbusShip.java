
import java.awt.geom.Point2D;
import java.util.Observable;
import java.util.Random;
public class ColumbusShip extends Observable implements Defense,VisiteeInterface{
    private Point2D coordinate; // variable that temporarily holds the location of Ship 
    private Random random;  
    private Defense shield; // Columbus holds a reference to Defense, this can indicate if ship has shield or not and number of shields
    public ColumbusShip(int x,int y){
        coordinate = new Point2D.Float(x,y);
    }
    // getter for defense
    public Defense getDefense(){
        return shield;
    }
    public int getX(){
        return (int)coordinate.getX();
    }
    public int getY(){
        return (int)coordinate.getY();
    }
    // After moving in any of the directions, check if new temporary location has shield/shark/treasure/whirlpool
    private void setCoordinate(Point2D point,Point2D previousPoint,Game game){
        coordinate=point;
        char[][] grid=game.getGrid();
        int cx=(int)point.getX();int cy=(int)point.getY(); 
        if(grid[cx][cy]=='W'){// If new location contains whirlpool, CC will be moved to any of the random whirlpool surrounding cells
            coordinate = game.newRandomLocation(cx, cy);// Get the random location from game
            coordinate=coordinate!=null?coordinate:previousPoint;
        }
        if(grid[cx][cy]=='T'){// If new location contains Treasure, then set winner
            game.setWinner("Columbus");
            game.updateGrid(cx, cy, 'C');
            return ;
        }  
        if(grid[(int)coordinate.getX()][(int)coordinate.getY()]=='S'){// If new location contains Shield
            addShield();
        }
        boolean killed=accept(game.getCreatures(), game);// Check if new location contains Sea Creature
        grid[(int)coordinate.getX()][(int)coordinate.getY()]=killed?' ':'C';        
        if(killed){
            game.setWinner("Shark");return ;// If columbus collides with Shark, set winner as Shark.
        }
        setChanged();
        notifyObservers(game);
        return ;
    }
    // When user presses right arrow, CC can move towards right/East
    public Point2D moveEast(Game game){
        char[][] grid=game.getGrid(); // Get the grid from Game to check for any obstacles before moving to new location
        int xCoordinate = getX();
        int yCoordinate = getY();
        grid[xCoordinate][yCoordinate]=Character.MIN_VALUE;   // Update current grid value of CC to null
        if(game.noObstacles(xCoordinate, yCoordinate+1))// Check if there are no obstacles in right cell before moving
            setCoordinate(new Point2D.Float(xCoordinate,yCoordinate+1),coordinate,game); 
        else setCoordinate(coordinate,coordinate, game);// Don't move to the adjacent right cell if there are obstacles
        return coordinate;
    }
    // When user presses Left arrow, CC can move towards Left/West
    public Point2D moveWest(Game game)
    {
        char[][] grid=game.getGrid();// Get the grid from Game to check for any obstacles before moving to new location
        int xCoordinate = getX();
        int yCoordinate = getY();      
        grid[xCoordinate][yCoordinate]=Character.MIN_VALUE;   // Update current grid value of CC to null
        if(game.noObstacles(xCoordinate, yCoordinate-1))// Check if there are no obstacles in left cell before moving
            setCoordinate(new Point2D.Float(xCoordinate,yCoordinate-1),coordinate,game);
        else setCoordinate(coordinate,coordinate, game);// Don't move to the adjacent left cell if there are obstacles
        return coordinate;
    }
    // When user presses Up arrow, CC can move towards Up/North
    public Point2D moveNorth(Game game){
        char[][] grid=game.getGrid();// Get the grid from Game to check for any obstacles before moving to new location
        int xCoordinate = getX();
        int yCoordinate = getY();        
        grid[xCoordinate][yCoordinate]=Character.MIN_VALUE;// Update current grid value of CC to null
        if(game.noObstacles(xCoordinate-1, yCoordinate))// Check if there are no obstacles in above cell before moving
            setCoordinate(new Point2D.Float(xCoordinate-1,yCoordinate),coordinate,game);
        else setCoordinate(coordinate,coordinate, game);// Don't move to above cell if there are obstacles
        return coordinate;       
    }
    // When user presses Down arrow, CC can move towards Down/South
    public Point2D moveSouth(Game game){
        char[][] grid=game.getGrid();// Get the grid from Game to check for any obstacles before moving to new location
        int xCoordinate = getX();
        int yCoordinate = getY();   
        grid[xCoordinate][yCoordinate]=Character.MIN_VALUE;// Update current grid value of CC to null
        if(game.noObstacles(xCoordinate+1, yCoordinate))// Check if there are no obstacles in below cell before moving
            setCoordinate(new Point2D.Float(xCoordinate+1,yCoordinate),coordinate,game);
        else setCoordinate(coordinate,coordinate, game);// Don't move to above cell if there are obstacles
        return coordinate;
    }
    @Override
    public void addShield() {        
        if(this.shield!=null)
            shield.addShield();
        else
            this.shield = new Shield();
    }
    @Override
    public void reduceShield() {
        if(this.shield.getDefense()!=null)
            shield.reduceShield();
        else
            shield = null; 
    }
    // method inherited from VisiteeInterface to call visit method of Visitor
    @Override
    public boolean accept(VisitorInterface shark, Game game) {
        return shark.visit(this, game);
    }
}

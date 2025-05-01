
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;
// Controller class that maintains rules
public class Game {
    private static char[][] grid=new char[20][20];  
    private ColumbusShip ship; // Reference to CC
    private PirateFactory pirateFactory;
    private List<PirateShip>pirateShips; // List of Pirate ships needed to addnotify/removenotify of CC
    private WhirlpoolComposite whirlpools; // Composite node of Whirlpools
    private CreatureContainer creatures; // Composite node of sharks
    private String winner; 
    private static Game game;
    // utility method to change values in grid
    public void updateGrid(int x,int y,char value){
        grid[x][y]=value;
    }
    public CreatureContainer getCreatures(){
        return creatures;
    }
    private Game(){         
            pirateFactory = new ConcretePirateShipFactory();
            whirlpools=new WhirlpoolComposite();             
            pirateShips = new ArrayList<>();
            creatures = new CreatureContainer();
            initializeGrid(); 
    }
    public static Game getInstance(){
        game = new Game();
        return game;
    }
    // utility method needed in object creation to check if there are no objects already available in given location
    public boolean containsObject(int x,int y){
        return (grid[x][y]=='C'|| grid[x][y]=='P' || grid[x][y]=='Q'|| grid[x][y]=='I' || grid[x][y]=='W' || grid[x][y]=='T' || grid[x][y] =='H' || grid[x][y]=='S');
    }
    // getter for pirates
    public List<PirateShip> getPirateShips()
    {
        return pirateShips;
    }
    // when a new game is started, create a new grid[][]
    public char[][] initializeGrid(){
        grid=new char[20][20];
        for(int i=0;i<grid.length;i++)Arrays.fill(grid[i],Character.MIN_VALUE);
        return grid;
    }
    public void setWinner(String winner){
        this.winner = winner;
    }
    public void setColumbusShip(ColumbusShip ship){
        this.ship=ship;
    }
    public String getWinner(){
        return this.winner;
    }
    public static char[][] getGrid(){        
        return grid;
    }
    public ColumbusShip getColumbusShip(){
        return this.ship;
    }
    // Method to handle arrow key press events
    public Game play(int keyEvent){
        if(ship!=null&&winner==null){
            if(keyEvent==37)ship.moveWest(this); // user presses left arrow
        else if(keyEvent==38)ship.moveNorth(this); // user presses up arrow
        else if(keyEvent==39)ship.moveEast(this); // user presses right arrow
        else if(keyEvent==40)ship.moveSouth(this); // user presses down arrow
        creatures.move(this);// calling move on composite node of creatures
        }
        return this;
    }
    // when user drags and drops a Pirate ship for creation
    public PirateShip addPirateShips(int xCoordinate,int yCoordinate,char type){        		
            PirateShip pirateShip=null;
            if(xCoordinate>19||xCoordinate<0||yCoordinate>19||yCoordinate<0||(type!='P'&&type!='Q'))
            {
                throw new InputMismatchException("Coordinates Index Out of Bounds");
            }                
            if(!containsObject(xCoordinate, yCoordinate))// check there is no other obstacle in given location before creating pirate
			{
				grid[xCoordinate][yCoordinate] = type;		
                pirateShip=pirateFactory.getNewPirateShip(xCoordinate, yCoordinate,type,this);// Get Pirate Ship object from pirate factory
			}  
            return pirateShip;                          		 
		}
        // method to create island in given location
    public void addIslands(int xCoordinate,int yCoordinate){
			//Before assigning island, Make sure that location is not occupied by some other obstacle
			if(!containsObject(xCoordinate, yCoordinate))
			{
				grid[xCoordinate][yCoordinate] = 'I';
			}
    }
    // method to create shark in given location
    public void addCreatures(int xCoordinate,int yCoordinate)
    {
        // make sure that creature is within pre defined boundaries of composite
        if(!this.containsObject(xCoordinate, yCoordinate)&&xCoordinate>=2&&xCoordinate<=5&&yCoordinate>=8&&yCoordinate<=11&&grid[xCoordinate][yCoordinate]!='M')
        {
            creatures.addMonster(new Shark(xCoordinate,yCoordinate));// add leaf creature to composite component
            grid[xCoordinate][yCoordinate]='M';
        }
    }
    // method to add whirlpool in given location
    public void addWhirlpool(int x,int y){
        if(!containsObject(x,y)){
            Whirlpool whirlpool = new Whirlpool(x, y);
            whirlpools.addWhirlpool(whirlpool);// add leaf whirlpool to composite node
            grid[x][y]='W';
        }
    }
    // if CC/pirate enters whirlpool, new location is generated surrounding different whirlpool
    // method that gets location of random whirlpool and returns any one of the surrounding cell of whirlpool
    public Point2D newRandomLocation(int x,int y){
        Point2D newWhirlpoolLocation = whirlpools.newRandomLocation(x, y);
        int nx = (int)newWhirlpoolLocation.getX();int ny=(int)newWhirlpoolLocation.getY();
        if(!containsObject(nx-1, ny))nx--;
        else if(!containsObject(nx+1, ny))nx++;
        else if(!containsObject(nx, ny-1))ny--;
        else if(!containsObject(nx, ny+1))ny++;
        Point2D nextLocation = new Point2D.Float(nx, ny);
        System.out.println(newWhirlpoolLocation +" , "+nextLocation);
        return nextLocation.equals(newWhirlpoolLocation)?null:nextLocation;//if there are any obstacles, return location of whirlpool itself
    }
    // utility method to check if there are no obstacles for CC to move
    public boolean noObstacles(int x,int y){
        return x>=0&&x<=19&&y>=0&&y<=19&&grid[x][y]!='I';
    }
    // utility method to check if there are no obstacles for pirate to move
    public boolean noObstaclesForPirate(int x,int y){
        return x>=0&&x<=19&&y>=0&&y<=19&&grid[x][y]!='I'&&grid[x][y]!='P'&&grid[x][y]!='Q'&&grid[x][y]!='T' && grid[x][y]!='S';
    }
    // create new CC only if it is not already present
    public void addColumbusShip(int xCoordinate,int yCoordinate){
        if(ship==null)ship=new ColumbusShip(xCoordinate,yCoordinate);
        grid[ship.getX()][ship.getY()] = 'C';
    }
    // User drags and drops type of object to create and this method handles object creation logic
    public Game createObject(int number, Character type){
        int xCoordinate = number/20;int yCoordinate=number%20;        
        switch(type){
            case 'C':addColumbusShip(xCoordinate,yCoordinate);break;
            case 'P': addPirateShips(xCoordinate, yCoordinate,'P');break;
            case 'Q': addPirateShips(xCoordinate, yCoordinate, 'Q');break;
            case 'I': addIslands(xCoordinate,yCoordinate);break;
            case 'W': addWhirlpool(xCoordinate, yCoordinate);break;
            case 'M': addCreatures(xCoordinate,yCoordinate);break;
            case 'T': grid[xCoordinate][yCoordinate]=!containsObject(xCoordinate, yCoordinate)?type:grid[xCoordinate][yCoordinate]; break;
            case 'S': grid[xCoordinate][yCoordinate]=!containsObject(xCoordinate, yCoordinate)?type:grid[xCoordinate][yCoordinate]; break;
            case 'H': grid[xCoordinate][yCoordinate]=!containsObject(xCoordinate, yCoordinate)?type:grid[xCoordinate][yCoordinate]; break;
        }
        return this;
    }
    // utility method to check specific pirate that will be killed when CC collides with pirate, also remove pirate from game
    public void killPirateShip(int xCoordinate,int yCoordinate){
        PirateShip pirate = pirateShips.stream().filter(p->(p.getPirateLocation().getX()==xCoordinate&&p.getPirateLocation().getY()==yCoordinate)).collect(Collectors.toList()).get(0);
        ship.deleteObserver(pirate);
        pirateShips.remove(pirate);
        pirate=null;
    }
    // If pirate collides with CC(with shield) then Pirate will be killed, else pirate wins game
    public Point2D pirateColumbusCollisionCheck(int px,int py){
        int cx=ship.getX();int cy=ship.getY();
        if(cx==px&&py==cy){
			if(ship.getDefense()==null){
				setWinner("Pirate");
				setColumbusShip(null);
			}
			else {
				ship.reduceShield();
				killPirateShip(px,py);
				return null;
			}
		}
        return new Point2D.Float(px,py);
    }
}

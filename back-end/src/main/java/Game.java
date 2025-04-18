import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
public class Game {
    private static char[][] grid=new char[20][20];  
    private ColumbusShip ship;
    PirateFactory slowPirateFactory;
    PirateFactory fastPirateFactory;
    List<PirateShip>pirateShips;
    WhirlpoolComposite whirlpools;
    Monster creatures;
    String winner;
    public void updateGrid(int x,int y,char value){
        grid[x][y]=value;
    }
    public Game(){         
            slowPirateFactory = new SlowPirateShipFactory();
            fastPirateFactory = new FastPirateShipFactory(); 
            whirlpools=new WhirlpoolComposite();             
            pirateShips = new ArrayList<>();
            creatures = new Monster();
            initializeGrid(); 
    }
    public boolean containsObject(int x,int y){
        return grid[x][y]=='C'|| grid[x][y]=='P' || grid[x][y]=='Q'|| grid[x][y]=='I' || grid[x][y]=='W';
    }
    public List<PirateShip> getPirateShips()
    {
        return this.pirateShips;
    }
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
        return winner;
    }
    public static char[][] getGrid(){        
        return grid;
    }
    public ColumbusShip getColumbusShip(){
        return ship;
    }
    public Game play(int keyEvent){
        if(ship!=null&&winner==null){
            if(keyEvent==37)ship.moveWest(this);
        else if(keyEvent==38)ship.moveNorth(this);
        else if(keyEvent==39)ship.moveEast(this);
        else if(keyEvent==40)ship.moveSouth(this);
        creatures.move(this);             
        }
        return this;
    }      
    public void addPirateShips(int xCoordinate,int yCoordinate,char type){        		
            PirateShip pirateShip;
            PirateFactory pirateFactory = type=='P'?fastPirateFactory:slowPirateFactory;
            if(!containsObject(xCoordinate, yCoordinate))
			{
				grid[xCoordinate][yCoordinate] = type;		
                pirateShip=pirateFactory.getNewPirateShip(xCoordinate, yCoordinate);	
                ship.addObserver(pirateShip);
                pirateShips.add(pirateShip);             
			}                            		 
		}
    public void addIslands(int xCoordinate,int yCoordinate){
			//Before assigning Pirate ships, Make sure that location is not occupied by some other island/ship 
			if(!containsObject(xCoordinate, yCoordinate))
			{
				grid[xCoordinate][yCoordinate] = 'I';
			}
        
    }
    public void addCreatures(int xCoordinate,int yCoordinate)
    {
        if(!this.containsObject(xCoordinate, yCoordinate)&&xCoordinate>=16&&xCoordinate<=19&&yCoordinate>=8&&yCoordinate<=11&&grid[xCoordinate][yCoordinate]!='M')
        {
            creatures.addMonster(new Shark(xCoordinate,yCoordinate));
            grid[xCoordinate][yCoordinate]='M';
        }
    }
    public void addWhirlpool(int x,int y){
        if(!containsObject(x,y)){
            Whirlpool whirlpool = new Whirlpool(x, y);
            whirlpools.addWhirlpool(whirlpool);
            grid[x][y]='W';
        }
    }
    public Point2D newRandomLocation(int x,int y){
        Point2D newLocation = whirlpools.newRandomLocation(x, y);
        int nx = (int)newLocation.getX();int ny=(int)newLocation.getY();
        if(nx-1>=0&&!containsObject(nx-1, ny))nx--;
        else if(nx+1<=19&&!containsObject(nx+1, ny))nx++;
        else if(ny-1>=0&&!containsObject(nx, ny-1))ny--;
        else if(ny+1<=19&&!containsObject(nx, ny+1))ny++;
        System.out.println("new WhirlPool location: X: "+nx+", Y: "+ny);
        return new Point2D.Float(nx,ny);
    }
    public boolean noObstacles(int x,int y){
        return grid[x][y]!='I';
    }
    public void addColumbusShip(int xCoordinate,int yCoordinate){
        if(ship==null)ship=new ColumbusShip(xCoordinate,yCoordinate);
    }
    public Game createObject(int number, Character type){
        int xCoordinate = number/20;int yCoordinate=number%20;
        System.out.println("Called Create Object: X: "+xCoordinate+", Y: "+yCoordinate);
        switch(type){
            case 'C':addColumbusShip(xCoordinate,yCoordinate);break;
            case 'P': addPirateShips(xCoordinate, yCoordinate,'P');break;
            case 'Q': addPirateShips(xCoordinate, yCoordinate, 'Q');break;
            case 'I': addIslands(xCoordinate,yCoordinate);break;
            case 'W': addWhirlpool(xCoordinate, yCoordinate);break;
            case 'M': addCreatures(xCoordinate,yCoordinate);break;
            case 'T': grid[xCoordinate][yCoordinate]=type;break;
        }
        return this;
    }
}

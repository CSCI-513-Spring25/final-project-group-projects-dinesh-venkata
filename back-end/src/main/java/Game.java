
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;
public class Game {
    private static char[][] grid=new char[20][20];  
    private ColumbusShip ship;
    private PirateFactory pirateFactory;
    private List<PirateShip>pirateShips;
    private WhirlpoolComposite whirlpools;
    private CreatureContainer creatures;
    private String winner;
    public void updateGrid(int x,int y,char value){
        grid[x][y]=value;
    }
    public CreatureContainer getCreatures(){
        return this.creatures;
    }
    public Game(){         
            pirateFactory = new ConcretePirateShipFactory();
            whirlpools=new WhirlpoolComposite();             
            pirateShips = new ArrayList<>();
            creatures = new CreatureContainer();
            initializeGrid(); 
    }
    public boolean containsObject(int x,int y){
        return (grid[x][y]=='C'|| grid[x][y]=='P' || grid[x][y]=='Q'|| grid[x][y]=='I' || grid[x][y]=='W' || grid[x][y]=='T');
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
    public PirateShip addPirateShips(int xCoordinate,int yCoordinate,char type){        		
            PirateShip pirateShip=null;
            if(xCoordinate>19||xCoordinate<0||yCoordinate>19||yCoordinate<0||(type!='P'&&type!='Q'))
            {
                throw new InputMismatchException("Coordinates Index Out of Bounds");
            }                
            if(!containsObject(xCoordinate, yCoordinate))
			{
				grid[xCoordinate][yCoordinate] = type;		
                pirateShip=pirateFactory.getNewPirateShip(xCoordinate, yCoordinate,type,this);	                         
			}  
            return pirateShip;                          		 
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
        Point2D newWhirlpoolLocation = whirlpools.newRandomLocation(x, y);
        int nx = (int)newWhirlpoolLocation.getX();int ny=(int)newWhirlpoolLocation.getY();
        if(!containsObject(nx-1, ny))nx--;
        else if(!containsObject(nx+1, ny))nx++;
        else if(!containsObject(nx, ny-1))ny--;
        else if(!containsObject(nx, ny+1))ny++;
        Point2D nextLocation = new Point2D.Float(nx, ny);
        System.out.println(newWhirlpoolLocation +" , "+nextLocation);
        return nextLocation.equals(newWhirlpoolLocation)?null:nextLocation;//Checking for obstacles surrounding whirlpool
    }
    public boolean noObstacles(int x,int y){
        return x>=0&&x<=19&&y>=0&&y<=19&&grid[x][y]!='I';
    }
    public boolean noObstaclesForPirate(int x,int y){
        return x>=0&&x<=19&&y>=0&&y<=19&&grid[x][y]!='I'&&grid[x][y]!='P'&&grid[x][y]!='Q'&&grid[x][y]!='T';
    }
    public void addColumbusShip(int xCoordinate,int yCoordinate){
        if(ship==null)ship=new ColumbusShip(xCoordinate,yCoordinate);
        grid[ship.getX()][ship.getY()] = 'C';
    }
        
    public Game createObject(int number, Character type){
        int xCoordinate = number/20;int yCoordinate=number%20;        
        switch(type){
            case 'C':addColumbusShip(xCoordinate,yCoordinate);break;
            case 'P': addPirateShips(xCoordinate, yCoordinate,'P');break;
            case 'Q': addPirateShips(xCoordinate, yCoordinate, 'Q');break;
            case 'I': addIslands(xCoordinate,yCoordinate);break;
            case 'W': addWhirlpool(xCoordinate, yCoordinate);break;
            case 'M': addCreatures(xCoordinate,yCoordinate);break;
            case 'T': grid[xCoordinate][yCoordinate]=type; break;
            case 'S': grid[xCoordinate][yCoordinate]=type;break;
        }
        return this;
    }
    public void killPirateShip(int xCoordinate,int yCoordinate){
        PirateShip pirate = pirateShips.stream().filter(p->(p.getPirateLocation().getX()==xCoordinate&&p.getPirateLocation().getY()==yCoordinate)).collect(Collectors.toList()).get(0);
        ship.deleteObserver(pirate);
        pirateShips.remove(pirate);
        pirate=null;
    }
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

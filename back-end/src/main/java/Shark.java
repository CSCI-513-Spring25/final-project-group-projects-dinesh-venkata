
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
// Leaf component of Composite design pattern
class Shark extends CreatureContainer implements Creature {
    private Point2D location; // 
    // common method of composite design pattern
    @Override
    public void move(Game game) {
        // TODO Auto-generated method stub
        int xCoordinate=(int)location.getX();
        int yCoordinate=(int)location.getY();
        Random random = new Random();
        int direction = random.nextInt(0,4);
        char[][] grid=game.getGrid();        
        grid[xCoordinate][yCoordinate]=Character.MIN_VALUE;
        // get random direction for Shark
        switch(direction){
            case 0:
            {
                if(xCoordinate-1>=super.xfloor&&grid[xCoordinate-1][yCoordinate]!='M') // move up
                    xCoordinate--;
                break;
            }
            case 1:
            {
                if(yCoordinate+1<=super.yceiling&&grid[xCoordinate][yCoordinate+1]!='M') // move right
                    yCoordinate++;
                break;
            }
            case 2:
            {
                if(xCoordinate+1<=super.xceiling&&grid[xCoordinate+1][yCoordinate]!='M') // move down
                    xCoordinate++;
                break;
            }
            case 3:
            {
                if(yCoordinate-1>=super.yfloor&&grid[xCoordinate][yCoordinate-1]!='M')// move left
                    yCoordinate--;                
                break;
            }
        }
        if(grid[xCoordinate][yCoordinate]!='M'){
            kill(game,xCoordinate,yCoordinate,grid[xCoordinate][yCoordinate]);// check if there is pirate/cc in new shark location and kill them
            location = new Point2D.Float(xCoordinate,yCoordinate);
            grid[xCoordinate][yCoordinate]='M';            
        }        
    }
    Shark(int x,int y){
        this.location=new Point2D.Float(x,y);
    }
    // Once shark moves to its location, check if there is any pirate/CC in that grid and kill
    public void kill(Game game,int x,int y, char type){
        List<PirateShip>pirateShips=game.getPirateShips();
        if(type=='P')
        {
            // System.out.println("Killing Pirate with coordinates: X: "+x+", Y: "+y);
            PirateShip pirate = pirateShips.stream().filter(p->(p.getPirateLocation().getX()==x&&p.getPirateLocation().getY()==y)).collect(Collectors.toList()).get(0);
            // Modify this for all Pirates
            game.getColumbusShip().deleteObserver(pirate);
            pirateShips.remove(pirate);
            pirate=null;
        }
        if (type=='C'){
            System.out.println("Killing Columbus");
            game.setWinner("Shark");
            game.setColumbusShip(null);            
        }
    }
    public Point2D getLocation() {
        return location;
    }
    public void setLocation(Point2D location) {
        this.location = location;
    }
}

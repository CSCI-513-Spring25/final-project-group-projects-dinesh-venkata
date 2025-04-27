
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
//Composite Component in Composite design pattern
class CreatureContainer implements Creature, VisitorInterface{
    // boundaries of all creatures inside container
    int xfloor=16;
    int xceiling=19;
    int yfloor=8;
    int yceiling=11;
    // Leaf elements in composite Component
    List<Creature>sharks;
    public Point2D getLocation(){
        return null;
    }
    public CreatureContainer()
    {
        sharks = new ArrayList();
    }
    // Add Creature to list of leaf elements
    public void addMonster(Creature creature){
        sharks.add(creature);
    }
    // Common method inherited from Creature interface
    @Override
    public void move(Game game) {
        // call move on all sharks in this Creature container
        for(Creature creature: sharks)creature.move(game);
    }
    // Method to check if any shark is present at the given coordinates and return the specific Shark
    public Creature getSpecifiCreature(int x,int y){
        return sharks.stream().filter(s->s.getLocation().getX()==x&&s.getLocation().getY()==y).collect(Collectors.toList()).getFirst();
    }
    // Method to check if Pirate Ship coordinates match with any shark location and call kill on the shark
    @Override
    public boolean visit(PirateShip pirate, Game game) {
        // TODO Auto-generated method stub
        char[][] grid = game.getGrid();
        int px = (int)pirate.getPirateLocation().getX();
        int py = (int)pirate.getPirateLocation().getY();
        if(grid[px][py]=='M'){
            Shark shark = (Shark)getSpecifiCreature(px, py);
            shark.kill(game, px, py, 'P');
            return true;
        }
        return false;
    }
    // Method to check if Columbus Ship coordinates match with any shark location and call kill on the shark
    @Override
    public boolean visit(ColumbusShip ship, Game game) {
        // TODO Auto-generated method stub
        char[][] grid = game.getGrid();
        int cx = (int)ship.getX();
        int cy = (int)ship.getY();
        if(grid[cx][cy]=='M'){
            Shark shark = (Shark)getSpecifiCreature(cx, cy);
            shark.kill(game,cx,cy,'C');
            return true;
        }
        return false;
    }
}

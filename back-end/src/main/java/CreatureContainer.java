
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class CreatureContainer implements Creature, VisitorInterface{
    int xfloor=5;
    int xceiling=2;
    int yfloor=8;
    int yceiling=11;
    List<Creature>sharks;
    public Point2D getLocation(){
        return null;
    }
    public CreatureContainer()
    {
        sharks = new ArrayList();
    }
    public void addMonster(Creature creature){
        sharks.add(creature);
    }
    @Override
    public void move(Game game) {
        // TODO Auto-generated method stub
        for(Creature creature: sharks)creature.move(game);
    }
    public Creature getSpecifiCreature(int x,int y){
        return sharks.stream().filter(s->s.getLocation().getX()==x&&s.getLocation().getY()==y).collect(Collectors.toList()).getFirst();
    }
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

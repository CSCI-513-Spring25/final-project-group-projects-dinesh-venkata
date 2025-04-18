import java.util.ArrayList;
import java.util.List;

class Monster implements Creature{
    int xfloor=16;
    int xceiling=19;
    int yfloor=8;
    int yceiling=11;
    List<Creature>sharks;
    public Monster()
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
}

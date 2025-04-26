
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

class WhirlpoolComposite{
    private List<Whirlpool>whirlpools = new ArrayList<>();
    Random random ;
    void addWhirlpool(Whirlpool whirlpool){
        random = new Random();
        whirlpools.add(whirlpool);
    }
    public Point2D newRandomLocation(int x,int y){
        Whirlpool whirlpool = whirlpools.stream().filter(w->w.getLocation().getX()==x&&w.getLocation().getY()==y).collect(Collectors.toList()).getFirst();
        int index = whirlpools.indexOf(whirlpool);
        int size = whirlpools.size();
        int random = this.random.nextInt(0,size);
        if(random==index)random = (random+1)%size;
        Whirlpool neWhirlpool = whirlpools.get(random);  
        int nx= (int)neWhirlpool.getLocation().getX();
        int ny=(int)neWhirlpool.getLocation().getY();           
        return new Point2D.Float(nx,ny);
    }
}
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

class WhirlpoolComposite{
    List<Whirlpool>whirlpools = new ArrayList<>();
    Random random ;
    void addWhirlpool(Whirlpool whirlpool){
        random = new Random();
        whirlpools.add(whirlpool);
    }
    Point2D newRandomLocation(int x,int y){
        Whirlpool whirlpool = whirlpools.stream().filter(w->w.location.getX()==x&&w.location.getY()==y).collect(Collectors.toList()).getFirst();
        int index = whirlpools.indexOf(whirlpool);
        int size = whirlpools.size();
        int random = 0 + this.random.nextInt(0,size);
        if(random==index)random = (random+1)%size;
        Whirlpool neWhirlpool = whirlpools.get(random);  
        int nx= (int)neWhirlpool.getLocation().getX();
        int ny=(int)neWhirlpool.getLocation().getY();   
        System.out.println("Returning the index of random whirpool: "+random+", for whirlpool with index: "+index);     
        return new Point2D.Float(nx,ny);
    }
}
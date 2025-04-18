import java.awt.geom.Point2D;
class Whirlpool {
    Point2D location;    
    public Whirlpool(int x,int y){
        location=new Point2D.Float(x,y);
    }
    public Point2D getLocation(){
        return location;
    }
}

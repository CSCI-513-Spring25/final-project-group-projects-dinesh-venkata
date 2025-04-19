

public class Shield implements ShieldInterface{
    private Shield shield;
@Override
public void addShield() {
    if(this.shield == null){
        this.shield = new Shield();
    }
    else{
        shield.addShield();
    }  
}

@Override
public void reduceShield() {
    if (this.shield != null && this.shield.shield == null) 
        this.shield = null;
    else{
        shield.reduceShield();
    }

        /*if(ship.shield.shield!=null)
            {
            shield.reduceshield();
            shield=null;
            }
            else{
                shield = null; 
            }*/
        
    }

    public Shield getShield(){
        return shield;
    }
}




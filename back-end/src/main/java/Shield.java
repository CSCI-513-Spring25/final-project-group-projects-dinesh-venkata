// concrete decorator class
public class Shield implements Defense{
    // Wrapper decorator
    private Defense shield;
    // Adds additional shield as a wrapper
    @Override
    public void addShield() {
        // TODO Auto-generated method stub
        if(this.shield == null){
            this.shield = new Shield();            
        }
        else{
            shield.addShield();            
        } 
    }
    // Remove shield wrapper once CC collides with pirate ship
    @Override
    public void reduceShield() {
        // TODO Auto-generated method stub
    if (this.shield != null && this.shield.getDefense() == null) 
         this.shield = null;
     else{
         shield.reduceShield();
     }     
    }
    // getter for wrapper object
    @Override
    public Defense getDefense() {
        // TODO Auto-generated method stub
        return shield;
    }

    
}

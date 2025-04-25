public class Shield implements Defense{
    private Defense shield;
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
    @Override
    public void reduceShield() {
        // TODO Auto-generated method stub
    if (this.shield != null && this.shield.getDefense() == null) 
         this.shield = null;
     else{
         shield.reduceShield();
     }     
    }
    @Override
    public Defense getDefense() {
        // TODO Auto-generated method stub
        return shield;
    }

    
}

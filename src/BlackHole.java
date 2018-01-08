
public class BlackHole extends Weapon{
	
	public BlackHole(){
		super();
	}
	
	public int getAttack(){
		int rand=(int) (Math.random()*100);
		if (rand<=50){
			return 1;
		}else if (rand<=70){
			return 2;
		}else{
			return 0;
		}
	}
}

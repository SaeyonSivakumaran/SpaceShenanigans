
public class Laser extends Weapon{

	int damage, accuracy;
	
	public Laser(){
		super();
		this.damage=10;
		this.accuracy=50;	
		
	}
	
	public int getDamage(){
		return this.damage;
	}
	
	public int getAccuracy(){
		return this.accuracy;
	}
}

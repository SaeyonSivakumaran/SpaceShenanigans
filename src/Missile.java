
public class Missile extends Weapon{

	int damage,accuracy;
	
	public Missile(){
		super();
		this.damage=30;
		this.accuracy=30;
	}

	public int getDamage(){
		return this.damage;
	}
	
	public int getAccuracy(){
		return this.accuracy;
	}
}

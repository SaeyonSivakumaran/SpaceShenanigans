
public class Missile extends Weapon{

	int damage,accuracy;
	
	public Missile(){
		super();
		this.damage=20;
		this.accuracy=30;
	}

	public int getDamage(){
		return this.damage;
	}
	
	public int getAccuracy(){
		return this.accuracy;
	}
	
	public void setLevel(int moduleLevel){
		this.damage=20*moduleLevel;
		this.accuracy=30+5*moduleLevel;
	}
}


public class Weapon {
	private int upgradeLevel;
	
	public Weapon(){
		this.upgradeLevel=1;
	}
	
	public void upgrade(){
		this.upgradeLevel++;
	}
	
	public int getUpgradeLevel(){
		return this.upgradeLevel;
	}
}

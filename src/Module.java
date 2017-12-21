
public class Module {

	private int upgradeLevel;
	
	public Module(){
		this.upgradeLevel=1;
	}
	
	public int getUpgradeLevel(){
		return this.upgradeLevel;
	}
	
	/*
	 * 
	 */
	public void upgrade(){
		this.upgradeLevel++;
	}
}

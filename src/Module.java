
public class Module {

	private int upgradeLevel, steelNeeded, grapheneNeeded;
	
	public Module(){
		this.upgradeLevel=1;
		this.steelNeeded=200;
		this.grapheneNeeded=100;
	}
	
	public int getUpgradeLevel(){
		return this.upgradeLevel;
	}
	
	/*
	 * 
	 */
	public void upgrade(){
		this.steelNeeded*=2;
		this.grapheneNeeded*=2;
		this.upgradeLevel++;
	}

	public int getSteel(){
		return this.steelNeeded;
	}
	
	public int getGraphene(){
		return this.grapheneNeeded;
	}
	
	public void setUpgradeLevel(int level){
		this.upgradeLevel=level;
	}
}

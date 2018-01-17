public class MiningModule extends Module{

	int mineSpeed, crystalNeeded;
	
	public MiningModule(){
		super();
		this.mineSpeed=5;
		this.crystalNeeded=15;
	}
	
	public void upgrade(){
		super.upgrade();
		this.mineSpeed++;
		this.crystalNeeded*=2;
	}
	
	public int getMineSpeed(){
		return this.mineSpeed;
	}
	
	public int getCrystal(){
		return this.crystalNeeded;
	}
	
	public void setLevel(int level){
		super.setUpgradeLevel(level);
		this.mineSpeed=5*super.getUpgradeLevel();
		this.crystalNeeded=15*super.getUpgradeLevel();
	}
}

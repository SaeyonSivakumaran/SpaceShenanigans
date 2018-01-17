public class EngineModule extends Module{

	int travelSpeed, plutNeeded;
	
	public EngineModule(){
		super();
		this.travelSpeed=10;
		this.plutNeeded=15;
	}
	
	public void upgrade(){
		super.upgrade();
		this.plutNeeded*=2;
		this.travelSpeed *= 0.5;
	}
	
	public int getPlut(){
		return this.plutNeeded;
	}
	
	public int getSpeed(){
		return this.travelSpeed;
	}
	
	public void setLevel(int level){
		super.setUpgradeLevel(level);
		this.travelSpeed=10*super.getUpgradeLevel();
		this.plutNeeded=15*super.getUpgradeLevel();
	}
}
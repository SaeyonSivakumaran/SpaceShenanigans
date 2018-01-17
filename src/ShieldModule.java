public class ShieldModule extends Module{
	
	int deflectionRate, starliteNeeded;
	
	public ShieldModule(){
		super();
		this.deflectionRate=20;
		this.starliteNeeded=10;
	}
	
	public void upgrade(){
		super.upgrade();
		this.deflectionRate+=20;
		this.starliteNeeded*=2;
	}
	
	public int getStarlite(){
		return this.starliteNeeded;
	}

	public int getDeflection(){
		return this.deflectionRate;
	}
	
	public void setDeflection(int newRate){
		this.deflectionRate = newRate;
	}
	
	public void setLevel(int level){
		super.setUpgradeLevel(level);
		this.deflectionRate=20*super.getUpgradeLevel();
		this.starliteNeeded=10*super.getUpgradeLevel();
	}
}
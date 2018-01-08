
public class ShieldModule extends Module{
	
	int deflectionRate, starliteNeeded;
	
	public ShieldModule(){
		super();
		this.deflectionRate=20;
		this.starliteNeeded=5;
	}
	
	public void upgrade(){
		super.upgrade();
		this.starliteNeeded*=2;
	}
	
	public int getStarlite(){
		return this.starliteNeeded;
	}

	public int getDeflection(){
		return this.deflectionRate;
	}
}

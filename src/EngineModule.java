public class EngineModule extends Module{

	int travelSpeed, plutNeeded;
	
	public EngineModule(){
		super();
		this.travelSpeed=1;
		this.plutNeeded=15;
	}
	
	public void upgrade(){
		super.upgrade();
		this.plutNeeded*=2;
	}
	
	public int getPlut(){
		return this.plutNeeded;
	}
	
	public int getSpeed(){
		return this.travelSpeed;
	}
}
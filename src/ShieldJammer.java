
public class ShieldJammer extends Weapon{

	int jamRate;
	
	public ShieldJammer(){
		super();
		this.jamRate=25;
	}
	
	public void upgrade(){
		super.upgrade();
		this.jamRate+=15;
	}
	
	public int getJamRate(){
		return this.jamRate;
	}
}


public class ShieldJammer extends Weapon{

	int jamRate;
	
	public ShieldJammer(){
		super();
		this.jamRate=25;
	}
	
	public void setLevel(int level){
		this.jamRate=25+(15*level);
	}
	
	public int getJamRate(){
		return this.jamRate;
	}
}

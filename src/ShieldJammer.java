/**
 * ShieldJammer.java
 * class for the shield jammer weapon
 * @author Car l Zhang
 * @version 1.0
 */
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

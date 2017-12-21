
public class WeaponModule extends Module{
	
	private Weapon weapon1, weapon2;
	private int steelNeeded, grapheneNeeded, pyroNeeded;
	
	public WeaponModule(){
		this.steelNeeded=200;
		this.grapheneNeeded=100;
		this.pyroNeeded=10;
	}
	
	public void changeWeapons(Weapon weapon1, Weapon weapon2){
		this.weapon1=weapon1;
		this.weapon2=weapon2;
	}
	
	
}

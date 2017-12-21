
public class WeaponModule extends Module{
	
	private Weapon weapon1, weapon2, weapon3;
	private int steelNeeded, grapheneNeeded, pyroNeeded;
	
	public WeaponModule(){
		this.steelNeeded=200;
		this.grapheneNeeded=100;
		this.pyroNeeded=10;
	}
	
	public void changeWeapon1(Weapon weapon){
		this.weapon1=weapon;
	}
	
	public void changeWeapon2(Weapon weapon){
		this.weapon2=weapon;
	}
	
	public void changeWeapon3(Weapon weapon){
		this.weapon3=weapon;
	}
	
	public void upgrade(){
		super.upgrade();
		this.steelNeeded*=2;
		this.grapheneNeeded*=2;
		this.pyroNeeded*=2;
	}
}

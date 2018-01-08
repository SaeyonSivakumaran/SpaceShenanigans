
public class WeaponModule extends Module{
	
	private Weapon weapon1, weapon2, weapon3;
	private int pyroNeeded;
	
	public WeaponModule(){
		super();
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
		this.pyroNeeded*=2;
	}
	
	public Weapon getWeapon1(){
		return this.weapon1;
	}
	
	public Weapon getWeapon2(){
		return this.weapon2;
	}
	
	public Weapon getWeapon3(){
		return this.weapon3;
	}
	
	public int getPyro(){
		return this.pyroNeeded;
	}
}

/**
 * Ship.java
 * This class represents a ship in the game
 * @author Saeyon Sivakumaran
 */

public class Ship {
	//Ship modules
	private EngineModule engine;
	private ShieldModule shield;
	private WeaponModule weaponModule;
	private MiningModule miningModule;
	private DeepSpaceViewer deepSpaceViewer; 
	//Ship health
	private int health;
	
	/**
	 * Constructor for Ship
	 */
	Ship(){
		this.health = 500;  //Setting the health of the ship
		//Initializing the modules
		this.engine = new EngineModule();
		this.shield = new ShieldModule();
		this.weaponModule = new WeaponModule();
		this.miningModule = new MiningModule();
		this.deepSpaceViewer = new DeepSpaceViewer();
	}
	
	/**
	 * getHealth
	 * Method to return the health of the ship to the user
	 * @param Nothing
	 * @return int Health of the ship
	 */
	public int getHealth(){
		return this.health;
	}
	
	/**
	 * addHealth
	 * Method to add health to the ship
	 * @param healthNum Amount of health to be added
	 * @return Nothing
	 */
	public void addHealth(int healthNum){
		this.health += healthNum;
	}
	
	/**
	 * removeHealth
	 * Method to remove health to the ship
	 * @param healthNum Amount of health to be removed
	 * @return Nothing
	 */
	public void removeHealth(int healthNum){
		this.health -= healthNum;
	}
	
	/**
	 * getModules
	 * Method to return the modules
	 * @param Nothing
	 * @return Module[] Array of modules
	 */
	public Module[] getModules(){
		Module[] modules = new Module[5];
		modules[0] = this.engine;
		modules[1] = this.shield;
		modules[2] = this.weaponModule;
		modules[3] = this.miningModule;
		modules[4] = this.deepSpaceViewer;
		return modules;
	}
	
	/**
	 * upgradeEngineModule
	 * Method to upgrade the engine
	 * @param Nothing
	 * @return Nothing
	 */
	public void upgradeEngineModule(){
		this.engine.upgrade();
	}
	
	/**
	 * upgradeShieldModule
	 * Method to upgrade the shield
	 * @param Nothing
	 * @return Nothing
	 */
	public void upgradeShieldModule(){
		this.shield.upgrade();
	}
	
	/**
	 * upgradeWeaponModule
	 * Method to upgrade the weapon module
	 * @param Nothing
	 * @return Nothing
	 */
	public void upgradeWeaponModule(){
		this.weaponModule.upgrade();
	}
	
	/**
	 * upgradeMiningModule
	 * Method to upgrade the mining module
	 * @param Nothing
	 * @return Nothing
	 */
	public void upgradeMiningModule(){
		this.miningModule.upgrade();
	}
	
	/**
	 * upgradeDeepSpaceViewer
	 * Method to upgrade the deep space viewer
	 * @param Nothing
	 * @return Nothing
	 */
	public void upgradeDeepSpaceViewer(){
		this.deepSpaceViewer.upgrade();
	}
	
}

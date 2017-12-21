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
	
}

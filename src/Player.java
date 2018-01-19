/**
 * [player.java]
 * class for player
 * @author Carl Zhang
 *@version 1.1
 */
public class Player {
	
	//vars
	private String username;
	private String password;//self explainatory
	private String location;//location of player
	private Ship playerShip;//players ship
	private int[] resources; //in this Steel, graphene, plutonium, starlite, pyroxium, blast crystal, intellectium
	private boolean inBattle = false;//player battling
	
	/**
	 * main constructor
	 * @param username
	 * @param password
	 */
	Player(String username, String password){
		this.username = username;
		this.password = password;//sets username and password
		this.playerShip = new Ship();//creates ship
		this.resources = new int[]{0, 0, 0, 0, 0, 0, 0};//starts resources
	}
	
	/**
	 * getUsername
	 * returns the username
	 * @param void
	 * @return string for username
	 */
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * setUsername
	 * sets the username
	 * @param username
	 * @return void
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * getPassword
	 * returns password
	 * @param void
	 * @return string for password
	 */
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * setPassword
	 * sets the password
	 * @param password
	 * @return void
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getLocation() {
		return this.location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public Ship getShip() {
		return this.playerShip;
	}
	
	/*
	 * changeBattleStatus
	 * Flips battle boolean when called
	 */
	public void changeBattleStatus(){
		this.inBattle = !inBattle;
	}
	
	/**
	 * Method to return number of a certain resource
	 * @return Number of resources at the index
	 * @param resourceNum
	 */
	public int getNumResources(int resourceNum){
		return this.resources[resourceNum];
	}
	
	/*
	 * changeResources
	 * Changes the amount of the specified resource
	 * @param int representing the index of the resource to be changed, int representing the amount changed
	 */
	public void changeResources(int resourceNum, int change){
		this.resources[resourceNum] = change;
	}
	
	/*
	 * getResources
	 * Getter for resources
	 * @return int[] of all the resources this player has
	 */
	public int[] getResources(){
		return this.resources;
	}
}

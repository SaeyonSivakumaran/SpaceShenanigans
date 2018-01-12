
public class Player {
	private String username;
	private String password;
	private String location;
	private Ship playerShip;
	private int[] resources; //in this Steel, graphene, plutonium, starlite, pyroxium, blast crystal, intellectium
	private boolean inBattle = false;
	
	Player(String username, String password){
		this.username = username;
		this.password = password;
		this.playerShip = new Ship();
		this.resources = new int[]{0, 0, 0, 0, 0, 0, 0};
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
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
		this.resources[resourceNum] = resources[resourceNum] + change;
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

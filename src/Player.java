
public class Player {
	private String username;
	private String password;
	private Ship playerShip;
	private int[] resources; //in this Steel, graphene, plutonium, starlite pyroxium, blast crystal, intellectium
	private boolean inBattle = false;
	
	Player(String username, String password){
		this.username = username;
		this.password = password;
		this.playerShip = new Ship();
		this.resources = new int[]{0, 0, 0, 0, 0, 0, 0};
	}
	
	/*
	 * changeBattleStatus
	 * Flips battle boolean when called
	 */
	public void changeBattleStatus(){
		this.inBattle = !inBattle;
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

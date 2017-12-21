import java.util.ArrayList;

public class SpaceDepot {
	private ArrayList<Player> playersInDepot = new ArrayList<Player>();

	
	/**
	 * addPlayer
	 * Adds parameter player 
	 * @param player that has arrived at the space depot
	 */
	public void addPlayer(Player newPlayer){
		playersInDepot.add(newPlayer);
	}

	/**
	 * removePlayer
	 * Removes player given as parameter from the list of players in the depot
	 * @param player that has left the depot and should be removed
	 */
	public void removePlayer(Player gonePlayer){
		playersInDepot.remove(gonePlayer);
	}

	/**
	 * getPlayers
	 * Getter for the list of players in the depot at the time
	 * @return playersInDepot
	 */
	public ArrayList<Player> getPlayers(){
		return playersInDepot;
	}
}

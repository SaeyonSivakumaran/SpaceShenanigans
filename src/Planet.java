/**
 * Planet.java
 * class for planets
 * @author carl zhang
 *@version 1.2
 */
public class Planet {
	
	//Class variables
	String name;
	String resource;
	int resourceAmount;
	int resourceRate;
	int mineRate;
	Player player1, player2;
	Boolean travel;
	long updateTime;
	SpaceServer server;
	static final String ALPHABET="abcdefghijklmnopqrstuvwxyz";

	/*
	 * Constructor for the Planet class
	 */
	public Planet(String name, long time, SpaceServer server){
		//Assigning variables
		this.name = name;
		this.server=server;
		//Creating a name
		int rand=(int) (Math.random()*15);
		for (int i=0;i<rand;i++){
			int letter=(int) (Math.random()*24);
			name+=ALPHABET.substring(letter,letter+1);
		}
		//Assigning a random resource to the planet
		rand=(int) (Math.random()*7);
		switch (rand){

		case 0: resource="Graphene";
		resourceAmount=(int) (Math.random()*100+50);
		resourceRate=10;
		mineRate=100;
		break;
		case 1: resource="Steel";
		resourceAmount=(int) (Math.random()*100+50);
		resourceRate=20;
		mineRate=200;
		break;
		case 2: resource="Intellectium";
		resourceAmount=(int) (Math.random()*10);
		resourceRate=2;
		mineRate=20;
		break;
		case 3: resource="Plutonium";
		resourceAmount=(int) (Math.random()*10);
		resourceRate=1;
		mineRate=15;
		break;
		case 4: resource="Starlite";
		resourceAmount=(int) (Math.random()*10);
		resourceRate=1;
		mineRate=10;
		break;
		case 5: resource="Blast Crystal";
		resourceAmount=(int) (Math.random()*10);
		resourceRate=1;
		mineRate=15;
		break;
		case 6: resource="Pyroxium";
		resourceAmount=(int) (Math.random()*10);
		resourceRate=1;
		mineRate=10;
		break;
		}

		updateTime=time;

	}

	/**
	 * getName
	 * Getter for the planet's name
	 * @return String name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * getResourceType
	 * Getter for the planet's resource type
	 * @return String getResourceType
	 */
	public String getResourceType() {
		return this.resource;
	}
	
	/**
	 * getResource
	 * Getter for the planet's amount of resources
	 * @return int resourceAmount
	 */
	public int getResource(){
		return this.resourceAmount;
	}
	
	/**
	 * canTravrel
	 * Checking whether you can travel to the planet
	 * @return boolean Variable for if you can travel
	 */
	public boolean canTravel() {
		if (player1 != null && player2 != null) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * addPlayer
	 * Method to add a player to the planet
	 * @param player
	 */
	public void addPlayer(Player player){
		if (player1!=null){
			player2=player;
			server.battle(player1, player2);
			return;
		}
		player1=player;
	}

	/**
	 * update
	 * Updates the planet's resources
	 * @param long timeNow
	 */
	public void update(long timeNow){
		if ((timeNow-updateTime)/1000000000.0>1){
			updateTime=timeNow;
			resourceAmount+=resourceRate;
		}
		
	}
	
	/**
	 * mine
	 * Method to mine the planet
	 * @param long timeNow
	 */
	public int mine(long timeNow){
		if (((timeNow-updateTime)/1000000000.0>1)&&(mineRate>=resourceAmount)){
			updateTime=timeNow;
			resourceAmount-=mineRate;
			return mineRate;
		}
		return 0;
		
	}
	
	/**
	 * removePlayer
	 * Method to remove a player from the planet
	 * @param player
	 */
	public void removePlayer(Player player){
		if (player1.getUsername().equals(player.getUsername())){
			player1=null;
		}
		if (player2.getUsername().equals(player.getUsername())){
			player2=null;
		}
	}
	
	/**
	 * setResource
	 * Set the amount of resources on the planet
	 * @param int resource
	 */
	public void setResource(int resource){
		this.resourceAmount=resource;
	}
	
}

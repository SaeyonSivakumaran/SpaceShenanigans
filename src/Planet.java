
public class Planet {
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

	public Planet(String name, long time, SpaceServer server){
		this.name = name;
		this.server=server;
		int rand=(int) (Math.random()*15);
		for (int i=0;i<rand;i++){
			int letter=(int) (Math.random()*24);
			name+=ALPHABET.substring(letter,letter+1);
		}
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

	public String getName() {
		return this.name;
	}
	
	public String getResourceType() {
		return this.resource;
	}
	
	public int getResource(){
		return this.resourceAmount;
	}
	public boolean canTravel() {
		if (player1 != null && player2 != null) {
			return false;
		} else {
			return true;
		}
	}
	
	public void addPlayer(Player player){
		if (player1!=null){
			player2=player;
			server.battle(player1, player2);
			return;
		}
		player1=player;
	}

	
	public void update(long timeNow){
		if ((timeNow-updateTime)/1000000000.0>60){
			updateTime=timeNow-updateTime;
			resourceAmount+=resourceRate;
		}
		
	}
	
	public int mine(long timeNow){
		if ((timeNow-updateTime)/1000000000.0>60){
			updateTime=timeNow-updateTime;
			resourceAmount-=resourceRate;
			return resourceRate;
		}
		return 0;
		
	}
	public void removePlayer(Player player){
		if (player1.getUsername().equals(player.getUsername())){
			player1=null;
		}
		if (player2.getUsername().equals(player.getUsername())){
			player2=null;
		}
	}
	
	public void setResource(int resource){
		this.resourceAmount=resource;
	}
	
}

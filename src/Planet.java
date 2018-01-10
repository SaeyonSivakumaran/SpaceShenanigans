
public class Planet {
	String name;
	String resource;
	int resourceAmount;
	Player player1, player2;
	Boolean travel;
	long updateTime;
	static final String ALPHABET="abcdefghijklmnopqrstuvwxyz";
	
	public Planet(long time){
		name="";
		int rand=(int) (Math.random()*15);
		for (int i=0;i<rand;i++){
			int letter=(int) (Math.random()*24);
			name+=ALPHABET.substring(letter,letter+1);
		}
		rand=(int) (Math.random()*7);
		switch (rand){

		case 0: resource="Graphene";
		resourceAmount=(int) (Math.random()*100+50);
		break;
		case 1: resource="Steel";
		resourceAmount=(int) (Math.random()*100+50);
		break;
		case 2: resource="Intellectium";
		resourceAmount=(int) (Math.random()*10);
		break;
		case 3: resource="Plutonium";
		resourceAmount=(int) (Math.random()*10);
		break;
		case 4: resource="Starlite";
		resourceAmount=(int) (Math.random()*10);
		break;
		case 5: resource="Fire Crystal";
		resourceAmount=(int) (Math.random()*10);
		break;
		case 6: resource="Pyroxium";
		resourceAmount=(int) (Math.random()*10);
		break;
		}
		
		updateTime=time;
		
	}
	System.out.println("u gey eh");
	
}

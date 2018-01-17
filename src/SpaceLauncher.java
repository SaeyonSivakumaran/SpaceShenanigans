
public class SpaceLauncher {

	public static void main(String args[]) {
		SpaceClient client=new SpaceClient();
		MapPanel display=new MapPanel(client);
		client.getMap(display);
		client.go();
	}
}


public class SpaceLauncher {

	public static void main(String args[]) {
		SpaceClient client = new SpaceClient();
		SpaceClient.MapPanel display = client.new MapPanel(client);
		client.getMap(display);
		client.go();
	}
}

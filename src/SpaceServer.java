/**
 * SpaceServer.java
 * Class for the main server
 * @author Saeyon Sivakumaran
 */

//Imports for GUI
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.BorderLayout;

//Networking imports
import java.net.Socket;
import java.net.ServerSocket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

//Game imports
import java.util.ArrayList;

class SpaceServer extends JFrame {

	// GUI Variables
	private JPanel mainPanel;
	private JTextArea consoleOutput;

	// Networking variables
	private boolean running;
	private ServerSocket serverSocket;

	// Game variables
	ArrayList<Player> players;
	ArrayList<Player> onlinePlayers;
	ArrayList<PlayerConnection> connections;
	SpaceDepot depot;

	/**
	 * Constructor for SpaceServer
	 */
	SpaceServer() {
		// Setting properties of the JFrame
		this.setSize(600, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Initializing all the game variables
		running = true;
		players = new ArrayList<Player>();
		connections = new ArrayList<PlayerConnection>();
		depot = new SpaceDepot();
		// Initializing all GUI components
		mainPanel = new JPanel(new BorderLayout());
		consoleOutput = new JTextArea();
		consoleOutput.setEditable(false);
		// Adding components to main panel
		mainPanel.add(consoleOutput, BorderLayout.CENTER);
		// Completing the main frame
		this.add(mainPanel);
		this.setVisible(true);
	}

	/**
	 * Method to start server
	 * 
	 * @param Nothing
	 * @return Nothing
	 */
	public void startServer() {
		consoleOutput.append("Server started\n");
		Socket client = null; // Socket for client
		// Waiting for connection
		try {
			serverSocket = new ServerSocket(5000);
			serverSocket.setSoTimeout(5000);
			while (running) {
				client = serverSocket.accept(); // Creating the client socket
				System.out.println("Client connected");
				PlayerConnection tempConnection = new PlayerConnection(client);
				connections.add(tempConnection); // Adding to the arraylist of connections
				Thread clientThread = new Thread(tempConnection);
				clientThread.start(); // Starting the client thread
			}
		} catch (Exception e) {
			System.out.println("Connection failed");
		}
	}

	/**
	 * Connection Handler for each client
	 */
	class PlayerConnection implements Runnable {

		// Networking variables
		Socket client;
		BufferedReader input;
		PrintWriter output;
		boolean playerRunning;
		String name;

		/**
		 * Constructor for ConnectionHandler
		 */
		PlayerConnection(Socket socket) {
			this.client = socket;
			// Getting connections
			try {
				InputStreamReader inputStream = new InputStreamReader(client.getInputStream());
				this.input = new BufferedReader(inputStream);
				this.output = new PrintWriter(client.getOutputStream());
			} catch (IOException e) {
				consoleOutput.append("Client connection failed\n");
			}
			playerRunning = true; // Allowing the handler to run
		}

		/**
		 * Main run method
		 * 
		 * @param Nothing
		 * @return Nothing
		 */
		public void run() {
			String msg; // Client message
			while (playerRunning) {
				try {
					// If the client is sending a message
					if (input.ready()) {
						msg = input.readLine();
						consoleOutput.append("Client message:" + msg + "\n");
						runCommand(msg); // Running the command
					}
				} catch (IOException e) {
					// Message failed
					consoleOutput.append("Client message failed\n");
				}
			}
		}

		/**
		 * Method to check run commands
		 * 
		 * @param Nothing
		 * @return Nothing
		 */
		public void runCommand(String msg) {
			String command = msg.substring(0, msg.indexOf(":")); // Command variable
			msg = msg.substring(msg.indexOf(":") + 1);
			// Different functions for each commands
			if (command.equals("login")) {
				// Getting the command info
				String username = msg.substring(0, msg.indexOf(","));
				String password = msg.substring(msg.indexOf(",") + 1);
				boolean userFound = false;
				// Finding the user in the list of players
				for (int i = 0; i < players.size(); i++) {
					if (players.get(i).getUsername().equals(username)) {
						if (players.get(i).getPassword().equals(password)) {
							userFound = true;
							consoleOutput.append(username + " logged IN\n");
							onlinePlayers.add(players.get(i));
							this.name = username;  //Setting name of the connection
							// Sending confirmation to client
							output.println("loginaccepted");
							output.flush();
						}
					}
				}
				// If no user was found with the correct details
				if (userFound == false) {
					consoleOutput.append("Login failed");
					// Sending fail message to client
					output.println("loginfailed");
					output.flush();
				}
			} else if (command.equals("logout")) {
				String username = msg;
				// Removing the user from list of online users
				for (int i = 0; i < onlinePlayers.size(); i++) {
					if (onlinePlayers.get(i).getUsername().equals(username)) {
						onlinePlayers.remove(i); // Removing the user
						consoleOutput.append(username + " logged OUT\n");
						playerRunning = false;
						break;
					}
				}
			} else if (command.equals("newaccount")) {
				// Getting the command info
				String username = msg.substring(0, msg.indexOf(","));
				String password = msg.substring(msg.indexOf(",") + 1);
				boolean used = false;
				//Checking if the username has been used
				for (int i = 0; i < players.size(); i++){
					if (players.get(i).getUsername().equals(username)){
						//Acknowledging that the username is already taken
						consoleOutput.append(username + " has already been taken\n");
						used = true;
						output.println("accountinvalid");  //Sending an invalid message to the suer
						output.flush();
						break;
					}
				}
				//If the username works
				if (!used){
					output.println("accountvalid");  //Sending a success message
					output.flush();
					//Adding the new user
					players.add(new Player(username, password));
					consoleOutput.append(username + " has joined Space Shenanigans\n");
				}
			} else if (command.equals("tradeInfoWanted")){
				String invitee = msg.substring(msg.indexOf(",") + 1);
				int[] resources;
				String resourceString = "";
				boolean playerFound = false;
				consoleOutput.append(invitee + "'s inventory has been requested\n");  //Console output
				//Finding the invitee
				for (int i = 0; i < onlinePlayers.size(); i++){
					if (onlinePlayers.get(i).getUsername().equals(invitee)){
						playerFound = true;
						resources = onlinePlayers.get(i).getResources();
						//Adding to the resource string
						for (int j = 0; j < resources.length; j++){
							if (resources[j] > 0){
								resourceString += j + "-" + resources[j] + ",";
							}
						}
						//Sending out the inventory
						consoleOutput.append(invitee + "'s inventory:  " + resourceString + "\n");
						output.println("inventory:" + invitee + "," + resourceString);
						output.flush();
					}
				}
				//If the player wasn't found
				if (playerFound == false){
					consoleOutput.append(invitee + "was not found\n");
					output.println("tradeinvalid");
					output.flush();
				}
			} else if (command.equals("tradeoffer")){
				//Getting the invitee
				String tempMsg = msg;
				tempMsg = tempMsg.substring(tempMsg.indexOf(",") + 1);
				String invitee = tempMsg.substring(0, tempMsg.indexOf(","));
				//Finding the user
				for (int i = 0; i < connections.size(); i++){
					if (connections.get(i).getName().equals(invitee)){
						consoleOutput.append(invitee + "was sent a trade:  " + msg + "\n");
						connections.get(i).output(msg);  //Outputting to the invitee
						break;
					}
				}
			} else if (command.equals("acceptTrade")){
				//User resources
				int[] inviterResources = new int[7];
				int[] inviteeResources = new int[7];
				//Getting the users
				String inviter = msg.substring(0, msg.indexOf(","));
				msg = msg.substring(msg.indexOf(",") + 1);
				String invitee = msg.substring(0, msg.indexOf(","));
				msg = msg.substring(msg.indexOf(",") + 1);
				//Running through all the resources
				while (msg.length() > 1){
					String resourceInfo = msg.substring(0, msg.indexOf(","));
					//Checking which player the resource is for
					if (resourceInfo.substring(0, 1).equals("1")){
						//Getting all resource information
						String tempResourceInfo = resourceInfo;
						tempResourceInfo = tempResourceInfo.substring(tempResourceInfo.indexOf("-") + 1);
						String resourceTypeString = tempResourceInfo.substring(0, 1);
						int resourceType = Integer.parseInt(resourceTypeString);
						tempResourceInfo = tempResourceInfo.substring(tempResourceInfo.indexOf("-") + 1);
						int resourceNum = Integer.parseInt(tempResourceInfo);
						//Finding the players
						for (int i = 0; i < onlinePlayers.size(); i++){
							if (onlinePlayers.get(i).getUsername().equals(inviter)){
								onlinePlayers.get(i).changeResources(resourceType, onlinePlayers.get(i).getNumResources(resourceType) - resourceNum);  //Changing the resource amount
							} else if (onlinePlayers.get(i).getUsername().equals(invitee)){
								onlinePlayers.get(i).changeResources(resourceType, onlinePlayers.get(i).getNumResources(resourceType) + resourceNum);  //Changing the resource amount
							}
						}
					} else {
						//Getting all resource information
						String tempResourceInfo = resourceInfo;
						tempResourceInfo = tempResourceInfo.substring(tempResourceInfo.indexOf("-") + 1);
						String resourceTypeString = tempResourceInfo.substring(0, 1);
						int resourceType = Integer.parseInt(resourceTypeString);
						tempResourceInfo = tempResourceInfo.substring(tempResourceInfo.indexOf("-") + 1);
						int resourceNum = Integer.parseInt(tempResourceInfo);
						//Finding the players
						for (int i = 0; i < onlinePlayers.size(); i++){
							if (onlinePlayers.get(i).getUsername().equals(invitee)){
								onlinePlayers.get(i).changeResources(resourceType, onlinePlayers.get(i).getNumResources(resourceType) - resourceNum);  //Changing the resource amount
							} else if (onlinePlayers.get(i).getUsername().equals(inviter)){
								onlinePlayers.get(i).changeResources(resourceType, onlinePlayers.get(i).getNumResources(resourceType) + resourceNum);  //Changing the resource amount
							}
						}
					}
					msg = msg.substring(msg.indexOf(",") + 1);  //Shortening the message
				}
				//Finding the inviter and invitee's resources
				for (int i = 0; i < onlinePlayers.size(); i++){
					if (onlinePlayers.get(i).getUsername().equals(inviter)){
						inviterResources = onlinePlayers.get(i).getResources();
					} else if (onlinePlayers.get(i).getUsername().equals(invitee)){
						inviteeResources = onlinePlayers.get(i).getResources();
					}
				}
				//Updating the inviter's client
				String newResources = "";
				for (int i = 0; i < inviterResources.length; i++){
					newResources += inviterResources[i] + ",";
				}
				consoleOutput.append(inviter + "'s new resources:  " + newResources + "\n");
				output.println("updateResource:" + newResources);  //Sending the command
				output.flush();
				//Updating the invitee's resource string
				newResources = "";
				for (int i = 0; i < inviteeResources.length; i++){
					newResources += inviteeResources[i] + ",";
				}
				//Finding the invitee
				for (int i = 0; i < connections.size(); i++){
					if (connections.get(i).getName().equals(invitee)){
						consoleOutput.append(invitee + "'s new resources:  " + newResources + "\n");
						connections.get(i).output("updateResource:" + newResources);  //Outputting to the invitee
						break;
					}
				}
			} else if (command.equals("rejectTrade")){
				//Getting the inviter's username
				msg = msg.substring(msg.indexOf(",") + 1);
				String inviter = msg;
				//Finding the inviter
				for (int i = 0; i < connections.size(); i++){
					if (connections.get(i).getName().equals(inviter)){
						consoleOutput.append(inviter + "'s trade was rejected\n");
						connections.get(i).output("traderejected");  //Sending the rejection message
						break;
					}
				}
			}
		}
		
		/**
		 * Method to return name of connection
		 * 
		 * @param Nothing
		 * @return Name of the connection
		 */
		public String getName(){
			return this.name;
		}
		
		/**
		 * Method to send out a message to the client
		 * 
		 * @param msg Message to be sent
		 * @return Nothing
		 */
		public void output(String msg){
			output.println(msg);
			output.flush();
		}

	}
	
	public void battle(Player player1, Player player2) {
		
	}

}

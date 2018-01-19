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
import java.util.Queue;
import java.util.LinkedList;

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
	ArrayList<Planet> planets;
	SpaceDepot depot;
	Queue battleCommands = new LinkedList<String>();

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
		onlinePlayers = new ArrayList<Player>(); 
		connections = new ArrayList<PlayerConnection>();
		depot = new SpaceDepot();
		planets = new ArrayList<Planet>();
		//Creating the planets
		long time = System.nanoTime();
		planets.add(new Planet("Yarn Planet", time, this));
		planets.add(new Planet("Flat Planet", time, this));
		planets.add(new Planet("Potato Planet", time, this));
		planets.add(new Planet("Speckle Planet", time, this));
		planets.add(new Planet("Fractured Planet", time, this));
		planets.add(new Planet("Jupiter Planet", time, this));
		planets.add(new Planet("Moon Planet", time, this));
		planets.add(new Planet("Bouncy Planet", time, this));
		planets.add(new Planet("Basketball Planet", time, this));
		planets.add(new Planet("Saturn Planet", time, this));
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
			serverSocket = new ServerSocket(798);
			while (running) {
				client = serverSocket.accept(); // Creating the client socket
				consoleOutput.append("Client connected\n");
				PlayerConnection tempConnection = new PlayerConnection(client);
				connections.add(tempConnection); // Adding to the arraylist of connections
				Thread clientThread = new Thread(tempConnection);
				clientThread.start(); // Starting the client thread
			}
		} catch (Exception e) {
			consoleOutput.append("Connection failed\n");
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to start battle
	 * @param player1 First player in battle
	 * @param player2 Second player in battle
	 * @return nothing
	 */
	public void battle(Player player1, Player player2) {
		//Creating the thread for a battle
		BattleHandler battleHandler = new BattleHandler(player1, player2);
		Thread battleThread = new Thread(battleHandler);
		battleThread.start();  //Starting the battle thread
	}
	
	/**
	 * Battle Handler
	 */
	class BattleHandler implements Runnable{
		
		//Player variables
		Player player1, player2;
		PlayerConnection connection1, connection2;
		
		/**
		 * Constructor for BattleHandler
		 */
		BattleHandler(Player player1, Player player2){
			this.player1 = player1;
			this.player2 = player2;
			//Finding the input and outputs of both players
			for (int i = 0; i < connections.size(); i++) {
				if (connections.get(i).getName().equals(player1.getUsername())){
					connection1 = connections.get(i);
				} else if (connections.get(i).getName().equals(player2.getUsername())) {
					connection2 = connections.get(i);
				}
			}
		}
		
		/**
		 * Main run method
		 * @param Nothing
		 * @return Nothing
		 */
		public void run() {
			//Run while the ships have not been destroyed
			while(player1.getShip().getHealth() > 0 && player2.getShip().getHealth() > 0) {
				if (battleCommands.size() > 0) {
					String msg = (String)battleCommands.poll();  //Getting the latest command
					//Splitting up the client message
					String command = msg.substring(0, msg.indexOf(":"));
					msg = msg.substring(msg.indexOf(":") + 1);
					if (command.equals("attack")) {
						//Player variables
						String username = msg.substring(0, msg.indexOf(","));
						Weapon weapon = new Weapon();
						Module[] modules = new Module[5];
						ShieldModule shield;
						//Finding the player
						for (int i = 0; i < onlinePlayers.size(); i++) {
							if (onlinePlayers.get(i).getUsername().equals(username)) {
								modules = onlinePlayers.get(i).getShip().getModules();
								if (msg.substring(msg.indexOf(",") + 1).equals("1")) {
									weapon = ((WeaponModule)modules[2]).getWeapon1();
								} else if (msg.substring(msg.indexOf(",") + 1).equals("2")) {
									weapon = ((WeaponModule)modules[2]).getWeapon2();
								} else if (msg.substring(msg.indexOf(",") + 1).equals("3")) {
									weapon = ((WeaponModule)modules[2]).getWeapon3();
								}
							}
						}
						//Finding the other player and getting their shield
						if (player1.getUsername().equals(username)) {
							Module[] modules2 = player2.getShip().getModules();
							shield = (ShieldModule)modules2[2];
						} else {
							Module[] modules2 = player1.getShip().getModules();
							shield = (ShieldModule)modules2[2];
						}
						//Calculating the damage to be dealt
						int shieldRand = (int)(Math.random() * 100);
						//If the attack bypasses the shield
						if (shieldRand > shield.getDeflection()) {
							//Finding what type of weapon it is and redeclaring it
							if (weapon instanceof Laser) {
								int accuracyRand = (int)(Math.random() * 100);
								if (accuracyRand < ((Laser)weapon).getAccuracy()) {
									//Removing health from the player
									if (player1.getUsername().equals(username)) {
										player2.getShip().removeHealth(((Laser)weapon).getDamage());
										connection2.output("damage:" + ((Laser)weapon).getDamage());
									} else {
										player1.getShip().removeHealth(((Laser)weapon).getDamage());
										connection1.output("damage:" + ((Laser)weapon).getDamage());
									}
								}
							} else if (weapon instanceof Missile) {
								int accuracyRand = (int)(Math.random() * 100);
								if (accuracyRand < ((Missile)weapon).getAccuracy()) {
									//Removing health from the player
									if (player1.getUsername().equals(username)) {
										player2.getShip().removeHealth(((Missile)weapon).getDamage());
										connection2.output("damage:" + ((Laser)weapon).getDamage());
									} else {
										player1.getShip().removeHealth(((Missile)weapon).getDamage());
										connection1.output("damage:" + ((Laser)weapon).getDamage());
									}
								}
							} else if (weapon instanceof ShieldJammer) {
								int jamChance = ((ShieldJammer)weapon).getJamRate();
								int jamRand = (int)(Math.random() * 100);
								//If the shield jammer works
								if (jamRand < jamChance) {
									shield.setDeflection(0);  //Jamming the shield
									//Sending the shield disabled command
									if (player1.getUsername().equals(username)) {
										connection2.output("shieldDisabled");
									} else {
										connection1.output("shieldDisabled");
									}
								}
							}
						}
						//End of turn
						connection1.output("endTurn");
						connection2.output("endTurn");
					}
				}
			}
			//After the battle is complete
			if (player1.getShip().getHealth() > 0) {
				//Sending the results to the client
				connection1.output("battleWinner");
				connection2.output("battleLoser");
			} else if (player2.getShip().getHealth() > 0){
				//Sending the results to the client
				connection2.output("battleWinner");
				connection1.output("battleLoser");
			}
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
		 * Getter for the input
		 * @param Nothing
		 * @return BufferedReader The client's input
		 */
		public BufferedReader getInput() {
			return this.input;
		}
		
		/**
		 * Getter for the output
		 * @param Nothing
		 * @return PrintWriter The client's output
		 */
		public PrintWriter getOutput() {
			return this.output;
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
						//Closing all the streams
						try {
							client.close();
							input.close();
							output.close();
						} catch (IOException e) {
							consoleOutput.append("Streams failed to close\n");
						}
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
			} else if (command.equals("tradeOffer")){
				//Getting the invitee
				String tempMsg = msg;
				tempMsg = tempMsg.substring(tempMsg.indexOf(",") + 1);
				String invitee = tempMsg.substring(0, tempMsg.indexOf(","));
				//Finding the user
				for (int i = 0; i < connections.size(); i++){
					if (connections.get(i).getName().equals(invitee)){
						consoleOutput.append(invitee + "was sent a trade:  " + msg + "\n");
						connections.get(i).output("trade:" + msg);  //Outputting to the invitee
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
			} else if (command.equals("upgrade")){
				//Getting the information
				String username = msg.substring(0, msg.indexOf(","));
				msg = msg.substring(msg.indexOf(",") + 1);
				String module = msg;
				//Finding the user
				for (int i = 0; i < onlinePlayers.size(); i++){
					//Checking if the player is at the depot
					if (onlinePlayers.get(i).getUsername().equals(username) && onlinePlayers.get(i).getLocation().equals("depot")){
						//Getting the player variables needed for upgrading
						Player tempPlayer = onlinePlayers.get(i);
						Ship tempShip = tempPlayer.getShip();
						Module[] tempModules = tempShip.getModules();
						int[] tempResources = tempPlayer.getResources();
						//Finding which module to work with
						if (module.equals("engineModule")){
							EngineModule tempEngine = (EngineModule)tempModules[0];
							//Checking if the player has enough resources
							if (tempResources[0] > tempEngine.getSteel() && tempResources[1] > tempEngine.getGraphene()){
								if (tempResources[2] > tempEngine.getPlut()){
									onlinePlayers.get(i).getShip().upgradeEngineModule();
									//Removing the resources from the player
									onlinePlayers.get(i).changeResources(0, tempResources[0] - tempEngine.getSteel());
									onlinePlayers.get(i).changeResources(1, tempResources[1] - tempEngine.getGraphene());
									onlinePlayers.get(i).changeResources(2, tempResources[2] - tempEngine.getPlut());
									//Outputting the success message to client
									output.println("upgradeSuccessful");
									output.flush();
									//Updating the client with new resources
									String newResources = "";
									//Adding resources to the string
									for (int j = 0; j < onlinePlayers.get(i).getResources().length; j++) {
										newResources += Integer.toString(onlinePlayers.get(i).getResources()[j]) + ",";
									}
									//Sending the resource update
									output.println("updateResource:" + newResources);
									output.flush();
								} else {
									//Outputting the fail message to client
									output.println("upgradeFailed");
									output.flush();
								}
							} else {
								//Outputting the fail message to client
								output.println("upgradeFailed");
								output.flush();
							}
						} else if (module.equals("miningModule")){
							MiningModule tempMining = (MiningModule)tempModules[1];
							//Checking if the player has enough resources
							if (tempResources[0] > tempMining.getSteel() && tempResources[1] > tempMining.getGraphene()){
								if (tempResources[5] > tempMining.getCrystal()){
									onlinePlayers.get(i).getShip().upgradeMiningModule();
									//Removing the resources from the player
									onlinePlayers.get(i).changeResources(0, tempResources[0] - tempMining.getSteel());
									onlinePlayers.get(i).changeResources(1, tempResources[1] - tempMining.getGraphene());
									onlinePlayers.get(i).changeResources(5, tempResources[5] - tempMining.getCrystal());
									//Outputting the success message to client
									output.println("upgradeSuccessful");
									output.flush();
									//Updating the client with new resources
									String newResources = "";
									//Adding resources to the string
									for (int j = 0; j < onlinePlayers.get(i).getResources().length; j++) {
										newResources += Integer.toString(onlinePlayers.get(i).getResources()[j]) + ",";
									}
									//Sending the resource update
									output.println("updateResource:" + newResources);
									output.flush();
								} else {
									//Outputting the fail message to client
									output.println("upgradeFailed");
									output.flush();
								}
							} else {
								//Outputting the fail message to client
								output.println("upgradeFailed");
								output.flush();
							}
						} else if (module.equals("shieldModule")){
							ShieldModule tempShield = (ShieldModule)tempModules[2];
							//Checking if the player has enough resources
							if (tempResources[0] > tempShield.getSteel() && tempResources[1] > tempShield.getGraphene()){
								if (tempResources[3] > tempShield.getStarlite()){
									onlinePlayers.get(i).getShip().upgradeShieldModule();
									//Removing the resources from the player
									onlinePlayers.get(i).changeResources(0, tempResources[0] - tempShield.getSteel());
									onlinePlayers.get(i).changeResources(1, tempResources[1] - tempShield.getGraphene());
									onlinePlayers.get(i).changeResources(3, tempResources[3] - tempShield.getStarlite());
									//Outputting the success message to client
									output.println("upgradeSuccessful");
									output.flush();
									//Updating the client with new resources
									String newResources = "";
									//Adding resources to the string
									for (int j = 0; j < onlinePlayers.get(i).getResources().length; j++) {
										newResources += Integer.toString(onlinePlayers.get(i).getResources()[j]) + ",";
									}
									//Sending the resource update
									output.println("updateResource:" + newResources);
									output.flush();
								} else {
									//Outputting the fail message to client
									output.println("upgradeFailed");
									output.flush();
								}
							} else {
								//Outputting the fail message to client
								output.println("upgradeFailed");
								output.flush();
							}
						} else if (module.equals("weaponModule")){
							WeaponModule tempWeapon = (WeaponModule)tempModules[3];
							//Checking if the player has enough resources
							if (tempResources[0] > tempWeapon.getSteel() && tempResources[1] > tempWeapon.getGraphene()){
								if (tempResources[4] > tempWeapon.getPyro()){
									onlinePlayers.get(i).getShip().upgradeWeaponModule();
									//Removing the resources from the player
									onlinePlayers.get(i).changeResources(0, tempResources[0] - tempWeapon.getSteel());
									onlinePlayers.get(i).changeResources(1, tempResources[1] - tempWeapon.getGraphene());
									onlinePlayers.get(i).changeResources(4, tempResources[4] - tempWeapon.getPyro());
									//Outputting the success message to client
									output.println("upgradeSuccessful");
									output.flush();
									//Updating the client with new resources
									String newResources = "";
									//Adding resources to the string
									for (int j = 0; j < onlinePlayers.get(i).getResources().length; j++) {
										newResources += Integer.toString(onlinePlayers.get(i).getResources()[j]) + ",";
									}
									//Sending the resource update
									output.println("updateResource:" + newResources);
									output.flush();
								} else {
									//Outputting the fail message to client
									output.println("upgradeFailed");
									output.flush();
								}
							} else {
								//Outputting the fail message to client
								output.println("upgradeFailed");
								output.flush();
							}
						} else if (module.equals("deepSpaceViewer")){
							DeepSpaceViewer tempViewer = (DeepSpaceViewer)tempModules[4];
							//Checking if the player has enough resources
							if (tempResources[0] > tempViewer.getSteel() && tempResources[1] > tempViewer.getGraphene()){
								if (tempResources[6] > tempViewer.getIntellectium()){
									onlinePlayers.get(i).getShip().upgradeDeepSpaceViewer();
									//Removing the resources from the player
									onlinePlayers.get(i).changeResources(0, tempResources[0] - tempViewer.getSteel());
									onlinePlayers.get(i).changeResources(1, tempResources[1] - tempViewer.getGraphene());
									onlinePlayers.get(i).changeResources(6, tempResources[6] - tempViewer.getIntellectium());
									//Outputting the success message to client
									output.println("upgradeSuccessful");
									output.flush();
									//Updating the client with new resources
									String newResources = "";
									//Adding resources to the string
									for (int j = 0; j < onlinePlayers.get(i).getResources().length; j++) {
										newResources += Integer.toString(onlinePlayers.get(i).getResources()[j]) + ",";
									}
									//Sending the resource update
									output.println("updateResource:" + newResources);
									output.flush();
								} else {
									//Outputting the fail message to client
									output.println("upgradeFailed");
									output.flush();
								}
							} else {
								//Outputting the fail message to client
								output.println("upgradeFailed");
								output.flush();
							}
						}
					}
				}
			} else if (command.equals("attack")) {
				battleCommands.add(command + ":" + msg);  //Adding to the queue of battle commands
			} else if (command.equals("travel")) {
				//Getting the travel info
				String username = msg.substring(0, msg.indexOf(","));
				String destination = msg.substring(msg.indexOf(",") + 1);
				//Finding the planet
				for (int i = 0; i < planets.size(); i++) {
					if (planets.get(i).getName().equals(destination)) {
						//If the planet is available
						if (planets.get(i).canTravel()) {
							//Finding the player
							for (int k = 0; k < onlinePlayers.size(); k++) {
								if (onlinePlayers.get(k).getUsername().equals(username)) {
									Module[] playerModules = onlinePlayers.get(k).getShip().getModules();
									int travelTime =  30 * ((EngineModule)(playerModules[0])).getSpeed();
									//Outputting the travel time
									output.println(Integer.toString(travelTime));  //Outputting travel time in seconds
									output.flush();

								}
							}
						} else {
							//Outputting an error command
							output.println("-1"); 
							output.flush();
						}
						break;  //Leaving the loop
					}
				}
			} else if (command.equals("arrived")) {
				//Getting the travel info
				String username = msg.substring(0, msg.indexOf(","));
				String destination = msg.substring(msg.indexOf(",") + 1);
				//Adding the player to the planet
				for (int i = 0; i < planets.size(); i++) {
					if (planets.get(i).getName().equals(destination)) {
						//Finding the player
						for (int j = 0; j < onlinePlayers.size(); j++) {
							if (onlinePlayers.get(j).getUsername().equals(username)) {
								planets.get(i).addPlayer(onlinePlayers.get(j));  //Adding the player to the planet
							}
						}
					}
				}
			} else if (command.equals("mine")) {
				//Getting the info
				String username = msg.substring(0, msg.indexOf(","));
				String planet = msg.substring(msg.indexOf(",") + 1);
				int playerIndex = 0;
				//Finding the player
				for (int i = 0; i < onlinePlayers.size(); i++) {
					if (onlinePlayers.get(i).getUsername().equals(username)) {
						playerIndex = i;
					}
				}
				//Finding the planet
				for (int i = 0; i < planets.size(); i++) {
					if (planets.get(i).getName().equals(planet)) {
						long currentTime = System.nanoTime();
						int resourceAmount = planets.get(i).mine(currentTime);  //Mining the planet
						consoleOutput.append("resA:" + resourceAmount + "\n");
						planets.get(i).update(currentTime);  //Updating the planet
						//Adding resources to the player
						String resourceType = planets.get(i).getResourceType();
						if (resourceType.equals("Graphene")) {
							onlinePlayers.get(playerIndex).changeResources(1, ((onlinePlayers.get(playerIndex).getResources())[1]) + resourceAmount);
						} else if (resourceType.equals("Steel")) {
							onlinePlayers.get(playerIndex).changeResources(0, ((onlinePlayers.get(playerIndex).getResources())[0]) + resourceAmount);
						} else if (resourceType.equals("Intellectium")) {
							onlinePlayers.get(playerIndex).changeResources(6, ((onlinePlayers.get(playerIndex).getResources())[6]) + resourceAmount);
						} else if (resourceType.equals("Plutonium")) {
							onlinePlayers.get(playerIndex).changeResources(2, ((onlinePlayers.get(playerIndex).getResources())[2]) + resourceAmount);
						} else if (resourceType.equals("Starlite")) {
							onlinePlayers.get(playerIndex).changeResources(3, ((onlinePlayers.get(playerIndex).getResources())[3]) + resourceAmount);
						} else if (resourceType.equals("Blast Crystal")) {
							onlinePlayers.get(playerIndex).changeResources(5, ((onlinePlayers.get(playerIndex).getResources())[5]) + resourceAmount);
						} else if (resourceType.equals("Pyroxium")) {
							onlinePlayers.get(playerIndex).changeResources(4, ((onlinePlayers.get(playerIndex).getResources())[4]) + resourceAmount);
						}
						break; //Exiting the loop
					}
				}
				//Updating the client with new resources
				String newResources = "";
				//Adding resources to the string
				for (int i = 0; i < onlinePlayers.get(playerIndex).getResources().length; i++) {
					newResources += Integer.toString(onlinePlayers.get(playerIndex).getResources()[i]) + ",";
				}
				//Sending the resource update
				output.println("updateResource:" + newResources);
				output.flush();
			} else if (command.equals("playersUpdate")) {
				String username = msg;
				String players = "";
				for (int i = 0; i < onlinePlayers.size(); i++) {
					//Finding other players
					if (!(onlinePlayers.get(i).getUsername().equals(username))) {
						players += onlinePlayers.get(i).getUsername() + ",";  //Adding the player to the string
					}
				}
				//Outputting the players to the client
				consoleOutput.append(players + "\n");
				output.println("playersUpdate:" + players);
				output.flush();
			} else if (command.equals("shipUpdate")) {
				String username = msg;
				Module[] modules = new Module[5];
				String shipInfo = "";
				//Finding the player
				for (int i = 0; i < onlinePlayers.size(); i++) {
					if (onlinePlayers.get(i).getUsername().equals(username)) {
						Ship tempShip = onlinePlayers.get(i).getShip();  //Getting the player's ship
						modules = tempShip.getModules();  //Finding the modules
					}
				}
				//Getting the module info
				for (int i = 0; i < modules.length; i++) {
					shipInfo += modules[i].getUpgradeLevel() +",";
				}
				//Outputting the module info to the client
				consoleOutput.append(shipInfo + "\n");
				output.println("shipUpdate:" + shipInfo);
				output.flush();
			} else if (command.equals("repair")) {
				String username = msg;
				//Finding the player
				for (int i = 0; i < onlinePlayers.size(); i++) {
					if (onlinePlayers.get(i).getUsername().equals(username)) {
						int[] resources = onlinePlayers.get(i).getResources();
						if (resources[0] > 50 & resources[1] > 50 && resources[2] > 50 && resources[3] > 50) {
							onlinePlayers.get(i).getShip().setHealth(100);  //Setting health to max
							output.println("repairComplete");
							output.flush();
						}
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

}

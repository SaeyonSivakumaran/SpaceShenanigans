/**
 * SpaceServer.java
 * Class for the main server
 * @author Saeyon Sivakumaran
 */

//Imports for GUI
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.GridLayout;

//Imports for buttons
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//Networking imports
import java.net.Socket;
import java.net.ServerSocket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

//Game imports
import java.util.ArrayList;

class SpaceServer extends JFrame{
	
	//GUI Variables
	private JPanel mainPanel;
	private JPanel buttonPanel;
	private JTextArea consoleOutput;
	private JButton startButton;
	private JButton endButton;
	
	//Networking variables
	private boolean running = true;
	private ServerSocket serverSocket;
	
	//Game variables
	ArrayList<Player> players;
	ArrayList<Player> onlinePlayers;
	ArrayList<PlayerConnection> connections;
	SpaceDepot depot;
	
	/**
	 * Constructor for SpaceServer
	 */
	SpaceServer(){
		//Setting properties of the JFrame
		this.setSize(600,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Initializing all the game variables
		players = new ArrayList<Player>();
		connections = new ArrayList<PlayerConnection>();
		depot = new SpaceDepot();
		//Initializing all GUI components
		mainPanel = new JPanel(new BorderLayout());
		buttonPanel = new JPanel(new GridLayout(2,1));
		consoleOutput = new JTextArea();
		startButton = new JButton("Start Server");
		startButton.addActionListener(new startButtonListener());
		endButton = new JButton("Stop Server");
		endButton.addActionListener(new endButtonListener());
		//Adding buttons to the button panel
		buttonPanel.add(startButton);
		buttonPanel.add(endButton);
		//Adding components to main panel
		mainPanel.add(consoleOutput, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.PAGE_END);
		//Completing the main frame
		this.add(mainPanel);
		this.setVisible(true);
	}
	
	/**
	 * Listener for the start button
	 */
	class startButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			startServer();  //Starting the server
		}
	}
	
	/**
	 * Listener for the end button
	 */
	class endButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			consoleOutput.append("Server stopping\n");
			running = false;
		}
	}
	
	/**
	 * Method to start server
	 * @param Nothing
	 * @return Nothing
	 */
	public void startServer() {
		consoleOutput.append("Server started\n");
		Socket client = null;  //Socket for client
		//Waiting for connection
		try {
			serverSocket = new ServerSocket(5000);
			while (running) {
				client = serverSocket.accept();  //Creating the client socket
				System.out.println("Client connected");
				PlayerConnection tempConnection = new PlayerConnection(client);
				connections.add(tempConnection);  //Adding to the arraylist of connections
				Thread clientThread = new Thread(tempConnection);
				clientThread.start();  //Starting the client thread
			}
		} catch(Exception e) {
			System.out.println("Connection failed");
		}
	}
	
	/**
	 * Connection Handler for each client
	 */
	class PlayerConnection implements Runnable{
		
		//Networking variables
		Socket client;
		BufferedReader input;
		PrintWriter output;
		boolean running;
		
		/**
		 * Constructor for ConnectionHandler
		 */
		PlayerConnection(Socket socket){
			this.client = socket;
			//Getting connections
			try {
				InputStreamReader inputStream = new InputStreamReader(client.getInputStream());
				this.input = new BufferedReader(inputStream);
				this.output = new PrintWriter(client.getOutputStream());
			} catch(IOException e) {
				consoleOutput.append("Client connection failed\n");
			}
			running = true;  //Allowing the handler to run
		}
		
		/**
		 * Main run method
		 * @param Nothing
		 * @return Nothing
		 */
		public void run() {
			String msg;  //Client message
			while(running) {
				try {
					//If the client is sending a message
					if (input.ready()){
						msg = input.readLine();
						consoleOutput.append("Client message:" + msg + "\n");
						runCommand(msg);  //Running the command
					}
				} catch(IOException e) {
					//Message failed
					consoleOutput.append("Client message failed\n");
				}
			}
		}
		
		/**
		 * Method to check run commands
		 * @param Nothing
		 * @return Nothing
		 */
		public void runCommand(String msg) {
			String command = msg.substring(0, msg.indexOf(":"));  //Command variable
			msg = msg.substring(msg.indexOf(",") + 1);
			//Different functions for each commands
			if (command.equals("login")) {
				//Getting the command info
				String username = msg.substring(0, msg.indexOf(","));
				String password = msg.substring(msg.indexOf(",") + 1);
				boolean userFound = false;
				//Finding the user in the list of players
				for (int i = 0; i < players.size(); i++) {
					if (players.get(i).getUsername().equals(username)) {
						if (players.get(i).getPassword().equals(password)) {
							userFound = true;
							consoleOutput.append(username + " logged IN\n");
							onlinePlayers.add(players.get(i));
							//Sending confirmation to client
							output.println("loginaccepted");
							output.flush();
						}
				}
				}
				//If no user was found with the correct details
				if (userFound == false) {
					consoleOutput.append("Login failed");
					//Sending fail message to client
					output.println("loginfailed");
					output.flush();
				}
			} else if (command.equals("logout")) {
				String username = msg;
				//Removing the user from list of online users
				for (int i = 0; i < onlinePlayers.size(); i++) {
					if (onlinePlayers.get(i).getUsername().equals(username)) {
						onlinePlayers.remove(i);  //Removing the user
						consoleOutput.append(username + " logged OUT\n");
						break;
					}
				}
			} else if (command.equals("newaccount")) {
				//Getting the command info
				String username = msg.substring(0, msg.indexOf(","));
				String password = msg.substring(msg.indexOf(",") + 1);
				//Adding the new user
				players.add(new Player(username, password));
				consoleOutput.append(username + " has joined Space Shenanigans\n");
			}
		}
		
	}
	
}

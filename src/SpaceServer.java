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
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

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
	
	/**
	 * Constructor for SpaceServer
	 */
	SpaceServer(){
		//Setting properties of the JFrame
		this.setSize(600,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Initializing all GUI components
		mainPanel = new JPanel(new BorderLayout());
		buttonPanel = new JPanel(new GridLayout(2,1));
		consoleOutput = new JTextArea();
		consoleOutput.setEditable(false);
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
			startButton.setEnabled(false);
			start();  //Starting the server
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
	public void start() {
		consoleOutput.append("Server started\n");
		Socket client = null;  //Socket for client
		//Waiting for connection
		try {
			serverSocket = new ServerSocket(5000);
			while (running) {
				client = serverSocket.accept();
				System.out.println("Client connected");
				Thread clientThread = new Thread(new ConnectionHandler(client));
				clientThread.start();
			}
		} catch(Exception e) {
			System.out.println("Connection failed");
		}
	}
	
	/**
	 * Connection Handler for each client
	 */
	class ConnectionHandler implements Runnable{
		
		//Networking variables
		Socket client;
		BufferedReader input;
		PrintWriter output;
		
		/**
		 * Constructor for ConnectionHandler
		 */
		ConnectionHandler(Socket socket){
			this.client = socket;
			//Getting connections
			try {
				InputStreamReader inputStream = new InputStreamReader(client.getInputStream());
				this.input = new BufferedReader(inputStream);
				this.output = new PrintWriter(client.getOutputStream());
			} catch(IOException e) {
				consoleOutput.append("Client connection failed\n");
			}
		}
		
		/**
		 * Main run method
		 * @param Nothing
		 * @return Nothing
		 */
		public void run() {
			
		}
		
	}
	
}

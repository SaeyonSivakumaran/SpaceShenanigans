
import java.io.*;
import java.lang.Thread;
import java.net.*;
import java.util.Scanner;
import java.util.ArrayList;

public class SpaceClient {
	Socket mySocket; // socket for connection
	BufferedReader input; // reader for network stream
	PrintWriter output; // printwriter for network output
	boolean running = true; // thread status via boolean
	String command;
	String input1,input2;
	Scanner inputs;
	String username;
	int[] resources;
	private EngineModule engine;
	private ShieldModule shield;
	private WeaponModule weaponModule;
	private MiningModule miningModule;
	private DeepSpaceViewer deepSpaceViewer; 
	//Ship health
	private int health;
	MapPanel display;
	Queuee<String> instructions;
	ArrayList<String> players;


	public static void main(String[] args) {
		SpaceClient client = new SpaceClient(); // start the client
		client.go(); // begin the connection
	}

	public void getMap(MapPanel mapPanel) {
		this.display=mapPanel;
	}

	/**
	 * go This method sets up the login frame and calls messengerLaunch(which
	 * constructs the main frame but leaves it invisible)
	 * 
	 * @param N/A
	 * @return N/A
	 */
	public void go() {
		players=new ArrayList<String>();
		inputs=new Scanner(System.in);
		try {
			mySocket = new Socket("209.221.91.250", 5000); // attempt socket connection (local address)
			InputStreamReader stream1 = new InputStreamReader(mySocket.getInputStream()); // Stream for network input
			input = new BufferedReader(stream1);

			output = new PrintWriter(mySocket.getOutputStream()); // assign printwriter to network stream

			command=inputs.nextLine();
			while(running){
				if (command.equals("login")){

					input1=inputs.nextLine();
					input2=inputs.nextLine();
					output.println("login:"+input1+","+input2);
					output.flush();
					command=input.readLine();
					if (command.equals("loginAccepted")){
						this.username=input1;
						running=false;
					}
				}else if(command.equals("newAccount")){

					input1=inputs.nextLine();
					input2=inputs.nextLine();
					output.println("newAccount:"+input1+","+input2);
					output.flush();
					command=input.readLine();
					if (command.equals("accountValid")){
					}
				}
				this.engine = new EngineModule();
				this.shield = new ShieldModule();
				this.weaponModule = new WeaponModule();
				this.miningModule = new MiningModule();
				this.deepSpaceViewer = new DeepSpaceViewer();
				this.resources = new int[]{0, 0, 0, 0, 0, 0, 0};

				System.out.println("Connection made.");

			}




			Thread t= new Thread(new Input());
			t.start();
			
			output.println("playersUpdate:"+username);
			output.println("shipUpdate:"+username);
			
			running=true;
			instructions=new Queuee<String>();
			while(running){
				command=inputs.nextLine();
				if (command.equals("1")){
					input2=inputs.nextLine();
					output.println("travel:"+username+","+input2);
				}else if(command.equals("2")){
					output.println("arrived:"+username);	
				}else if(command.equals("3")){
					input2=inputs.nextLine();
					output.println("upgrade:"+username+","+input2);
				}else if(command.equals("4")){
					output.println("mine:"+username);
				}else if(command.equals("5")){
					input2=inputs.nextLine();
					output.println("tradeInfoWanted:"+username+","+input2);
				}else if(command.equals("6")){
					input1=inputs.nextLine();
					input2="";
					while(!input1.equals(" ")){
						input2+=input1;
					}
					input1=inputs.nextLine();
					output.println("trade:"+username+","+input1);
				}else if(command.equals("7")){
					output.println("acceptTrade:"+input1);
				}else if(command.equals("8")){
					output.println("rejectTrade:"+input1);
				}else if(command.equals("9")){
					input1=inputs.nextLine();
					output.println("attack:"+username+","+input2);
				}else if(command.equals("10")){
					output.println("logout:"+input1);
					running=false;
				}else if(command.equals("11")){
					output.println("logout:"+input1);
					running=false;
				}
			}

			try {  
				input.close();
				output.close();
				mySocket.close();
			}catch (Exception e) { 
				System.out.println("Failed to close socket");
			}
		} catch (IOException e) { // connection error occured
			System.out.println("Connection to Server Failed");
			e.printStackTrace();
		}

	}

	class Input implements Runnable{
		/**
		 * receive This method continually loops and receives data from the server
		 * 
		 * @param N/A
		 * @return N/A
		 */
		public void run() {
			// after connecting loop and keep appending[.append()] to the JTextArea

			while (running) { // loop continually receives and processes messages coming from the server
				try {
					if (input.ready()) { // check for an incoming messge
						String msg = input.readLine(); // read the message

						// decipher server messages
						if (msg.substring(0,msg.indexOf(":")).equals("travel")) {
							command=msg.substring(msg.indexOf(":")+1);
							//send to map
						}else if (msg.substring(0,msg.indexOf(":")).equals("upgrade")) {
							command=msg.substring(msg.indexOf(":")+1);
							//send to map
						}else if (msg.substring(0,msg.indexOf(":")).equals("mine")) {

							command=msg.substring(msg.indexOf(":")+1);
							//send to map
						}else if (msg.substring(0,msg.indexOf(":")).equals("inventory")) {

							command=msg.substring(msg.indexOf(":")+1);
							//send to map
						}else if (msg.substring(0,msg.indexOf(":")).equals("updateResource")) {

							command=msg.substring(msg.indexOf(":")+1);
							//send to map
						}else if (msg.substring(0,msg.indexOf(":")).equals("battle")) {

							command=msg.substring(msg.indexOf(":")+1);
							//send to map
						}else if (msg.substring(0,msg.indexOf(":")).equals("playersUpdate")) {

							command=msg.substring(msg.indexOf(":")+1);
							while(command.length()>1){
								players.add(command.substring(0,command.indexOf(",")));
								command=command.substring(command.indexOf(",")+1);
							}
						}else if (msg.substring(0,msg.indexOf(":")).equals("shipUpdate")) {

							command=msg.substring(msg.indexOf(":")+1);
							//send to map
						}
					}

				} catch (IOException e) {
					System.out.println("Failed to receive msg from the server");
					e.printStackTrace();
				}
			}

		}
	}
	public void sendCommand(String command) {
		this.instructions.enqueue(command);
	}
}

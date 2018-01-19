/**
 * SpaceClient.java
 * class for the main client side program
 * @author carl zhang (not for inner graphics classes)
 * @version 2
 */

/*
 * To login: (enter in separate lines)
 * type newaccount
 * type *username*
 * type *password*
 * type login
 * type *username*
 * type *password*
 */

//imports
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.IOException;
import java.lang.Thread;
import java.net.Socket;
import java.util.Scanner;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.MatteBorder;

//main class
public class SpaceClient {
	
	//vars
	Socket mySocket; // socket for connection
	BufferedReader input; // reader for network stream
	PrintWriter output; // printwriter for network output
	boolean running = true; // thread status via boolean
	String command;//string used to save commands from user
	Queuee<String> commandd;//queue of user commands
	String input1, input2;//strings for saving multiple user messages
	Scanner inputs;//scanner for console input
	Queuee<String> inputss;//queue for user messages
	String username = "";//string for username
	int[] resources;//array of resources
	private EngineModule engine;//
	private ShieldModule shield;
	private WeaponModule weaponModule;
	private MiningModule miningModule;
	private DeepSpaceViewer deepSpaceViewer;//all of the modules
	private Ship shipp;//player's ship
	private Planet yarnPlanet;
	private Planet flatEarth;
	private Planet potatoPlanet;
	private Planet specklePlanet;
	private Planet fracturedPlanet;
	private Planet jupiter;
	private Planet moonPlanet;
	private Planet bouncyPlanet;
	private Planet basketballPlanet;
	private Planet saturnPlanet;//all of the planets
	// Ship health
	private int health;
	MapPanel display;//panel for display
	Queuee<String> instructions;//queue of instructions from server
	ArrayList<String> players;//arraylist of online players
	String location;//location of player
	static JFrame frame;//frame for display
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//dimensions
	int screenX = (int) screenSize.getWidth();//screen size
	int screenY = (int) screenSize.getHeight();

	ImageIcon buttonIcon = null;
	ImageIcon blastCrystalIcon = null;
	ImageIcon grapheneIcon = null;
	ImageIcon intellectiumIcon = null;
	ImageIcon plutoniumIcon = null;
	ImageIcon pyroxiumIcon = null;
	ImageIcon starliteIcon = null;
	ImageIcon steelIcon = null;
	ImageIcon repairIcon = null;
	ImageIcon shieldJammerIcon = null;
	ImageIcon laserIcon = null;
	ImageIcon missileIcon = null;//all of the buttons on the screen
	BufferedImage ship = null;
	BufferedImage laser = null;
	BufferedImage shieldJammer = null;
	BufferedImage missile = null;
	BufferedImage engine1 = null;
	BufferedImage engine2 = null;
	BufferedImage engine3 = null;
	BufferedImage engine4 = null;
	BufferedImage engine5 = null;
	BufferedImage userShip = null;//images for all objects
	
	Image hyperImage = null;
	Image backgroundImage = null;
	Image shipImage = null;//images for backgrounds

	//constructor
	SpaceClient() {
		try { // loading images
			shipp=new Ship();
			blastCrystalIcon = new ImageIcon(ImageIO.read(new File("BlastCrystal.png")));
			grapheneIcon = new ImageIcon(ImageIO.read(new File("Graphene.png")));
			intellectiumIcon = new ImageIcon(ImageIO.read(new File("Intellectium.png")));
			plutoniumIcon = new ImageIcon(ImageIO.read(new File("Plutonium.png")));
			pyroxiumIcon = new ImageIcon(ImageIO.read(new File("Pyroxium.png")));
			starliteIcon = new ImageIcon(ImageIO.read(new File("Starlite.png")));
			steelIcon = new ImageIcon(ImageIO.read(new File("Steel.png")));
			buttonIcon = new ImageIcon(ImageIO.read(new File("Button.png")));
			repairIcon = new ImageIcon(ImageIO.read(new File("RepairButton.png")));
			shieldJammerIcon = new ImageIcon(ImageIO.read(new File("ShieldJammer.png")));
			laserIcon = new ImageIcon(ImageIO.read(new File("Laser.png")));
			missileIcon = new ImageIcon(ImageIO.read(new File("MissileLauncher.png")));
			ship = ImageIO.read(new File("SpaceShip.png"));
			engine1 = ImageIO.read(new File("EngineModule1.png"));
			engine2 = ImageIO.read(new File("EngineModule2.png"));
			engine3 = ImageIO.read(new File("EngineModule3.png"));
			engine4 = ImageIO.read(new File("EngineModule4.png"));
			engine5 = ImageIO.read(new File("EngineModule5.png"));
			laser = ImageIO.read(new File("Laser.png"));
			missile = ImageIO.read(new File("MissileLauncher.png"));
			shieldJammer = ImageIO.read(new File("ShieldJammer.png"));
			hyperImage = new ImageIcon("SpaceBackground.png").getImage();
			backgroundImage = new ImageIcon("SpaceMap.png").getImage();
		} catch (Exception ex) {
			System.out.println("image didn't load");
		}
		backgroundImage = backgroundImage.getScaledInstance(screenX, screenY, Image.SCALE_DEFAULT); // resize space background to fit screen
	}

	/**
	 * main
	 * main class
	 */
	public static void main(String[] args) {
		SpaceClient client = new SpaceClient(); // start the client
		client.go(); // begin the connection
	}


	/**
	 * go 
	 * This method sets up the login frame and calls messengerLaunch(which
	 * constructs the main frame but leaves it invisible)
	 * @param void
	 * @return void
	 */
	public void go() {
		players = new ArrayList<String>();
		inputs = new Scanner(System.in);
		commandd = new Queuee<String>();
		inputss = new Queuee<String>();
		this.engine = new EngineModule();
		this.shield = new ShieldModule();
		this.weaponModule = new WeaponModule();
		this.miningModule = new MiningModule();
		this.deepSpaceViewer = new DeepSpaceViewer();
		this.health = 100;
		this.location = "depot";//constructs all objects
		try {
			mySocket = new Socket("127.0.0.1", 798); // attempt socket connection (local address)
			InputStreamReader stream1 = new InputStreamReader(mySocket.getInputStream()); // Stream for network input
			input = new BufferedReader(stream1);

			output = new PrintWriter(mySocket.getOutputStream()); // assign printwriter to network stream

			
			while (username.length() == 0) {//while no username logged in with
				command = inputs.nextLine();//get user input
				if (command.equals("login")) {

					input1 = inputs.nextLine();
					input2 = inputs.nextLine();
					output.println("login:" + input1 + "," + input2);
					output.flush();//send to server
					command = input.readLine();//gets server response
					if (command.equals("loginaccepted")) {
						this.username = input1;
						System.out.println("Connection made.");//if login successful set username
					} else {
						System.out.println("login failed");
					}
				} else if (command.equals("newaccount")) {//making new account

					input1 = inputs.nextLine();
					input2 = inputs.nextLine();//get user input
					output.println("newaccount:" + input1 + "," + input2);//send to server
					output.flush();
					command = input.readLine();//get server response
					if (command.equals("accountvalid")) {
						System.out.println("account valid");
					} else {
						System.out.println("account invalid");//indicates whether the account can be created
					}
				}
				this.engine = new EngineModule();
				this.shield = new ShieldModule();
				this.weaponModule = new WeaponModule();
				this.miningModule = new MiningModule();
				this.deepSpaceViewer = new DeepSpaceViewer();
				this.resources = new int[] { 0, 0, 0, 0, 0, 0, 0 };//initiates modules and resources

			}

			Thread t = new Thread(new Input());
			t.start();//start thread for receiving server input

			frame = new JFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setContentPane(new MapPanel());
			frame.setPreferredSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
					(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
			frame.pack();
			frame.setResizable(false);
			frame.setVisible(true);//start the main frame

			output.println("playersUpdate:" + username);
			output.flush();
			output.println("shipUpdate:" + username);
			output.flush();//asks server for update on online players and player's ship
			running = true;
			while (running) {//main game loop

				// System.out.println("input2");

				command = commandd.dequeue();//take user command
				if (command == null) {
					command = "";
				}
				System.out.print(command);
				if (command.equals("1")) {//if command is travel
					input2 = inputss.dequeue();//take location
					output.println("travel:" + username + "," + input2);//send to server
					// output.flush();
				} else if (command.equals("2")) {//if command is arrived
					input2 = inputss.dequeue();//take location
					output.println("arrived:" + username + "," + input2);//send to server
				} else if (command.equals("3")) {//if command is upgrade
					input2 = inputs.nextLine();//take upgrade module
					output.println("upgrade:" + username + "," + input2);//send to server
				} else if (command.equals("4")) {//if command is mine
					input2 = inputss.dequeue();//take planet
					output.println("mine:" + username + "," + input2);//send to server
					userShip = resizeImage(combineSpaceship(engine.getUpgradeLevel()), screenX/4, (screenX/4)/3);
				} else if (command.equals("5")) {//if command is asking for player inventory
					input2 = inputs.nextLine();//take player name
					output.println("tradeInfoWanted:" + username + "," + input2);//send to server
				} else if (command.equals("6")) {//if command is sending trade offer
					input1 = inputs.nextLine();//take resource offer
					input2 = "";
					while (!input1.equals(" ")) {
						input2 += input1;
					}
					input1 = inputs.nextLine();
					output.println("trade:" + username + "," + input1);//send to server
				} else if (command.equals("7")) {//if command is accept trade offer
					output.println("acceptTrade:" + input1);//send to server
				} else if (command.equals("8")) {//if command is reject offer
					output.println("rejectTrade:" + input1);//send to server
				} else if (command.equals("9")) {//if command is attack
					input1 = inputs.nextLine();//take weapon used
					output.println("attack:" + username + "," + input2);//send to server
				} else if (command.equals("10")) {//if command is logout
					output.println("logout:" + username);//send to server
					running = false;
				}
				output.flush();
				command = "";

				// System.out.print(1);
			}

			try {
				input.close();
				output.close();
				mySocket.close();
				System.out.println("im ey lamo");
			} catch (Exception e) {
				System.out.println("Failed to close socket");
			}
		} catch (IOException e) { // connection error occured
			System.out.println("Connection to Server Failed");
			e.printStackTrace();
		}

	}

	/**
	 * Input.class
	 * class for server inputs
	 * @author Carl Zhang
	 *
	 */
	class Input implements Runnable {
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
						System.out.println(msg);
						// decipher server messages
						if (msg.indexOf(":") != -1) {
							if (msg.equals("upgradeSuccessful")) {
								msg = msg.substring(msg.indexOf(":") + 1);
								switch (Integer.parseInt(msg.substring(0, msg.indexOf(",")))) {
								case 1:
									engine.upgrade();
								case 2:
									shield.upgrade();
								case 3:
									weaponModule.upgrade();
								case 4:
									miningModule.upgrade();
								case 5:
									deepSpaceViewer.upgrade();//server send upgrade, upgrade module
								}
							
							} else if (msg.substring(0, msg.indexOf(":")).equals("inventory")) {

								msg = msg.substring(msg.indexOf(":") + 1);//server sends inventory of player
								// send to map
								// display offer
								// send back response
							} else if (msg.substring(0, msg.indexOf(":")).equals("updateResource")) {
								msg = msg.substring(msg.indexOf(":") + 1);
								for (int i=0; i<resources.length;i++) {
									resources[i]=(Integer.parseInt(msg.substring(0, msg.indexOf(","))));
									msg = msg.substring(msg.indexOf(",") + 1);//server sends resource update for player
								}
							} else if (msg.substring(0, msg.indexOf(":")).equals("battle")) {
								msg = msg.substring(msg.indexOf(":") + 1);
								health -= Integer.parseInt(msg);//server sends health lost from battle
							} else if (msg.substring(0, msg.indexOf(":")).equals("playersUpdate")) {

								msg = msg.substring(msg.indexOf(":") + 1);
								players.clear();//server sends update of online player list
								while (msg.length() > 1) {
									players.add(msg.substring(0, msg.indexOf(",")));
									msg = msg.substring(msg.indexOf(",") + 1);
								}
							} else if (msg.substring(0, msg.indexOf(":")).equals("shipUpdate")) {

								msg = msg.substring(msg.indexOf(":") + 1);
								engine.setLevel(Integer.parseInt(msg.substring(0, msg.indexOf(","))));
								msg = msg.substring(msg.indexOf(",") + 1);
								shield.setLevel(Integer.parseInt(msg.substring(0, msg.indexOf(","))));
								msg = msg.substring(msg.indexOf(",") + 1);
								weaponModule.setLevel(Integer.parseInt(msg.substring(0, msg.indexOf(","))));
								msg = msg.substring(msg.indexOf(",") + 1);
								miningModule.setLevel(Integer.parseInt(msg.substring(0, msg.indexOf(","))));
								msg = msg.substring(msg.indexOf(",") + 1);
								deepSpaceViewer.setLevel(Integer.parseInt(msg.substring(0, msg.indexOf(","))));//server sends update of ship modules
							} else if (msg.substring(0, msg.indexOf(":")).equals("planetsUpdate")) {
								msg = msg.substring(msg.indexOf(":") + 1);
								yarnPlanet.setResource(Integer.parseInt(msg.substring(0, msg.indexOf(","))));
								msg = msg.substring(msg.indexOf(",") + 1);
								flatEarth.setResource(Integer.parseInt(msg.substring(0, msg.indexOf(","))));
								msg = msg.substring(msg.indexOf(",") + 1);
								potatoPlanet.setResource(Integer.parseInt(msg.substring(0, msg.indexOf(","))));
								msg = msg.substring(msg.indexOf(",") + 1);
								specklePlanet.setResource(Integer.parseInt(msg.substring(0, msg.indexOf(","))));
								msg = msg.substring(msg.indexOf(",") + 1);
								fracturedPlanet.setResource(Integer.parseInt(msg.substring(0, msg.indexOf(","))));
								msg = msg.substring(msg.indexOf(",") + 1);
								jupiter.setResource(Integer.parseInt(msg.substring(0, msg.indexOf(","))));//server sends update of planet resources
								if (msg.substring(0, msg.indexOf(":")).equals("upgrade")) {
									msg = msg.substring(msg.indexOf(":") + 1);
									switch (Integer.parseInt(msg.substring(0, msg.indexOf(",")))) {
									case 1:
										engine.upgrade();
									case 2:
										shield.upgrade();
									case 3:
										weaponModule.upgrade();
									case 4:
										miningModule.upgrade();
									case 5:
										deepSpaceViewer.upgrade();//server sends module updates
									}
								} else if (msg.substring(0, msg.indexOf(":")).equals("mine")) {
									msg = msg.substring(msg.indexOf(":") + 1);
									resources[Integer.parseInt(msg.substring(0, msg.indexOf(",")))] += Integer
											.parseInt(msg.substring(msg.indexOf(",") + 1));
									if (location.equals("Yarn Planet")) {
										yarnPlanet.setResource(yarnPlanet.getResource() - Integer.parseInt(msg));
									} else if (location.equals("Flat Planet")) {
										flatEarth.setResource(flatEarth.getResource() - Integer.parseInt(msg));
									} else if (location.equals("Potato Planet")) {
										potatoPlanet.setResource(potatoPlanet.getResource() - Integer.parseInt(msg));
									} else if (location.equals("Speckle Planet")) {
										specklePlanet.setResource(specklePlanet.getResource() - Integer.parseInt(msg));
									} else if (location.equals("Fractured Planet")) {
										fracturedPlanet.setResource(fracturedPlanet.getResource() - Integer.parseInt(msg));
									} else if (location.equals("Jupiter Planet")) {
										jupiter.setResource(jupiter.getResource() - Integer.parseInt(msg));
									} else if (location.equals("Moon Planet")) {
										moonPlanet.setResource(moonPlanet.getResource() - Integer.parseInt(msg));
									} else if (location.equals("Bouncy Planet")) {
										bouncyPlanet.setResource(bouncyPlanet.getResource() - Integer.parseInt(msg));
									} else if (location.equals("Basketball Planet")) {
										basketballPlanet.setResource(basketballPlanet.getResource() - Integer.parseInt(msg));
									} else if (location.equals("Saturn Planet")) {
										saturnPlanet.setResource(saturnPlanet.getResource() - Integer.parseInt(msg));//server sends mining
									}
								} else if (msg.substring(0, msg.indexOf(":")).equals("inventory")) {

									msg = msg.substring(msg.indexOf(":") + 1);
									// send to map
									// display offer
									// send back response
								} else if (msg.substring(0, msg.indexOf(":")).equals("updateResource")) {

									if (msg.substring(0, msg.indexOf(",")).equals("yarnPlanet")) {
										yarnPlanet.setResource(Integer.parseInt(msg.substring(msg.indexOf(","))));
									} else if (msg.substring(0, msg.indexOf(",")).equals("flatEarth")) {
										flatEarth.setResource(Integer.parseInt(msg.substring(msg.indexOf(","))));
									} else if (msg.substring(0, msg.indexOf(",")).equals("potatoPlanet")) {
										potatoPlanet.setResource(Integer.parseInt(msg.substring(msg.indexOf(","))));
									} else if (msg.substring(0, msg.indexOf(",")).equals("specklePlanet")) {
										specklePlanet.setResource(Integer.parseInt(msg.substring(msg.indexOf(","))));
									} else if (msg.substring(0, msg.indexOf(",")).equals("fracturedPlanet")) {
										fracturedPlanet.setResource(Integer.parseInt(msg.substring(msg.indexOf(","))));
									} else if (msg.substring(0, msg.indexOf(",")).equals("jupiter")) {
										jupiter.setResource(Integer.parseInt(msg.substring(msg.indexOf(","))));
									} else if (msg.substring(0, msg.indexOf(",")).equals("moonPlanet")) {
										moonPlanet.setResource(Integer.parseInt(msg.substring(msg.indexOf(","))));
									}
								} else if (msg.substring(0, msg.indexOf(":")).equals("battle")) {
									msg = msg.substring(msg.indexOf(":") + 1);
									health -= Integer.parseInt(msg);
								} else if (msg.substring(0, msg.indexOf(":")).equals("playersUpdate")) {

									msg = msg.substring(msg.indexOf(":") + 1);
									players.clear();
									while (msg.length() > 1) {
										players.add(msg.substring(0, msg.indexOf(",")));
										msg = msg.substring(msg.indexOf(",") + 1);
									}
								} else if (msg.substring(0, msg.indexOf(":")).equals("shipUpdate")) {

									msg = msg.substring(msg.indexOf(":") + 1);
									shipp.setEngineModule(Integer.parseInt(msg.substring(0, msg.indexOf(","))));
									msg = msg.substring(msg.indexOf(",") + 1);
									shipp.setShieldModule(Integer.parseInt(msg.substring(0, msg.indexOf(","))));
									msg = msg.substring(msg.indexOf(",") + 1);
									shipp.setWeaponModule(Integer.parseInt(msg.substring(0, msg.indexOf(","))));
									msg = msg.substring(msg.indexOf(",") + 1);
									shipp.setMiningModule(Integer.parseInt(msg.substring(0, msg.indexOf(","))));
									msg = msg.substring(msg.indexOf(",") + 1);
									shipp.setDeepSpaceViewer(Integer.parseInt(msg.substring(0, msg.indexOf(","))));
								} else if (msg.substring(0, msg.indexOf(":")).equals("planetsUpdate")) {
									msg = msg.substring(msg.indexOf(":") + 1);
									yarnPlanet.setResource(Integer.parseInt(msg.substring(0, msg.indexOf(","))));
									msg = msg.substring(msg.indexOf(",") + 1);
									flatEarth.setResource(Integer.parseInt(msg.substring(0, msg.indexOf(","))));
									msg = msg.substring(msg.indexOf(",") + 1);
									potatoPlanet.setResource(Integer.parseInt(msg.substring(0, msg.indexOf(","))));
									msg = msg.substring(msg.indexOf(",") + 1);
									specklePlanet.setResource(Integer.parseInt(msg.substring(0, msg.indexOf(","))));
									msg = msg.substring(msg.indexOf(",") + 1);
									fracturedPlanet.setResource(Integer.parseInt(msg.substring(0, msg.indexOf(","))));
									msg = msg.substring(msg.indexOf(",") + 1);
									jupiter.setResource(Integer.parseInt(msg.substring(0, msg.indexOf(","))));
									msg = msg.substring(msg.indexOf(",") + 1);
									moonPlanet.setResource(Integer.parseInt(msg.substring(0, msg.indexOf(","))));
								}

							}

						}

					}

				} catch (IOException e) {
					System.out.println("Failed to receive msg from the server");
					e.printStackTrace();
				}
			}

		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * MapPanel
	 * Draws planet selection screen
	 * @author Felix Tai
	 */
	public class MapPanel extends JPanel {

		BufferedImage yarnPlanet = null;
		JLabel yarnLabel;
		BufferedImage flatPlanet = null;
		JLabel flatLabel;
		BufferedImage potatoPlanet = null;
		JLabel potatoLabel;
		BufferedImage specklePlanet = null;
		JLabel speckleLabel;
		BufferedImage fracturedPlanet = null;
		JLabel fracturedLabel;
		BufferedImage depot = null;
		JLabel depotLabel;
		BufferedImage jupiterPlanet = null;
		JLabel jupiterLabel;
		BufferedImage moonPlanet = null;
		JLabel moonLabel;
		BufferedImage basketballPlanet = null;
		JLabel basketballLabel;
		BufferedImage bouncyPlanet = null;
		JLabel bouncyLabel;
		BufferedImage saturnPlanet = null;
		JLabel saturnLabel;

		Color clearColour = new Color(239, 161, 4, 225); // create 50% transparent colour
		Font bigFont = new Font("Helvetica", Font.BOLD, 40);
		Color textColour = new Color(209, 0, 198);
		String planetName = "";
		String drawText1 = "";
		String drawText2 = "";
		int estimatedResources = 0;

		public MapPanel() {
			try { // loading images
				yarnPlanet = ImageIO.read(new File("yarnPlanet.png"));
				flatPlanet = ImageIO.read(new File("flatEarth.png"));
				potatoPlanet = ImageIO.read(new File("potatoPlanet.png"));
				specklePlanet = ImageIO.read(new File("specklePlanet.png"));
				fracturedPlanet = ImageIO.read(new File("fracturedPlanet.png"));
				depot = ImageIO.read(new File("SpaceDepot.png"));
				jupiterPlanet = ImageIO.read(new File("jupiter.png"));
				moonPlanet = ImageIO.read(new File("moonPlanet.png"));
				basketballPlanet = ImageIO.read(new File("basketball.png"));
				bouncyPlanet = ImageIO.read(new File("bouncyPlanet.png"));
				saturnPlanet = ImageIO.read(new File("saturnPlanet.png"));
			} catch (Exception ex) {
				System.out.println("image didn't load");
			}

			this.setLayout(null);

			// Resize images
			yarnPlanet = resizeImage(yarnPlanet, screenX / 12, screenX / 12);
			flatPlanet = resizeImage(flatPlanet, screenX / 12, screenX / 12);
			potatoPlanet = resizeImage(potatoPlanet, screenX / 12, screenX / 12);
			specklePlanet = resizeImage(specklePlanet, screenX / 12, screenX / 12);
			fracturedPlanet = resizeImage(fracturedPlanet, screenX / 12, screenX / 12);
			depot = resizeImage(depot, screenX / 8, screenX / 8);
			jupiterPlanet = resizeImage(jupiterPlanet, screenX / 12, screenX / 12);
			moonPlanet = resizeImage(moonPlanet, screenX / 12, screenX / 12);
			basketballPlanet = resizeImage(basketballPlanet, screenX / 12,screenX / 12);
			bouncyPlanet = resizeImage(bouncyPlanet, screenX / 12,screenX / 12);
			saturnPlanet = resizeImage(saturnPlanet, screenX / 12,screenX / 12);

			yarnLabel = createImageButton(yarnPlanet);
			yarnLabel.setBounds(0, 100, yarnPlanet.getWidth(), yarnPlanet.getHeight());

			flatLabel = createImageButton(flatPlanet);
			flatLabel.setBounds(50, 2 * screenY / 3 - flatPlanet.getWidth() - 100, flatPlanet.getWidth(),
					flatPlanet.getHeight());

			potatoLabel = createImageButton(potatoPlanet);
			potatoLabel.setBounds(screenX - potatoPlanet.getWidth() - 50, 50, potatoPlanet.getWidth(),
					potatoPlanet.getHeight());

			speckleLabel = createImageButton(specklePlanet);
			speckleLabel.setBounds(screenX / 5 - specklePlanet.getWidth() / 2, screenY / 2 - specklePlanet.getHeight() / 2,
					specklePlanet.getWidth(), specklePlanet.getHeight());

			fracturedLabel = createImageButton(fracturedPlanet);
			fracturedLabel.setBounds(screenX - yarnPlanet.getWidth() - 200, 500, yarnPlanet.getWidth(),
					yarnPlanet.getHeight());

			depotLabel = createImageButton(depot);
			depotLabel.setBounds(screenX / 2 - depot.getWidth() / 2, screenY / 2 - depot.getHeight(), depot.getWidth(),
					depot.getHeight());

			jupiterLabel = createImageButton(jupiterPlanet);
			jupiterLabel.setBounds(screenX - jupiterPlanet.getWidth() - 400, screenY / 2, jupiterPlanet.getWidth(),
					jupiterPlanet.getHeight());

			moonLabel = createImageButton(moonPlanet);
			moonLabel.setBounds(screenX - moonPlanet.getWidth() - 400, 200, moonPlanet.getWidth(), moonPlanet.getHeight());

			basketballLabel = createImageButton(basketballPlanet);
			basketballLabel.setBounds(screenX/2 + 200, 200, screenX / 12, screenX / 12);

			bouncyLabel = createImageButton(bouncyPlanet);
			bouncyLabel.setBounds(screenX/4, screenY/6, screenX / 12, screenX / 12);

			saturnLabel = createImageButton(saturnPlanet);
			saturnLabel.setBounds(screenX/2 - screenX/24, 30, screenX / 12, screenX / 12);

			userShip = resizeImage(combineSpaceship(engine.getUpgradeLevel()), screenX/4, (screenX/4)/3);

			JButton button = new JButton("Confirm Travel");
			button.setFont(new Font("Tahoma", Font.PLAIN, 28));
			button.setBounds(screenX / 2 - 150, screenY - 200, 300, 100);
			button.addActionListener(new TravelButtonListener());

			this.add(yarnLabel);
			this.add(flatLabel);
			this.add(potatoLabel);
			this.add(speckleLabel);
			this.add(fracturedLabel);
			this.add(depotLabel);
			this.add(jupiterLabel);
			this.add(moonLabel);
			this.add(basketballLabel);
			this.add(bouncyLabel);
			this.add(saturnLabel);
			this.add(button);
		}

		/**
		 * createImageButton 
		 * creates JLabel with MouseListener so it acts like a button
		 * @param Buffered image to be turned into JLabel
		 * @return button JLabel
		 */
		public JLabel createImageButton(final BufferedImage planet) {
			JLabel imageButton = new JLabel(new ImageIcon(planet));
			imageButton.addMouseListener(new PlanetListener(planet));
			return imageButton;
		}

		public class TravelButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (planetName.equals("Yarn Planet")) {
					frame.setContentPane(new TravelPanel("Yarn Planet", 5));
					frame.invalidate();
					frame.validate();
				} else if (planetName.equals("Flat Planet")) {
					frame.setContentPane(new TravelPanel("Flat Planet", 5));
					frame.invalidate();
					frame.validate();
				} else if (planetName.equals("Potato Planet")) {
					frame.setContentPane(new TravelPanel("Potato Planet", 5));
					frame.invalidate();
					frame.validate();
				} else if (planetName.equals("Speckle Planet")) {
					frame.setContentPane(new TravelPanel("Speckle Planet", 5));
					frame.invalidate();
					frame.validate();
				} else if (planetName.equals("Fractured Planet")) {
					frame.setContentPane(new TravelPanel("Fractured Planet", 5));
					frame.invalidate();
					frame.validate();
				} else if (planetName.equals("Depot")) {
					frame.setContentPane(new TravelPanel("Depot", 5));
					frame.invalidate();
					frame.validate();
				} else if (planetName.equals("Jupiter Planet")) {
					frame.setContentPane(new TravelPanel("Jupiter Planet", 5));
					frame.invalidate();
					frame.validate();
				} else if (planetName.equals("Moon Planet")) {
					frame.setContentPane(new TravelPanel("Moon Planet", 5));
					frame.invalidate();
					frame.validate();
				} else if (planetName.equals("Basketball Planet")) {
					frame.setContentPane(new TravelPanel("Basketball Planet", 5));
					frame.invalidate();
					frame.validate();
				} else if (planetName.equals("Saturn Planet")) {
					frame.setContentPane(new TravelPanel("Saturn Planet", 5));
					frame.invalidate();
					frame.validate();
				} else if (planetName.equals("Bouncy Planet")) {
					frame.setContentPane(new TravelPanel("Bouncy Planet", 5));
					frame.invalidate();
					frame.validate();
				}
				inputss.enqueue(planetName);
				commandd.enqueue("1");
			}
		}

		// MouseAdapter for planets
		public class PlanetListener extends MouseAdapter {
			BufferedImage planet;
			JLabel source;

			PlanetListener(BufferedImage planet) {
				this.planet = planet;
			}

			public void mouseClicked(MouseEvent e) {
				boolean opaque = (planet.getRGB(e.getX(), e.getY()) & 0x00ffffff) != 0; //check colour of clicked planetlabel
				if (opaque) {
					source = (JLabel) e.getSource();
					//Change planet name according to which planet has been clicked
					if (source == yarnLabel) { 
						planetName = "Yarn Planet";
					} else if (source == flatLabel) {
						planetName = "Flat Planet";
					} else if (source == potatoLabel) {
						planetName = "Potato Planet";
					} else if (source == speckleLabel) { 
						planetName = "Speckle Planet";
					} else if (source == fracturedLabel) { 
						planetName = "Fractured Planet";
					} else if (source == depotLabel) { 
						planetName = "Depot";
					} else if (source == jupiterLabel) { 
						planetName = "Jupiter Planet";
					} else if (source == moonLabel) {
						planetName = "Moon Planet";
					} else if (source == basketballLabel) { 
						planetName = "Basketball Planet";
					} else if (source == saturnLabel) {
						planetName = "Saturn Planet";
					} else if (source == bouncyLabel) {
						planetName = "Bouncy Planet";
					}
				}
			}
		}

		/*
		 * paintComponent
		 * 
		 * @param Graphics g
		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g); // required to ensure the panel is correctly redrawn
			g.drawImage(backgroundImage, 0, 0, null);

			g.setColor(clearColour);
			g.fillRect(0, 2 * screenY / 3, screenX, screenY / 3);

			g.setFont(bigFont);
			g.setColor(textColour);
			drawText1 = "Travel to: " + planetName;
			if (!planetName.equals("Depot")) { // No projected resources for depot
				drawText2 = "Deep Space Viewer projected Resources: " + estimatedResources;
			} else {
				drawText2 = "";
			}
			g.drawString(drawText1, 20, 2 * screenY / 3 + 50);
			g.drawString(drawText2, 20, 2 * screenY / 3 + 150);

			repaint();
		}

		public void sendInstructions(String instructions) {

		}

	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * DepotPanel
	 * Inner class for displaying depot panel
	 * @author Felix Tai
	 */
	public class DepotPanel extends JPanel {
		Image backgroundImage = null;

		DepotPanel() {
			JPanel upgrade = new UpgradePanel();
			this.setLayout(null);
			upgrade.setBounds(0, 2 * screenY / 3, screenX, screenY / 3);
			try { // loading images
				backgroundImage = new ImageIcon("SpaceStationHangar.png").getImage();
			} catch (Exception ex) {
				System.out.println("image didn't load");
			}
			backgroundImage = backgroundImage.getScaledInstance(screenX, screenY, Image.SCALE_DEFAULT);

			// Make a JButton to go back to the Map Panel
			JButton mapButton = new JButton("Travel Elsewhere");
			mapButton.addActionListener(new backListener());
			mapButton.setBounds(screenX / 12, screenX / 12, 400, 100);
			mapButton.setFont(new Font("Tahoma", Font.BOLD, 29));

			this.add(upgrade);
			this.add(mapButton);
		}

		//Paint component to draw background
		public void paintComponent(Graphics g) {
			super.paintComponent(g); // required to ensure the panel is correctly redrawn
			g.drawImage(backgroundImage, 0, 0, null);
			repaint();
		}
		
		//ActionListener for going back to map selection
		class backListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(new MapPanel());
				frame.invalidate();
				frame.validate();
			}
		}

		/**
		 * UpgradePanel
		 * Panel for upgrading
		 * @author Felix Tai
		 */
		public class UpgradePanel extends JPanel {

			JButton button; // engine
			JButton button_1; // mining
			JButton button_2; // shields
			JButton button_3; // weapons
			JButton button_5; // Deepspaceviewer

			public UpgradePanel() {
				
				this.setPreferredSize(new Dimension(screenX, screenY / 3));
				UIManager.put("TabbedPane.selected", new Color(0, 183, 229)); // change tab to white when it is selected
				setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
				setForeground(new Color(0, 0, 139));
				setBackground(new Color(51, 0, 102));

				JLabel lblUpgradeShip = new JLabel("Upgrade Ship");
				lblUpgradeShip.setBackground(new Color(102, 205, 170));
				lblUpgradeShip.setForeground(new Color(255, 255, 255));
				lblUpgradeShip.setHorizontalAlignment(SwingConstants.CENTER);
				lblUpgradeShip.setFont(new Font("Gill Sans MT", Font.BOLD, 28));

				JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
				tabbedPane.setFont(new Font("Tahoma", Font.BOLD, 19));
				tabbedPane.setForeground(Color.WHITE);
				GroupLayout groupLayout = new GroupLayout(this);
				groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblUpgradeShip, GroupLayout.DEFAULT_SIZE, 815, Short.MAX_VALUE)
						.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 1223, Short.MAX_VALUE));
				groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addComponent(lblUpgradeShip)
								.addPreferredGap(ComponentPlacement.RELATED, 24, Short.MAX_VALUE).addComponent(
										tabbedPane, GroupLayout.PREFERRED_SIZE, 454, GroupLayout.PREFERRED_SIZE)));

				tabbedPane.setBackground(new Color(3, 53, 132));

				// Tab for engine upgrades
				JPanel EnginePanel = new JPanel();
				EnginePanel.setBackground(Color.LIGHT_GRAY);
				tabbedPane.addTab("Engine Module", null, EnginePanel, null);

				JLabel label = new JLabel("Current Level: " + engine.getUpgradeLevel());
				label.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

				button = new JButton(buttonIcon);
				button.setFont(new Font("Tahoma", Font.PLAIN, 28));
				button.addActionListener(new UpgradeListener());

				JLabel label_1 = new JLabel("Cost:");
				label_1.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

				JLabel lblUpgradeEngines = new JLabel("Upgrade Engines");
				lblUpgradeEngines.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 28));

				JLabel lblNewLabel_1 = new JLabel(steelIcon);

				JLabel label_9 = new JLabel(grapheneIcon);

				JLabel lblX = new JLabel("x" + engine.getSteel());
				lblX.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

				JLabel label_16 = new JLabel("x" + engine.getGraphene());
				label_16.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

				JLabel label_4 = new JLabel(plutoniumIcon);

				JLabel label_20 = new JLabel("x" + engine.getPlut());
				label_20.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
				GroupLayout gl_EnginePanel = new GroupLayout(EnginePanel);
				gl_EnginePanel.setHorizontalGroup(gl_EnginePanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_EnginePanel.createSequentialGroup().addGap(10).addComponent(label,
								GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_EnginePanel.createSequentialGroup().addGap(15).addComponent(lblUpgradeEngines,
								GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_EnginePanel.createSequentialGroup().addGap(77)
								.addGroup(gl_EnginePanel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_EnginePanel.createSequentialGroup().addGap(71).addComponent(
												lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
										.addGroup(
												gl_EnginePanel.createSequentialGroup()
												.addComponent(label_1, GroupLayout.DEFAULT_SIZE, 92,
														Short.MAX_VALUE)
												.addGap(49)))
								.addGap(10).addComponent(lblX, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE).addGap(7)
								.addComponent(label_9, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE).addGap(10)
								.addComponent(label_16, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE).addGap(42)
								.addComponent(label_4, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(label_20, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
								.addGap(337))
						.addGroup(gl_EnginePanel.createSequentialGroup().addGap(220)
								.addComponent(button, GroupLayout.PREFERRED_SIZE, 655, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(308, Short.MAX_VALUE)));
				gl_EnginePanel
				.setVerticalGroup(
						gl_EnginePanel.createParallelGroup(Alignment.LEADING).addGroup(
								gl_EnginePanel.createSequentialGroup().addGroup(gl_EnginePanel
										.createParallelGroup(Alignment.LEADING,
												false)
										.addGroup(gl_EnginePanel.createSequentialGroup().addGap(11)
												.addComponent(label, GroupLayout.PREFERRED_SIZE, 43,
														GroupLayout.PREFERRED_SIZE)
												.addGap(46)
												.addComponent(
														lblUpgradeEngines, GroupLayout.PREFERRED_SIZE, 43,
														GroupLayout.PREFERRED_SIZE)
												.addGap(7)
												.addGroup(gl_EnginePanel.createParallelGroup(Alignment.LEADING)
														.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE,
																50, GroupLayout.PREFERRED_SIZE)
														.addGroup(gl_EnginePanel.createSequentialGroup()
																.addGap(18).addComponent(label_1,
																		GroupLayout.PREFERRED_SIZE, 20,
																		GroupLayout.PREFERRED_SIZE))
														.addGroup(gl_EnginePanel
																.createSequentialGroup().addGap(18)
																.addComponent(lblX, GroupLayout.PREFERRED_SIZE,
																		20, GroupLayout.PREFERRED_SIZE))
														.addComponent(label_9, GroupLayout.PREFERRED_SIZE, 50,
																GroupLayout.PREFERRED_SIZE)
														.addGroup(gl_EnginePanel.createSequentialGroup()
																.addGap(18).addComponent(label_16,
																		GroupLayout.PREFERRED_SIZE, 20,
																		GroupLayout.PREFERRED_SIZE))
														.addComponent(label_4, GroupLayout.PREFERRED_SIZE, 50,
																GroupLayout.PREFERRED_SIZE))
												.addGap(42))
										.addGroup(Alignment.TRAILING, gl_EnginePanel.createSequentialGroup()
												.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(label_20).addGap(51)))
								.addComponent(button, GroupLayout.PREFERRED_SIZE, 69,
										GroupLayout.PREFERRED_SIZE)
								.addGap(106)));
				EnginePanel.setLayout(gl_EnginePanel);

				// Tab for weapons upgrades
				JPanel WeaponsPanel = new JPanel();
				WeaponsPanel.setBackground(Color.GRAY);
				tabbedPane.addTab("Weapons Module", null, WeaponsPanel, null);
				WeaponsPanel.setLayout(null);

				JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
				tabbedPane_1.setBounds(10, 11, 1120, 395);
				tabbedPane_1.setBackground(new Color(3, 53, 132)); // change colour of all tabs
				tabbedPane_1.setFont(new Font("Tahoma", Font.BOLD, 19)); // change font
				tabbedPane_1.setForeground(Color.WHITE); // change text colour
				WeaponsPanel.add(tabbedPane_1);

				// Sub-tabbed panel for upgrading weapon module
				JPanel UpgradeWeaponModule = new JPanel();
				UpgradeWeaponModule.setBackground(Color.LIGHT_GRAY);
				tabbedPane_1.addTab("Upgrade Weapons Module", null, UpgradeWeaponModule, null);

				JLabel label_10 = new JLabel("Upgrade Weapon Module");
				label_10.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 28));

				JLabel label_11 = new JLabel(grapheneIcon);

				JLabel label_12 = new JLabel("Current Level: " + weaponModule.getUpgradeLevel());
				label_12.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

				JLabel label_25 = new JLabel("Cost:");
				label_25.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

				JLabel label_26 = new JLabel("x" + weaponModule.getSteel());
				label_26.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

				JLabel label_27 = new JLabel(steelIcon);

				JLabel label_28 = new JLabel("x" + weaponModule.getGraphene());
				label_28.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

				JLabel label_48 = new JLabel(pyroxiumIcon);

				JLabel label_49 = new JLabel("x" + weaponModule.getPyro());
				label_49.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

				button_3 = new JButton(buttonIcon);
				button_3.setFont(new Font("Tahoma", Font.PLAIN, 28));
				button_3.addActionListener(new UpgradeListener());

				GroupLayout gl_UpgradeWeaponModule = new GroupLayout(UpgradeWeaponModule);
				gl_UpgradeWeaponModule.setHorizontalGroup(gl_UpgradeWeaponModule.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_UpgradeWeaponModule.createSequentialGroup().addGroup(gl_UpgradeWeaponModule
								.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_UpgradeWeaponModule.createSequentialGroup().addGap(10).addComponent(
										label_12, GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_UpgradeWeaponModule.createSequentialGroup().addGap(15).addComponent(
										label_10, GroupLayout.PREFERRED_SIZE, 334, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_UpgradeWeaponModule.createSequentialGroup().addGap(77)
										.addGroup(gl_UpgradeWeaponModule.createParallelGroup(Alignment.LEADING, false)
												.addGroup(gl_UpgradeWeaponModule.createSequentialGroup()
														.addComponent(label_25, GroupLayout.PREFERRED_SIZE, 81,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED, 49,
																Short.MAX_VALUE))
												.addGroup(gl_UpgradeWeaponModule.createSequentialGroup()
														.addPreferredGap(ComponentPlacement.RELATED, 71,
																Short.MAX_VALUE)
														.addComponent(label_27, GroupLayout.PREFERRED_SIZE, 59,
																GroupLayout.PREFERRED_SIZE)))
										.addGap(10)
										.addGroup(gl_UpgradeWeaponModule.createParallelGroup(Alignment.LEADING)
												.addComponent(button_3, GroupLayout.PREFERRED_SIZE, 655,
														GroupLayout.PREFERRED_SIZE)
												.addGroup(gl_UpgradeWeaponModule.createSequentialGroup()
														.addComponent(label_26, GroupLayout.PREFERRED_SIZE, 125,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(label_11, GroupLayout.PREFERRED_SIZE, 61,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(label_28, GroupLayout.PREFERRED_SIZE, 125,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(label_48, GroupLayout.PREFERRED_SIZE, 61,
																GroupLayout.PREFERRED_SIZE)
														.addGap(9).addComponent(label_49, GroupLayout.PREFERRED_SIZE,
																125, GroupLayout.PREFERRED_SIZE)))))
								.addContainerGap(243, Short.MAX_VALUE)));
				gl_UpgradeWeaponModule.setVerticalGroup(gl_UpgradeWeaponModule.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_UpgradeWeaponModule.createSequentialGroup().addGroup(gl_UpgradeWeaponModule
								.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_UpgradeWeaponModule.createSequentialGroup().addGap(11)
										.addComponent(label_12, GroupLayout.PREFERRED_SIZE, 43,
												GroupLayout.PREFERRED_SIZE)
										.addGap(46)
										.addComponent(
												label_10, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
										.addGap(7)
										.addGroup(gl_UpgradeWeaponModule.createParallelGroup(Alignment.LEADING)
												.addComponent(label_48, GroupLayout.PREFERRED_SIZE, 50,
														GroupLayout.PREFERRED_SIZE)
												.addGroup(gl_UpgradeWeaponModule.createSequentialGroup().addGap(18)
														.addComponent(label_25, GroupLayout.PREFERRED_SIZE, 20,
																GroupLayout.PREFERRED_SIZE))
												.addComponent(label_27, GroupLayout.PREFERRED_SIZE, 50,
														GroupLayout.PREFERRED_SIZE)
												.addGroup(gl_UpgradeWeaponModule.createSequentialGroup().addGap(18)
														.addComponent(label_26, GroupLayout.PREFERRED_SIZE, 20,
																GroupLayout.PREFERRED_SIZE))
												.addComponent(label_11, GroupLayout.PREFERRED_SIZE, 50,
														GroupLayout.PREFERRED_SIZE)
												.addGroup(gl_UpgradeWeaponModule.createSequentialGroup().addGap(18)
														.addComponent(label_28, GroupLayout.PREFERRED_SIZE, 20,
																GroupLayout.PREFERRED_SIZE))))
								.addGroup(gl_UpgradeWeaponModule.createSequentialGroup().addGap(161)
										.addComponent(label_49)))
								.addGap(33)
								.addComponent(button_3, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(56, Short.MAX_VALUE)));
				UpgradeWeaponModule.setLayout(gl_UpgradeWeaponModule);

				// Sub-tabbed panel for upgrading/purchasing individual weapons
				JPanel WeaponStats = new JPanel();
				WeaponStats.setBackground(Color.LIGHT_GRAY);
				tabbedPane_1.addTab("Weapon Stats", null, WeaponStats, null);

				JLabel lblNewLabel_3 = new JLabel(shieldJammerIcon);

				JLabel lblNewLabel_4 = new JLabel(laserIcon);

				JLabel lblNewLabel_5 = new JLabel(missileIcon);

				JLabel lblNewLabel_2 = new JLabel("New label");

				JLabel label_50 = new JLabel("New label");

				JLabel label_51 = new JLabel("New label");
				GroupLayout gl_WeaponStats = new GroupLayout(WeaponStats);
				gl_WeaponStats.setHorizontalGroup(gl_WeaponStats.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_WeaponStats.createSequentialGroup().addGap(87)
								.addComponent(lblNewLabel_3, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE).addGap(90)
								.addComponent(lblNewLabel_5, GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE).addGap(95)
								.addComponent(lblNewLabel_4, GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE).addGap(56))
						.addGroup(gl_WeaponStats.createSequentialGroup().addGap(139)
								.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE).addGap(187)
								.addComponent(label_50, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE).addGap(235)
								.addComponent(label_51, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE).addGap(128)));
				gl_WeaponStats.setVerticalGroup(gl_WeaponStats.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_WeaponStats.createSequentialGroup().addGap(45).addGroup(gl_WeaponStats
								.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_4, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_5, GroupLayout.PREFERRED_SIZE, 42,
										GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_WeaponStats.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_WeaponStats.createSequentialGroup().addGap(55)
												.addGroup(gl_WeaponStats.createParallelGroup(Alignment.BASELINE)
														.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 169,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(label_50, GroupLayout.PREFERRED_SIZE, 169,
																GroupLayout.PREFERRED_SIZE)))
										.addGroup(gl_WeaponStats.createSequentialGroup().addGap(51).addComponent(
												label_51, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)))
								.addGap(47)));
				WeaponStats.setLayout(gl_WeaponStats);

				JPanel MiningPanel = new JPanel();
				MiningPanel.setBackground(Color.LIGHT_GRAY);
				tabbedPane.addTab("Mining Module", null, MiningPanel, null);

				JLabel label_2 = new JLabel("Current Level: " + miningModule.getUpgradeLevel());
				label_2.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

				JLabel label_3 = new JLabel("Upgrade Mining Equipment");
				label_3.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 28));

				JLabel label_5 = new JLabel(steelIcon);

				JLabel label_6 = new JLabel("Cost:");
				label_6.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

				JLabel label_7 = new JLabel("x" + miningModule.getSteel());
				label_7.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

				JLabel label_8 = new JLabel(grapheneIcon);

				JLabel label_13 = new JLabel("x" + miningModule.getGraphene());
				label_13.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

				JLabel label_14 = new JLabel(blastCrystalIcon);

				JLabel label_15 = new JLabel("x" + miningModule.getCrystal());
				label_15.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

				button_1 = new JButton(buttonIcon);
				button_1.setFont(new Font("Tahoma", Font.PLAIN, 28));
				button_1.addActionListener(new UpgradeListener());

				GroupLayout gl_MiningPanel = new GroupLayout(MiningPanel);
				gl_MiningPanel.setHorizontalGroup(gl_MiningPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_MiningPanel.createSequentialGroup().addGap(10).addComponent(label_2,
								GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_MiningPanel.createSequentialGroup().addGap(77)
								.addGroup(gl_MiningPanel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_MiningPanel.createSequentialGroup().addGap(71)
												.addComponent(label_5, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
										.addGroup(
												gl_MiningPanel.createSequentialGroup()
												.addComponent(label_6, GroupLayout.DEFAULT_SIZE, 92,
														Short.MAX_VALUE)
												.addGap(49)))
								.addGap(10).addComponent(label_7, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
								.addGap(7).addComponent(label_8, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
								.addGap(10).addComponent(label_13, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
								.addGap(42)
								.addComponent(label_14, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(label_15, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
								.addGap(337))
						.addGroup(
								gl_MiningPanel.createSequentialGroup().addGap(220)
								.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 655,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap(308, Short.MAX_VALUE))
						.addGroup(gl_MiningPanel.createSequentialGroup().addContainerGap()
								.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 322, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(846, Short.MAX_VALUE)));
				gl_MiningPanel.setVerticalGroup(gl_MiningPanel.createParallelGroup(Alignment.LEADING)
						.addGap(0, 417, Short.MAX_VALUE)
						.addGroup(gl_MiningPanel.createSequentialGroup()
								.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(gl_MiningPanel.createParallelGroup(Alignment.TRAILING, false)
										.addGroup(gl_MiningPanel.createSequentialGroup()
												.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 43,
														GroupLayout.PREFERRED_SIZE)
												.addGap(46)
												.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 43,
														GroupLayout.PREFERRED_SIZE)
												.addGap(7)
												.addGroup(gl_MiningPanel.createParallelGroup(Alignment.LEADING)
														.addComponent(label_5, GroupLayout.PREFERRED_SIZE, 50,
																GroupLayout.PREFERRED_SIZE)
														.addGroup(gl_MiningPanel.createSequentialGroup().addGap(18)
																.addComponent(label_6, GroupLayout.PREFERRED_SIZE, 20,
																		GroupLayout.PREFERRED_SIZE))
														.addGroup(gl_MiningPanel.createSequentialGroup().addGap(18)
																.addComponent(label_7, GroupLayout.PREFERRED_SIZE, 20,
																		GroupLayout.PREFERRED_SIZE))
														.addComponent(label_8, GroupLayout.PREFERRED_SIZE, 50,
																GroupLayout.PREFERRED_SIZE)
														.addGroup(gl_MiningPanel.createSequentialGroup().addGap(18)
																.addComponent(label_13, GroupLayout.PREFERRED_SIZE, 20,
																		GroupLayout.PREFERRED_SIZE))
														.addComponent(label_14, GroupLayout.PREFERRED_SIZE, 50,
																GroupLayout.PREFERRED_SIZE))
												.addGap(42))
										.addGroup(gl_MiningPanel.createSequentialGroup().addComponent(label_15)
												.addGap(51)))
								.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
								.addGap(106)));
				MiningPanel.setLayout(gl_MiningPanel);

				JPanel DeepSpaceViewerPanel = new JPanel();
				DeepSpaceViewerPanel.setBackground(Color.LIGHT_GRAY);
				tabbedPane.addTab("Deep Space Viewer", null, DeepSpaceViewerPanel, null);

				JLabel label_24 = new JLabel("Current Level: " + deepSpaceViewer.getUpgradeLevel());
				label_24.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

				JLabel label_32 = new JLabel("Upgrade Deep Space Viewer");
				label_32.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 28));

				JLabel label_33 = new JLabel(steelIcon);

				JLabel label_34 = new JLabel("Cost:");
				label_34.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

				JLabel label_35 = new JLabel("x" + deepSpaceViewer.getSteel());
				label_35.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

				JLabel label_36 = new JLabel(grapheneIcon);

				JLabel label_37 = new JLabel("x" + deepSpaceViewer.getGraphene());
				label_37.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

				JLabel label_38 = new JLabel(intellectiumIcon);

				JLabel label_39 = new JLabel("x" + deepSpaceViewer.getIntellectium());
				label_39.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

				button_5 = new JButton(buttonIcon);
				button_5.setFont(new Font("Tahoma", Font.PLAIN, 28));
				button_5.addActionListener(new UpgradeListener());

				GroupLayout gl_DeepSpaceViewerPanel = new GroupLayout(DeepSpaceViewerPanel);
				gl_DeepSpaceViewerPanel.setHorizontalGroup(gl_DeepSpaceViewerPanel
						.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_DeepSpaceViewerPanel.createSequentialGroup().addGap(10).addComponent(label_24,
								GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_DeepSpaceViewerPanel.createSequentialGroup().addGap(77)
								.addGroup(gl_DeepSpaceViewerPanel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_DeepSpaceViewerPanel.createSequentialGroup().addGap(71)
												.addComponent(label_33, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
										.addGroup(
												gl_DeepSpaceViewerPanel.createSequentialGroup()
												.addComponent(label_34, GroupLayout.DEFAULT_SIZE, 92,
														Short.MAX_VALUE)
												.addGap(49)))
								.addGap(10).addComponent(label_35, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
								.addGap(7).addComponent(label_36, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
								.addGap(10).addComponent(label_37, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
								.addGap(42)
								.addComponent(label_38, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(label_39, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
								.addGap(337))
						.addGroup(
								gl_DeepSpaceViewerPanel.createSequentialGroup().addGap(220)
								.addComponent(button_5, GroupLayout.PREFERRED_SIZE, 655,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap(308, Short.MAX_VALUE))
						.addGroup(gl_DeepSpaceViewerPanel.createSequentialGroup().addContainerGap()
								.addComponent(label_32, GroupLayout.PREFERRED_SIZE, 361, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(807, Short.MAX_VALUE)));
				gl_DeepSpaceViewerPanel.setVerticalGroup(gl_DeepSpaceViewerPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_DeepSpaceViewerPanel.createSequentialGroup()
								.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(gl_DeepSpaceViewerPanel.createParallelGroup(Alignment.TRAILING, false)
										.addGroup(gl_DeepSpaceViewerPanel.createSequentialGroup()
												.addComponent(label_24, GroupLayout.PREFERRED_SIZE, 43,
														GroupLayout.PREFERRED_SIZE)
												.addGap(46)
												.addComponent(label_32, GroupLayout.PREFERRED_SIZE, 43,
														GroupLayout.PREFERRED_SIZE)
												.addGap(7)
												.addGroup(gl_DeepSpaceViewerPanel.createParallelGroup(Alignment.LEADING)
														.addComponent(label_33, GroupLayout.PREFERRED_SIZE, 50,
																GroupLayout.PREFERRED_SIZE)
														.addGroup(gl_DeepSpaceViewerPanel.createSequentialGroup()
																.addGap(18).addComponent(label_34,
																		GroupLayout.PREFERRED_SIZE, 20,
																		GroupLayout.PREFERRED_SIZE))
														.addGroup(gl_DeepSpaceViewerPanel.createSequentialGroup()
																.addGap(18)
																.addComponent(label_35, GroupLayout.PREFERRED_SIZE, 20,
																		GroupLayout.PREFERRED_SIZE))
														.addComponent(label_36, GroupLayout.PREFERRED_SIZE, 50,
																GroupLayout.PREFERRED_SIZE)
														.addGroup(gl_DeepSpaceViewerPanel.createSequentialGroup()
																.addGap(18).addComponent(label_37,
																		GroupLayout.PREFERRED_SIZE, 20,
																		GroupLayout.PREFERRED_SIZE))
														.addComponent(label_38, GroupLayout.PREFERRED_SIZE, 50,
																GroupLayout.PREFERRED_SIZE))
												.addGap(42))
										.addGroup(gl_DeepSpaceViewerPanel.createSequentialGroup().addComponent(label_39)
												.addGap(51)))
								.addComponent(button_5, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
								.addGap(106)));
				DeepSpaceViewerPanel.setLayout(gl_DeepSpaceViewerPanel);

				JPanel ShieldPanel = new JPanel();
				ShieldPanel.setBackground(Color.LIGHT_GRAY);
				tabbedPane.addTab("Shield Module", null, ShieldPanel, null);

				JLabel label_17 = new JLabel("Current Level: " + shield.getUpgradeLevel());
				label_17.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

				JLabel label_18 = new JLabel("Upgrade Shields");
				label_18.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 28));

				JLabel label_19 = new JLabel(steelIcon);

				JLabel label_21 = new JLabel("Cost:");
				label_21.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

				JLabel label_22 = new JLabel("x" + shield.getSteel());
				label_22.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

				JLabel label_23 = new JLabel(grapheneIcon);

				JLabel label_29 = new JLabel("x" + shield.getGraphene());
				label_29.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

				JLabel label_30 = new JLabel(starliteIcon);

				JLabel label_31 = new JLabel("x" + shield.getStarlite());
				label_31.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

				button_2 = new JButton(buttonIcon);
				button_2.setFont(new Font("Tahoma", Font.PLAIN, 28));
				button_2.addActionListener(new UpgradeListener());

				GroupLayout gl_ShieldPanel = new GroupLayout(ShieldPanel);
				gl_ShieldPanel.setHorizontalGroup(gl_ShieldPanel.createParallelGroup(Alignment.LEADING)
						.addGap(0, 1183, Short.MAX_VALUE).addGap(0, 1183, Short.MAX_VALUE)
						.addGroup(gl_ShieldPanel.createSequentialGroup().addGap(10).addComponent(label_17,
								GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_ShieldPanel.createSequentialGroup().addGap(15).addComponent(label_18,
								GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_ShieldPanel.createSequentialGroup().addGap(77)
								.addGroup(gl_ShieldPanel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_ShieldPanel.createSequentialGroup().addGap(71)
												.addComponent(label_19, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
										.addGroup(
												gl_ShieldPanel.createSequentialGroup()
												.addComponent(label_21, GroupLayout.DEFAULT_SIZE, 92,
														Short.MAX_VALUE)
												.addGap(49)))
								.addGap(10).addComponent(label_22, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
								.addGap(7).addComponent(label_23, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
								.addGap(10).addComponent(label_29, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
								.addGap(42)
								.addComponent(label_30, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(label_31, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
								.addGap(337))
						.addGroup(gl_ShieldPanel.createSequentialGroup().addGap(220)
								.addComponent(button_2, GroupLayout.PREFERRED_SIZE, 655, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(308, Short.MAX_VALUE)));
				gl_ShieldPanel.setVerticalGroup(gl_ShieldPanel.createParallelGroup(Alignment.LEADING)
						.addGap(0, 417, Short.MAX_VALUE).addGap(0, 417, Short.MAX_VALUE)
						.addGroup(gl_ShieldPanel.createSequentialGroup().addContainerGap(11, Short.MAX_VALUE).addGroup(
								gl_ShieldPanel.createParallelGroup(Alignment.TRAILING, false).addGroup(gl_ShieldPanel
										.createSequentialGroup()
										.addComponent(label_17, GroupLayout.PREFERRED_SIZE, 43,
												GroupLayout.PREFERRED_SIZE)
										.addGap(46)
										.addComponent(
												label_18, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
										.addGap(7)
										.addGroup(gl_ShieldPanel.createParallelGroup(Alignment.LEADING)
												.addComponent(label_19, GroupLayout.PREFERRED_SIZE, 50,
														GroupLayout.PREFERRED_SIZE)
												.addGroup(gl_ShieldPanel.createSequentialGroup().addGap(18)
														.addComponent(label_21, GroupLayout.PREFERRED_SIZE, 20,
																GroupLayout.PREFERRED_SIZE))
												.addGroup(gl_ShieldPanel.createSequentialGroup().addGap(18)
														.addComponent(label_22, GroupLayout.PREFERRED_SIZE, 20,
																GroupLayout.PREFERRED_SIZE))
												.addComponent(label_23, GroupLayout.PREFERRED_SIZE, 50,
														GroupLayout.PREFERRED_SIZE)
												.addGroup(gl_ShieldPanel.createSequentialGroup().addGap(18)
														.addComponent(label_29, GroupLayout.PREFERRED_SIZE, 20,
																GroupLayout.PREFERRED_SIZE))
												.addComponent(label_30, GroupLayout.PREFERRED_SIZE, 50,
														GroupLayout.PREFERRED_SIZE))
										.addGap(42))
								.addGroup(gl_ShieldPanel.createSequentialGroup().addComponent(label_31)
										.addGap(51)))
								.addComponent(button_2, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
								.addGap(106)));
				ShieldPanel.setLayout(gl_ShieldPanel);

				// Tab to repair ship
				JPanel RepairPanel = new JPanel();
				RepairPanel.setBackground(Color.LIGHT_GRAY);
				RepairPanel.setForeground(Color.BLACK);
				tabbedPane.addTab("Repair Ship", null, RepairPanel, null);

				JButton btnRepairShipTo = new JButton(repairIcon);
				btnRepairShipTo.setFont(new Font("Tw Cen MT", Font.BOLD, 24));
				btnRepairShipTo.addActionListener(new UpgradeListener());

				JLabel lblCost = new JLabel("Cost:");
				lblCost.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

				JLabel lblNewLabel = new JLabel("Current Ship Health: " + health);
				lblNewLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 30));

				JLabel lblMaximumShipHealth = new JLabel("Maximum Ship Health: " + 100);
				lblMaximumShipHealth.setFont(new Font("Tw Cen MT", Font.PLAIN, 30));

				JLabel label_40 = new JLabel(steelIcon);

				JLabel label_41 = new JLabel("x" + (100 - health) * 10);
				label_41.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

				JLabel label_42 = new JLabel(grapheneIcon);

				JLabel label_43 = new JLabel("x" + (100 - health) * 10);
				label_43.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

				JLabel label_44 = new JLabel(starliteIcon);

				JLabel label_45 = new JLabel("x" + (100 - health) * 10);
				label_45.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

				JLabel label_46 = new JLabel(blastCrystalIcon);

				JLabel label_47 = new JLabel("x" + (100 - health) * 10);
				label_47.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
				GroupLayout gl_RepairPanel = new GroupLayout(RepairPanel);
				gl_RepairPanel.setHorizontalGroup(gl_RepairPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_RepairPanel.createSequentialGroup().addGap(54)
								.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE).addGap(104)
								.addComponent(lblMaximumShipHealth, GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
								.addGap(252))
						.addGroup(gl_RepairPanel.createSequentialGroup().addGap(89)
								.addComponent(btnRepairShipTo, GroupLayout.PREFERRED_SIZE, 400,
										GroupLayout.PREFERRED_SIZE)
								.addGap(71).addComponent(lblCost, GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_RepairPanel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_RepairPanel.createSequentialGroup()
												.addComponent(label_40, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
												.addGap(10)
												.addComponent(label_41, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
												.addGap(7)
												.addComponent(label_42, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
												.addGap(10)
												.addComponent(label_43, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE))
										.addGroup(gl_RepairPanel.createSequentialGroup()
												.addComponent(label_44, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
												.addGap(10)
												.addComponent(label_45, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
												.addGap(7)
												.addComponent(label_46, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
												.addGap(10).addComponent(label_47, GroupLayout.DEFAULT_SIZE, 136,
														Short.MAX_VALUE)))));
				gl_RepairPanel.setVerticalGroup(gl_RepairPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_RepairPanel.createSequentialGroup().addGap(62)
								.addGroup(gl_RepairPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 43,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblMaximumShipHealth, GroupLayout.PREFERRED_SIZE, 43,
												GroupLayout.PREFERRED_SIZE))
								.addGap(64)
								.addGroup(gl_RepairPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(btnRepairShipTo, GroupLayout.PREFERRED_SIZE, 150,
												GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_RepairPanel.createSequentialGroup()
												.addGroup(gl_RepairPanel.createParallelGroup(Alignment.TRAILING)
														.addGroup(gl_RepairPanel.createParallelGroup(Alignment.LEADING)
																.addComponent(label_40, GroupLayout.PREFERRED_SIZE, 50,
																		GroupLayout.PREFERRED_SIZE)
																.addGroup(gl_RepairPanel.createSequentialGroup()
																		.addGap(18).addComponent(label_41))
																.addComponent(label_42, GroupLayout.PREFERRED_SIZE, 50,
																		GroupLayout.PREFERRED_SIZE)
																.addGroup(gl_RepairPanel.createSequentialGroup()
																		.addGap(18).addComponent(label_43)))
														.addComponent(lblCost, GroupLayout.PREFERRED_SIZE, 43,
																GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(gl_RepairPanel.createParallelGroup(Alignment.LEADING)
														.addComponent(label_44, GroupLayout.PREFERRED_SIZE, 50,
																GroupLayout.PREFERRED_SIZE)
														.addGroup(gl_RepairPanel.createSequentialGroup().addGap(18)
																.addComponent(label_45))
														.addComponent(label_46, GroupLayout.PREFERRED_SIZE, 50,
																GroupLayout.PREFERRED_SIZE)
														.addGroup(gl_RepairPanel.createSequentialGroup().addGap(18)
																.addComponent(label_47)))))));
				RepairPanel.setLayout(gl_RepairPanel);
				setLayout(groupLayout);

			}

			class UpgradeListener implements ActionListener {
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == button) { // engine upgrade
						inputss.enqueue("engineModule");
					} else if (e.getSource() == button_1) { // mining upgrade
						inputss.enqueue("miningModule");
					} else if (e.getSource() == button_2) { // shields upgrade
						inputss.enqueue("shieldModule");
					} else if (e.getSource() == button_3) { // weapons upgrade
						inputss.enqueue("weaponModule");
					} else if (e.getSource() == button_5) { // deep space viewer upgrade
						inputss.enqueue("deepSpaceViewer");
					}
					commandd.enqueue("3"); // command for upgrade
				}
			}
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public class TravelPanel extends JPanel {

		Clock clock = new Clock();
		int x = 0;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenX = (int) screenSize.getWidth();
		int screenY = (int) screenSize.getHeight();
		Font bigFont = new Font("Helvetica", Font.BOLD, 40);
		Color clearColour = new Color(36, 5, 51, 225); // create 50% transparent colour
		Color textColour = new Color(209, 0, 198);
		String drawText1 = "";
		String drawText2 = "";
		String planetName;
		int travelSec;
		int timePassed;
		double startTime = System.currentTimeMillis(); // time of travel start
		double currentTimeCheck;
		String timeString = "";
		Image scrollBackground = null;

		/**
		 * constructor
		 * @param planetName String containing name of planet being traveled to
		 * @param travelSec Integer representing the amount of time in seconds needed to
		 * travel to the planet
		 */
		public TravelPanel(String planetName, int travelSec) {
			try {
				scrollBackground = new ImageIcon("SpaceBackground.png").getImage();
			} catch (Exception ex) {
				System.out.println("image didn't load");
			}
			scrollBackground = scrollBackground.getScaledInstance(screenX, screenY, Image.SCALE_DEFAULT); // resize
			// background //
			// image
			this.planetName = planetName;
			this.travelSec = travelSec;
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g); // required to ensure the panel is correctly redrawn
			clock.update(); // update time
			boolean repaint = true;

			g.drawImage(scrollBackground, 0, 0, screenX - x, screenY, x, 0, screenX, screenY, null); // draw the part of the background from the left
			g.drawImage(scrollBackground, screenX - x, 0, screenX, screenY, 0, 0, x, screenY, null); // draw the rest of the background
			g.drawImage(userShip, screenX/5, screenY/20, null);

			// Draw translucent rectangle
			g.setColor(clearColour);
			g.fillRect(0, 2 * screenY / 3, screenX, screenY / 3);

			// Draw text with countdown and location being traveled to
			g.setFont(bigFont);
			g.setColor(textColour);
			currentTimeCheck = System.currentTimeMillis();
			timePassed = (int) (currentTimeCheck - startTime) / 1000;

			if (timePassed <= travelSec) { // check if travel time has not elapsed yet
				if ((travelSec - timePassed) / 60 < 1) { // no minutes
					timeString = Integer.toString(travelSec - timePassed) + " sec";
				} else {
					timeString = Integer.toString((travelSec - timePassed) / 60) + " min || "
							+ Integer.toString((travelSec - timePassed) % 60) + " sec";
				}
			} else {
				// exit to mine/depot
				commandd.enqueue("2");
				inputss.enqueue(planetName);
				repaint = false;
			}
			drawText1 = "Travelling to: " + planetName;
			drawText2 = "Time remaining: " + timeString;

			g.drawString(drawText1, 20, 2 * screenY / 3 + 50);
			g.drawString(drawText2, 20, 2 * screenY / 3 + 150);

			if (x < screenX) {
				x = (int) (x + clock.getElapsedTime() * 1500); // continue scrolling from left to right
			} else {
				x = 0; // right image has completely covered, reset to continue the loop
			}
			// request a repaint
			if (repaint) {
				repaint();
			}else {
				System.out.println(planetName);
				if (!planetName.equals("Depot")) {
					frame.setContentPane(new MinePanel(planetName));
				} else {
					frame.setContentPane(new DepotPanel());
				}
				frame.invalidate();
				frame.validate();
			}
		}

		class Clock {
			double elapsedTime; // time that has passed
			double lastTimeCheck; // when the last time check occured

			public Clock() { // constructor starts with the first time check
				lastTimeCheck = System.nanoTime();
				elapsedTime = 0;
			}

			public void update() { // called each loop of the game loop
				double currentTime = System.nanoTime(); // if the computer is fast you need more precision
				elapsedTime = currentTime - lastTimeCheck; // update current time
				lastTimeCheck = currentTime; // update last time check
			}

			// return elapsed time in milliseconds
			public double getElapsedTime() {
				return elapsedTime / 1.0E9; // used to get the elapsed time
			}
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * MinePanel
	 * Displays screen with mining in progress
	 * @author Felix Tai
	 * @param name of planet being mined
	 */
	public class MinePanel extends JPanel {
		Color clearColour = new Color(239, 161, 4, 225); // create 50% transparent colour
		Font bigFont = new Font("Helvetica", Font.BOLD, 40);
		Color textColour = new Color(209, 0, 198);
		String planetName = "";
		String drawText1 = "";
		String drawText2 = "";
		boolean exit = false;
		BufferedImage backgroundPlanet;
		
		//constructor
		MinePanel(String planetName) {
			try {
				//load planet for background
				if (planetName.equals("Yarn Planet")) {
					backgroundPlanet = ImageIO.read(new File("yarnPlanet.png"));
				} else if (planetName.equals("Flat Planet")) {
					backgroundPlanet = ImageIO.read(new File("flatEarth.png"));
				} else if (planetName.equals("Potato Planet")) {
					backgroundPlanet = ImageIO.read(new File("potatoPlanet.png"));
				} else if (planetName.equals("Speckle Planet")) {
					backgroundPlanet = ImageIO.read(new File("specklePlanet.png"));
				} else if (planetName.equals("Fractured Planet")) {
					backgroundPlanet = ImageIO.read(new File("fracturedPlanet.png"));
				} else if (planetName.equals("Jupiter Planet")) {
					backgroundPlanet = ImageIO.read(new File("jupiter.png"));
				} else if (planetName.equals("Moon Planet")) {
					backgroundPlanet = ImageIO.read(new File("moonPlanet.png"));
				} else if (planetName.equals("Basketball Planet")) {
					backgroundPlanet = ImageIO.read(new File("basketball.png"));
				} else if (planetName.equals("Bouncy Planet")) {
					backgroundPlanet = ImageIO.read(new File("bouncyPlanet.png"));
				} else if (planetName.equals("Saturn Planet")) {
					backgroundPlanet = ImageIO.read(new File("saturnPlanet.png"));
				}
				this.planetName = planetName;
			} catch (Exception ex) {
				System.out.println("images didn't load");
			}
			backgroundPlanet = resizeImage(backgroundPlanet, screenX / 2, screenX / 2);
			
			JButton mapButton = new JButton("Travel Elsewhere");
			mapButton.addActionListener(new MapListener());
			mapButton.setBounds(screenX / 12, screenX / 12, 400, 100);
			mapButton.setFont(new Font("Tahoma", Font.BOLD, 29));

			// Create new thread to "mine" in the background
			Thread thread = new Thread(new MineThread());
			thread.start();
			this.add(mapButton);
		}

		/**
		 * MineThread
		 * seperate thread to run mine
		 * @author Felix Tai
		 */
		class MineThread implements Runnable {

			public void run() {
				while (!exit) { // keep looping until leaving planet
					commandd.enqueue("4");
					inputss.enqueue(planetName);
					try { // pause for 5 seconds before sending again
						Thread.sleep(500);
					} catch (Exception e) {
						System.out.println("couldn't sleep thread");
					}
				}
			}
		}
		
		//ButtonListener for selecting new planet
		class MapListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(new MapPanel());
				frame.invalidate();
				frame.validate();
				exit = true;
			}
		}

		/*
		 * paintComponent
		 * 
		 * @param Graphics g
		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g); // required to ensure the panel is correctly redrawn
			g.drawImage(backgroundImage, 0, 0, null);
			g.drawImage(backgroundPlanet, screenX - backgroundPlanet.getWidth(), 0, null);
			g.drawImage(userShip, screenX/5, screenY/20, null);
			g.setColor(clearColour);
			g.fillRect(0, screenY / 3, screenX / 4, 2 * screenY / 3);

			g.setFont(bigFont);
			g.setColor(textColour);
			g.drawString("Steel: " + resources[0], 20, screenY / 3 + 100); //Steel, graphene, plutonium, starlite pyroxium, blast crystal, intellectium
			g.drawString("Graphene: " + resources[1], 20, screenY / 3 + 150);
			g.drawString("Plutonium: " + resources[2], 20, screenY / 3 + 200);
			g.drawString("Starlite: " + resources[3], 20, screenY / 3 + 250);
			g.drawString("Pyroxium: " + resources[4], 20, screenY / 3 + 300);
			g.drawString("Blast Crystal: " + resources[5], 20, screenY / 3 + 350);
			g.drawString("Intellectium: " + resources[6], 20, screenY / 3 + 400);

			repaint();
		}
	}
	

	/**
	 * resizeImage 
	 * resizes a Buffered image to specified size
	 * @param BufferedImage to be resized, integers of width and height to be resized to
	 * @return BufferedImage that is resized
	 */
	public BufferedImage resizeImage(BufferedImage image, int resizeWidth, int resizeHeight) {
		// resize a buffered image into an image
		Image temp = image.getScaledInstance(resizeWidth, resizeHeight, Image.SCALE_DEFAULT);
		BufferedImage originalImg = new BufferedImage(resizeWidth, resizeHeight, BufferedImage.TYPE_INT_ARGB);

		// draw image onto Buffered image to copy it
		Graphics2D g2d = originalImg.createGraphics();
		g2d.drawImage(temp, 0, 0, null);
		g2d.dispose();

		return originalImg;
	}
	
	/**
	 * combineSpaceship
	 * Combines base ship with engine and weapons
	 * @param engineLevel
	 * @return combined ship BufferedImage
	 */

	public BufferedImage combineSpaceship(int engineLevel) {
		BufferedImage builtShip = new BufferedImage(1000, 400, BufferedImage.TYPE_INT_ARGB);
		Graphics g = builtShip.getGraphics(); //Create graphics to draw onto image
		g.drawImage(ship, 0, 0, null); //draw ship
		if(engineLevel == 1) { //draw engine
			g.drawImage(engine1, 0, 0, null);
		}else if(engineLevel == 2) {
			g.drawImage(engine2, 0, 0, null);
		}else if(engineLevel == 3) {
			g.drawImage(engine3, 0, 0, null);
		}else if(engineLevel == 4) {
			g.drawImage(engine4, 0, 0, null);
		}else if(engineLevel == 5) {
			g.drawImage(engine5, 0, 0, null);
		}
		g.drawImage(laser, 600, 150, null); //draw all weapons
		g.drawImage(shieldJammer, 600, 200, null);
		g.drawImage(missile, 600, 250, null);
		return builtShip;
	}
}

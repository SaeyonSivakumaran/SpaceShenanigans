
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
import java.io.*;
import java.lang.Thread;
import java.net.*;
import java.util.Scanner;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;


public class SpaceClient {
	Socket mySocket; // socket for connection
	BufferedReader input; // reader for network stream
	PrintWriter output; // printwriter for network output
	boolean running = true; // thread status via boolean
	String command;
	Queuee<String> commandd;
	String input1,input2;
	Scanner inputs;
	Queuee<String> inputss;
	String username;
	int[] resources;
	private EngineModule engine;
	private ShieldModule shield;
	private WeaponModule weaponModule;
	private MiningModule miningModule;
	private DeepSpaceViewer deepSpaceViewer; 
	private Planet yarnPlanet;
	private Planet flatEarth;
	private Planet potatoPlanet;
	private Planet specklePlanet;
	private Planet fracturedPlanet;
	private Planet jupiter;
	private Planet moonPlanet;
	//Ship health
	private int health;
	MapPanel display;
	Queuee<String> instructions;
	ArrayList<String> players;
	String location;
	static JFrame frame;


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
		this.engine = new EngineModule();
		this.shield = new ShieldModule();
		this.weaponModule = new WeaponModule();
		this.miningModule = new MiningModule();
		this.deepSpaceViewer = new DeepSpaceViewer();
		this.health=100;
		this.location="depot";
		try {
			mySocket = new Socket("127.0.0.1", 799); // attempt socket connection (local address)
			InputStreamReader stream1 = new InputStreamReader(mySocket.getInputStream()); // Stream for network input
			input = new BufferedReader(stream1);

			output = new PrintWriter(mySocket.getOutputStream()); // assign printwriter to network stream

			while(running){
				command=inputs.nextLine();
				if (command.equals("login")){

					input1=inputs.nextLine();
					input2=inputs.nextLine();
					output.println("login:"+input1+","+input2);
					output.flush();
					command=input.readLine();
					if (command.equals("loginaccepted")){
						this.username=input1;
						System.out.println("Connection made.");
						running=false;
					}else {
						System.out.println("login failed");
					}
				}else if(command.equals("newaccount")){

					input1=inputs.nextLine();
					input2=inputs.nextLine();
					output.println("newaccount:"+input1+","+input2);
					output.flush();
					command=input.readLine();
					if (command.equals("accountvalid")){
						System.out.println("account valid");
					}else {
						System.out.println("account invalid");
					}
				}
				this.engine = new EngineModule();
				this.shield = new ShieldModule();
				this.weaponModule = new WeaponModule();
				this.miningModule = new MiningModule();
				this.deepSpaceViewer = new DeepSpaceViewer();
				this.resources = new int[]{0, 0, 0, 0, 0, 0, 0};


			}




			Thread t= new Thread(new Input());
			t.start();
			
			frame=new JFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setContentPane(new MapPanel());
			frame.setPreferredSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
			frame.pack();
			frame.setResizable(false);
			frame.setVisible(true);
			
			output.println("playersUpdate:"+username);
			output.flush();
			output.println("shipUpdate:"+username);
			output.flush();
			running=true;
			instructions=new Queuee<String>();
			while(running){
				command=commandd.dequeue();
				if (command.equals("1")){
					while(!inputss.hasItem()) {
					}
					input2=inputss.dequeue();
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
					output.println("logout:"+username);
					running=false;
				}
				output.flush();
			}

			try {  
				input.close();
				output.close();
				mySocket.close();
				System.out.println("im ey lamo");
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
						System.out.println(msg);
						// decipher server messages
						if (msg.substring(0,msg.indexOf(":")).equals("arrived")) {
							command=msg.substring(msg.indexOf(":")+1);
							location=""+command;
							System.out.println(location);
						}else if (msg.substring(0,msg.indexOf(":")).equals("upgrade")) {
							command=msg.substring(msg.indexOf(":")+1);
							switch (Integer.parseInt(command.substring(0,command.indexOf(",")))){
							case 1: engine.upgrade();
							case 2: shield.upgrade();
							case 3: weaponModule.upgrade();
							case 4: miningModule.upgrade();
							case 5: deepSpaceViewer.upgrade();
							}
						}else if (msg.substring(0,msg.indexOf(":")).equals("mine")) {
							command=msg.substring(msg.indexOf(":")+1);
							resources[Integer.parseInt(command)]+=Integer.parseInt(command.substring(command.indexOf(",")+1));
							if (location.equals("yarnPlanet")){
								yarnPlanet.setResource(yarnPlanet.getResource()-Integer.parseInt(command));
							}else if (location.equals("flatEarth")){
								flatEarth.setResource(flatEarth.getResource()-Integer.parseInt(command));
							}else if (location.equals("potatoPlanet")){
								potatoPlanet.setResource(potatoPlanet.getResource()-Integer.parseInt(command));
							}else if (location.equals("specklePlanet")){
								specklePlanet.setResource(specklePlanet.getResource()-Integer.parseInt(command));
							}else if (location.equals("fracturedPlanet")){
								fracturedPlanet.setResource(fracturedPlanet.getResource()-Integer.parseInt(command));
							}else if (location.equals("jupiter")){
								jupiter.setResource(jupiter.getResource()-Integer.parseInt(command));
							}else if (location.equals("moonPlanet")){
								moonPlanet.setResource(moonPlanet.getResource()-Integer.parseInt(command));
							}
						}else if (msg.substring(0,msg.indexOf(":")).equals("inventory")) {

							command=msg.substring(msg.indexOf(":")+1);
							//send to map
							//display offer
							//send back response
						}else if (msg.substring(0,msg.indexOf(":")).equals("updateResource")) {

							if (command.substring(0,command.indexOf(",")).equals("yarnPlanet")){
								yarnPlanet.setResource(Integer.parseInt(command.substring(command.indexOf(","))));
							}else if (command.substring(0,command.indexOf(",")).equals("flatEarth")){
								flatEarth.setResource(Integer.parseInt(command.substring(command.indexOf(","))));
							}else if (command.substring(0,command.indexOf(",")).equals("potatoPlanet")){
								potatoPlanet.setResource(Integer.parseInt(command.substring(command.indexOf(","))));
							}else if (command.substring(0,command.indexOf(",")).equals("specklePlanet")){
								specklePlanet.setResource(Integer.parseInt(command.substring(command.indexOf(","))));
							}else if (command.substring(0,command.indexOf(",")).equals("fracturedPlanet")){
								fracturedPlanet.setResource(Integer.parseInt(command.substring(command.indexOf(","))));
							}else if (command.substring(0,command.indexOf(",")).equals("jupiter")){
								jupiter.setResource(Integer.parseInt(command.substring(command.indexOf(","))));
							}else if (command.substring(0,command.indexOf(",")).equals("moonPlanet")){
								moonPlanet.setResource(Integer.parseInt(command.substring(command.indexOf(","))));
							}
						}else if (msg.substring(0,msg.indexOf(":")).equals("battle")) {
							command=msg.substring(msg.indexOf(":")+1);
							health-=Integer.parseInt(command);
						}else if (msg.substring(0,msg.indexOf(":")).equals("playersUpdate")) {

							command=msg.substring(msg.indexOf(":")+1);
							players.clear();
							while(command.length()>1){
								players.add(command.substring(0,command.indexOf(",")));
								command=command.substring(command.indexOf(",")+1);
							}
						}else if (msg.substring(0,msg.indexOf(":")).equals("shipUpdate")) {

							command=msg.substring(msg.indexOf(":")+1);
							engine.setLevel(Integer.parseInt(command.substring(0,command.indexOf(","))));
							command=command.substring(command.indexOf(",")+1);
							shield.setLevel(Integer.parseInt(command.substring(0,command.indexOf(","))));
							command=command.substring(command.indexOf(",")+1);
							weaponModule.setLevel(Integer.parseInt(command.substring(0,command.indexOf(","))));
							command=command.substring(command.indexOf(",")+1);
							miningModule.setLevel(Integer.parseInt(command.substring(0,command.indexOf(","))));
							command=command.substring(command.indexOf(",")+1);
							deepSpaceViewer.setLevel(Integer.parseInt(command.substring(0,command.indexOf(","))));
						}else if (msg.substring(0,msg.indexOf(":")).equals("planetsUpdate")) {
							command=msg.substring(msg.indexOf(":")+1);
							yarnPlanet.setResource(Integer.parseInt(command.substring(0,command.indexOf(","))));
							command=command.substring(command.indexOf(",")+1);
							flatEarth.setResource(Integer.parseInt(command.substring(0,command.indexOf(","))));
							command=command.substring(command.indexOf(",")+1);
							potatoPlanet.setResource(Integer.parseInt(command.substring(0,command.indexOf(","))));
							command=command.substring(command.indexOf(",")+1);
							specklePlanet.setResource(Integer.parseInt(command.substring(0,command.indexOf(","))));
							command=command.substring(command.indexOf(",")+1);
							fracturedPlanet.setResource(Integer.parseInt(command.substring(0,command.indexOf(","))));
							command=command.substring(command.indexOf(",")+1);
							jupiter.setResource(Integer.parseInt(command.substring(0,command.indexOf(","))));
							command=command.substring(command.indexOf(",")+1);
							moonPlanet.setResource(Integer.parseInt(command.substring(0,command.indexOf(","))));
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
	
	public class MapPanel extends JPanel {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenX = (int) screenSize.getWidth();
		int screenY = (int) screenSize.getHeight();
		Image backgroundImage = null;
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
		
		Color clearColour = new Color(239, 161, 4, 225); // create 50% transparent colour
		Font bigFont = new Font("Helvetica", Font.BOLD, 40);
		Color textColour = new Color(209, 0, 198);
		String planetName = "";
		String drawText1 = "";
		String drawText2 = "";
		int tempCount = 0;
		SpaceClient client;

		public MapPanel() {
			try { // loading images
				backgroundImage = new ImageIcon("SpaceMap.png").getImage();
				yarnPlanet = ImageIO.read(new File("yarnPlanet.png"));
				flatPlanet = ImageIO.read(new File("flatEarth.png"));
				potatoPlanet = ImageIO.read(new File("potatoPlanet.png"));
				specklePlanet = ImageIO.read(new File("specklePlanet.png"));
				fracturedPlanet = ImageIO.read(new File("fracturedPlanet.png"));
				depot = ImageIO.read(new File("SpaceDepot.png"));
				jupiterPlanet = ImageIO.read(new File("jupiter.png"));
				moonPlanet = ImageIO.read(new File("moonPlanet.png"));
			} catch (Exception ex) {
				System.out.println("image didn't load");
			}
			
			this.setLayout(null);

			// Resize images
			backgroundImage = backgroundImage.getScaledInstance(screenX, screenY, Image.SCALE_DEFAULT);
			yarnPlanet = resizeImage(yarnPlanet, screenX / 12, screenX / 12);
			flatPlanet = resizeImage(flatPlanet, screenX / 12, screenX / 12);
			potatoPlanet = resizeImage(potatoPlanet, screenX/12, screenX/12);
			specklePlanet = resizeImage(specklePlanet, screenX/12, screenX/12);
			fracturedPlanet = resizeImage(fracturedPlanet, screenX/12, screenX/12);
			depot = resizeImage(depot, screenX/8, screenX/8);
			jupiterPlanet = resizeImage(jupiterPlanet, screenX/12, screenX/12);
			moonPlanet = resizeImage(moonPlanet, screenX/12, screenX/12);
			
			yarnLabel = createImageButton(yarnPlanet);
			yarnLabel.setBounds(0, 100, yarnPlanet.getWidth(), yarnPlanet.getHeight());
			
			flatLabel = createImageButton(flatPlanet);
			flatLabel.setBounds(50, 2*screenY/3 - flatPlanet.getWidth() - 100, flatPlanet.getWidth(), flatPlanet.getHeight());
			
			potatoLabel = createImageButton(potatoPlanet);
			potatoLabel.setBounds(screenX - potatoPlanet.getWidth() - 50, 50, potatoPlanet.getWidth(), potatoPlanet.getHeight());
			
			speckleLabel = createImageButton(specklePlanet);
			speckleLabel.setBounds(screenX/5 - specklePlanet.getWidth()/2, screenY/2 - specklePlanet.getHeight()/2, specklePlanet.getWidth(), specklePlanet.getHeight());
			
			fracturedLabel = createImageButton(fracturedPlanet);
			fracturedLabel.setBounds(screenX - yarnPlanet.getWidth() - 200, 500, yarnPlanet.getWidth(), yarnPlanet.getHeight());
			
			depotLabel = createImageButton(depot);
			depotLabel.setBounds(screenX/2 - depot.getWidth()/2, screenY/2 - depot.getHeight(), depot.getWidth(), depot.getHeight());
			
			jupiterLabel = createImageButton(jupiterPlanet);
			jupiterLabel.setBounds(screenX - jupiterPlanet.getWidth() - 400, screenY/2, jupiterPlanet.getWidth(), jupiterPlanet.getHeight());
			
			moonLabel = createImageButton(moonPlanet);
			moonLabel.setBounds(screenX - moonPlanet.getWidth() - 400, 200, moonPlanet.getWidth(), moonPlanet.getHeight());
			
			JButton button = new JButton("Confirm Travel");
			button.setFont(new Font("Tahoma", Font.PLAIN, 28));
			button.setBounds(screenX/2 - 150, screenY - 200, 300, 100);
			button.addActionListener(new TravelButtonListener());
			
			this.add(yarnLabel);
			this.add(flatLabel);
			this.add(potatoLabel);
			this.add(speckleLabel);
			this.add(fracturedLabel);
			this.add(depotLabel);
			this.add(jupiterLabel);
			this.add(moonLabel);
			this.add(button);
			
		}

		/*
		 * resizeImage 
		 * resizes a Buffered image to specified size
		 * @param BufferedImage to be resized, integers of width and height to be resized to
		 * @return BufferedImage that is resized
		 */
		public BufferedImage resizeImage(BufferedImage image, int resizeWidth, int resizeHeight) {
			//resize a buffered image into an image 
			Image temp = image.getScaledInstance(resizeWidth, resizeHeight, Image.SCALE_DEFAULT);
			BufferedImage originalImg = new BufferedImage(resizeWidth, resizeHeight, BufferedImage.TYPE_INT_ARGB);

			//draw image onto Buffered image to copy it
			Graphics2D g2d = originalImg.createGraphics();
			g2d.drawImage(temp, 0, 0, null);
			g2d.dispose();

			return originalImg;
		}

		/*
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
		
		public class TravelButtonListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				commandd.enqueue("1");
				if(planetName.equals("Yarn Planet")) {
					frame.setContentPane(new TravelPanel("Yarn Planet", 50));
					frame.invalidate();
					frame.validate();
					inputss.enqueue("yarnPlanet");
				}else if (planetName.equals("Flat Planet")){
					frame.setContentPane(new TravelPanel("Flat Planet", 50));
					frame.invalidate();
					frame.validate();
					inputss.enqueue("flatEarth");
				}else if (planetName.equals("Potato Planet")){
					frame.setContentPane(new TravelPanel("Potato Planet", 50));
					frame.invalidate();
					frame.validate();	
					inputss.enqueue("potatoPlanet");
				}else if (planetName.equals("Speckle Planet")){
					frame.setContentPane(new TravelPanel("Speckle Planet", 50));
					frame.invalidate();
					frame.validate();
					inputss.enqueue("specklePlanet");
				}else if (planetName.equals("Fractured Planet")){
					frame.setContentPane(new TravelPanel("Fractured Planet", 50));
					frame.invalidate();
					frame.validate();
					inputss.enqueue("fracturedPlanet");
				}else if (planetName.equals("Depot")){
					frame.setContentPane(new TravelPanel("Depot", 50));
					frame.invalidate();
					frame.validate();
					inputss.enqueue("depot");
				}else if (planetName.equals("Jupiter Planet")){
					frame.setContentPane(new TravelPanel("Jupiter Planet", 50));
					frame.invalidate();
					frame.validate();
					inputss.enqueue("jupiter");
				}else if (planetName.equals("Moon Planet")){
					frame.setContentPane(new TravelPanel("Moon Planet", 50));
					frame.invalidate();
					frame.validate();
					inputss.enqueue("moonPlanet");
				}
			}
		}
		
		//MouseAdapter for planets
		public class PlanetListener extends MouseAdapter {
			BufferedImage planet;
			JLabel source;
			
			PlanetListener(BufferedImage planet){
				this.planet = planet;
			}
			   public void mouseClicked(MouseEvent e) {
				   boolean opaque = (planet.getRGB(e.getX(), e.getY()) & 0x00ffffff) != 0;
					if (opaque) {
						source = (JLabel)e.getSource();

						if(source == yarnLabel) { //yarnLabel
							planetName = "Yarn Planet";
						}else if(source == flatLabel) { //flatLabel
							planetName = "Flat Planet";
						}else if (source == potatoLabel) { //potatoLabel
							planetName = "Potato Planet";
						}else if (source == speckleLabel) { //speckleLabel
							planetName = "Speckle Planet";
						}else if (source == fracturedLabel) { //fracturedLabel
							planetName = "Fractured Planet";
						}else if (source == depotLabel){ //depotLabel
							planetName = "Depot";
						}else if (source == jupiterLabel) { //jupiterLabel
							planetName = "Jupiter Planet";
						}else if (source == moonLabel) { //moonLabel
							planetName = "Moon Planet";
						}
					}
			   }
			}

		/*
		 * paintComponent
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
			if (!planetName.equals("Depot")) { //No projected resources for depot
				drawText2 = "Deep Space Viewer projected Resources: " + tempCount;
			}else {
				drawText2 = "";
			}
			g.drawString(drawText1, 20, 2*screenY/3 + 50);
			g.drawString(drawText2, 20, 2*screenY/3 + 150);
			
			repaint();
		}

		
		
		public void sendInstructions(String instructions){
			
		}

	}
}

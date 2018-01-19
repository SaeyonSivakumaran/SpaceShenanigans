
import javax.swing.*;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;

public class BattlePanel extends JPanel {
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int screenX = (int) screenSize.getWidth();
	int screenY = (int) screenSize.getHeight();
	Image backgroundImage = null;
	Color clearRed = new Color(247, 9, 49, 150); // create 50% transparent colours
	Color clearGreen = new Color(16, 196, 9, 200);
	BufferedImage backgroundPlanet = null;
	BufferedImage userShip;
	BufferedImage opponentShip;
	JButton button1;
	JButton button2;
	JButton button3;
	String userName;
	String opponentName;
	
	
	public BattlePanel(String planetName, BufferedImage userShip, BufferedImage opponentShip, String userName, String opponentName) {
		try { // loading images
			backgroundImage = new ImageIcon("SpaceMap.png").getImage();
			if(planetName.equals("Yarn Planet")) {
				backgroundPlanet = ImageIO.read(new File("yarnPlanet.png"));
			}else if (planetName.equals("Flat Planet")){
				backgroundPlanet = ImageIO.read(new File("flatEarth.png"));
			}else if (planetName.equals("Potato Planet")){
				backgroundPlanet = ImageIO.read(new File("potatoPlanet.png"));
			}else if (planetName.equals("Speckle Planet")){
				backgroundPlanet = ImageIO.read(new File("specklePlanet.png"));
			}else if (planetName.equals("Fractured Planet")){
				backgroundPlanet = ImageIO.read(new File("fracturedPlanet.png"));			
			}else if (planetName.equals("Jupiter Planet")){
				backgroundPlanet = ImageIO.read(new File("jupiter.png"));
			}else if (planetName.equals("Moon Planet")){
				backgroundPlanet = ImageIO.read(new File("moonPlanet.png"));
			}else if (planetName.equals("Basketball Planet")) {
				backgroundPlanet = ImageIO.read(new File("basketball.png"));
			}else if (planetName.equals("Bouncy Planet")) {
				backgroundPlanet = ImageIO.read(new File("bouncyPlanet.png"));
			}else if (planetName.equals("Saturn Planet")) {
				backgroundPlanet = ImageIO.read(new File("saturnPlanet.png"));
			}
		} catch (Exception ex) {
			System.out.println("image didn't load");
		}
		this.userShip = resizeImage(userShip, screenX/4, (screenX/4)/3);
		this.opponentShip = resizeImage(opponentShip, screenX/4, (screenX/4)/3);
		this.userName = userName;
		this.opponentName = opponentName;
		
		//TODO no need to resize backgroundImage
		backgroundImage = backgroundImage.getScaledInstance(screenX, screenY, Image.SCALE_DEFAULT);
		backgroundPlanet = resizeImage(backgroundPlanet, screenX / 3, screenX / 3);
		
		this.setLayout(null);
		this.add(new AttackPanel());

	}
	
	public class AttackButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == button1){
				//Send server a message saying that button has been pressed
			}else if (e.getSource() == button2) {
				
			}
		}
	}

	//Panel for when it is not time to select attack
	public class TextPanel extends JPanel {
		TextPanel(String serverMsg) {
			this.setPreferredSize(new Dimension(screenX / 2, screenY / 3));
			this.setBounds(0, 2 * screenY / 3, screenX / 2, screenY);
			this.setBackground(clearGreen);

			JLabel messageLabel;
			if (serverMsg.equals("hitMissile")) { // messages to be displayed when your attack has hit
				messageLabel = new JLabel("Your missile has hit " + opponentName + "!", SwingConstants.CENTER);
			} else if (serverMsg.equals("hitMissile")) {
				messageLabel = new JLabel("Your laser has hit " + opponentName + "!", SwingConstants.CENTER);
			} else if (serverMsg.equals("disabledShield")) {
				messageLabel = new JLabel("Your ShieldJammer has disabled " + opponentName + "'s shields!",
						SwingConstants.CENTER);
			} else if (serverMsg.equals("shieldDisabled")) {
				messageLabel = new JLabel(opponentName + "'s ShieldJammer has hit!", SwingConstants.CENTER);
			} else if (serverMsg.indexOf("laserDamage") == 0) {
				messageLabel = new JLabel(opponentName + "'s Laser has hit!", SwingConstants.CENTER);
			} else if (serverMsg.indexOf("missileDamage") == 0) {
				messageLabel = new JLabel(opponentName + "'s Missile has hit!", SwingConstants.CENTER);
			} else if (serverMsg.equals("missed")) {
				messageLabel = new JLabel("Attack has missed!", SwingConstants.CENTER);
			} else {
					if() {
					messageLabel = new JLabel("Waiting for " + opponentName);
					}
			}

			messageLabel.setFont(new Font("Tahoma", Font.PLAIN, 35));
			this.add(messageLabel);
		}
	}

	public class AttackPanel extends JPanel {
		AttackPanel() {
			// Create Panel with buttons to select attack
			this.setLayout(new BorderLayout());
			this.setPreferredSize(new Dimension(screenX / 2, screenY / 3));
			this.setBounds(0, 2 * screenY / 3, screenX / 2, screenY);
			this.setBackground(clearGreen);

			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new FlowLayout());
			buttonPanel.setBackground(clearGreen);

			JLabel attackLabel = new JLabel("Attack!", SwingConstants.CENTER);
			attackLabel.setFont(new Font("Tahoma", Font.PLAIN, 35));

			button1 = new JButton("Use Laser");
			button1.setFont(new Font("Tahoma", Font.PLAIN, 28));
			button1.addActionListener(new AttackButtonListener());

			button2 = new JButton("Use Missile");
			button2.setFont(new Font("Tahoma", Font.PLAIN, 28));
			button2.addActionListener(new AttackButtonListener());
			
			button3 = new JButton("Use ShieldJammer");
			button3.setFont(new Font("Tahoma", Font.PLAIN, 28));
			button3.addActionListener(new AttackButtonListener());

			buttonPanel.add(button1);
			buttonPanel.add(button2);
			buttonPanel.add(button3);
			this.add(attackLabel, BorderLayout.NORTH);
			this.add(buttonPanel, BorderLayout.CENTER);
		}
	}
	
	
	/*
	 * resizeImage 
	 * resizes a Buffered image to specified size
	 * @param BufferedImage to be resized, integers of width and height to be resized to
	 * @return BufferedImage that is resized
	 */
	public static BufferedImage resizeImage(BufferedImage image, int resizeWidth, int resizeHeight) {
		//resize a buffered image into an image 
		Image temp = image.getScaledInstance(resizeWidth, resizeHeight, Image.SCALE_DEFAULT);
		BufferedImage originalImg = new BufferedImage(resizeWidth, resizeHeight, BufferedImage.TYPE_INT_ARGB);

		//draw image onto Buffered image to copy it
		Graphics2D g2d = originalImg.createGraphics();
		g2d.drawImage(temp, 0, 0, null);
		g2d.dispose();
		
		return originalImg;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g); // required to ensure the panel is correctly redrawn
		g.drawImage(backgroundImage, 0, 0, null);

		g.drawImage(backgroundPlanet, screenX / 20, screenX / 20, null);

		// Draw translucent rectangle for enemy side
		g.setColor(clearRed);
		g.fillRect(screenX/2, 0, screenX/2, screenY);
		
		g.drawImage(userShip, screenX/12, screenY/2 - userShip.getHeight(), null);
		g.drawImage(opponentShip, screenX - opponentShip.getWidth() - screenX/12, screenY/2 - userShip.getHeight(), null);

		repaint();
	}
	
	//Method to create spaceship image
	public static BufferedImage combineSpaceship(BufferedImage ship, BufferedImage engine, BufferedImage laser, BufferedImage shieldJammer, BufferedImage missile) {
		
		BufferedImage builtShip = new BufferedImage(1000, 400, BufferedImage.TYPE_INT_ARGB);
		Graphics g = builtShip.getGraphics();
		g.drawImage(ship, 0, 0, null);
		g.drawImage(engine, 0, 0, null);
		g.drawImage(laser, 600, 150, null);
		g.drawImage(missile, 600, 200, null);
		g.drawImage(shieldJammer, 600, 250, null);
		return builtShip;
	}
	
	public static void main(String args[]) {
		BufferedImage ship = null;
		BufferedImage laser = null;
		BufferedImage shieldJammer = null;
		BufferedImage missile = null;
		BufferedImage engine1 = null;
		BufferedImage engine2 = null;
		BufferedImage engine3 = null;
		BufferedImage engine4 = null;
		BufferedImage engine5 = null;

		try {
			ship = ImageIO.read(new File("SpaceShip.png"));
			
			engine1 = ImageIO.read(new File("EngineModule1.png"));
			engine2 = ImageIO.read(new File("EngineModule2.png"));
			engine3 = ImageIO.read(new File("EngineModule3.png"));
			engine4 = ImageIO.read(new File("EngineModule4.png"));
			engine5 = ImageIO.read(new File("EngineModule5.png"));

			laser = ImageIO.read(new File("Laser.png"));
			missile = ImageIO.read(new File("MissileLauncher.png"));
			shieldJammer = ImageIO.read(new File("ShieldJammer.png"));
		}catch(Exception e) {
			System.out.println("Failed to load images from main");
		}
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new BattlePanel("Jupiter Planet", combineSpaceship(ship, engine4, laser, shieldJammer, missile), combineSpaceship(ship, engine2, laser, shieldJammer, missile), "noob_1", "noob_2"));
		frame.setPreferredSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
				(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}
}

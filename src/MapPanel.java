
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;

public class MapPanel extends JPanel {

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static JFrame frame = new JFrame();
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
	int tempCount = 0;
	SpaceClient client;

	public MapPanel() {
		//this.client = client;
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
			basketballPlanet = ImageIO.read(new File("basketball.png"));
			bouncyPlanet = ImageIO.read(new File("bouncyPlanet.png"));
			saturnPlanet = ImageIO.read(new File("saturnPlanet.png"));
		} catch (Exception ex) {
			System.out.println("image didn't load");
		}

		this.setLayout(null);

		// Resize images
		backgroundImage = backgroundImage.getScaledInstance(screenX, screenY, Image.SCALE_DEFAULT);
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

	/*
	 * resizeImage resizes a Buffered image to specified size
	 * 
	 * @param BufferedImage to be resized, integers of width and height to be
	 * resized to
	 * 
	 * @return BufferedImage that is resized
	 */
	public static BufferedImage resizeImage(BufferedImage image, int resizeWidth, int resizeHeight) {
		// resize a buffered image into an image
		Image temp = image.getScaledInstance(resizeWidth, resizeHeight, Image.SCALE_DEFAULT);
		BufferedImage originalImg = new BufferedImage(resizeWidth, resizeHeight, BufferedImage.TYPE_INT_ARGB);

		// draw image onto Buffered image to copy it
		Graphics2D g2d = originalImg.createGraphics();
		g2d.drawImage(temp, 0, 0, null);
		g2d.dispose();

		return originalImg;
	}

	/*
	 * createImageButton creates JLabel with MouseListener so it acts like a button
	 * 
	 * @param Buffered image to be turned into JLabel
	 * 
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
				frame.setContentPane(new TravelPanel("Yarn Planet", 50));
				frame.invalidate();
				frame.validate();
			} else if (planetName.equals("Flat Planet")) {
				frame.setContentPane(new TravelPanel("Flat Planet", 50));
				frame.invalidate();
				frame.validate();
			} else if (planetName.equals("Potato Planet")) {
				frame.setContentPane(new TravelPanel("Potato Planet", 50));
				frame.invalidate();
				frame.validate();
			} else if (planetName.equals("Speckle Planet")) {
				frame.setContentPane(new TravelPanel("Speckle Planet", 50));
				frame.invalidate();
				frame.validate();
			} else if (planetName.equals("Fractured Planet")) {
				frame.setContentPane(new TravelPanel("Fractured Planet", 50));
				frame.invalidate();
				frame.validate();
			} else if (planetName.equals("Depot")) {
				frame.setContentPane(new TravelPanel("Depot", 50));
				frame.invalidate();
				frame.validate();
			} else if (planetName.equals("Jupiter Planet")) {
				frame.setContentPane(new TravelPanel("Jupiter Planet", 50));
				frame.invalidate();
				frame.validate();
			} else if (planetName.equals("Moon Planet")) {
				frame.setContentPane(new TravelPanel("Moon Planet", 50));
				frame.invalidate();
				frame.validate();
			} else if (planetName.equals("Basketball Planet")) {
				frame.setContentPane(new TravelPanel("Basketball Planet", 50));
				frame.invalidate();
				frame.validate();
			} else if (planetName.equals("Saturn Planet")) {
				frame.setContentPane(new TravelPanel("Saturn Planet", 50));
				frame.invalidate();
				frame.validate();
			} else if (planetName.equals("Bouncy Planet")) {
				frame.setContentPane(new TravelPanel("Bouncy Planet", 50));
				frame.invalidate();
				frame.validate();
			}
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
			boolean opaque = (planet.getRGB(e.getX(), e.getY()) & 0x00ffffff) != 0;
			if (opaque) {
				source = (JLabel) e.getSource();

				if (source == yarnLabel) { // yarnLabel
					planetName = "Yarn Planet";
				} else if (source == flatLabel) { // flatLabel
					planetName = "Flat Planet";
				} else if (source == potatoLabel) { // potatoLabel
					planetName = "Potato Planet";
				} else if (source == speckleLabel) { // speckleLabel
					planetName = "Speckle Planet";
				} else if (source == fracturedLabel) { // fracturedLabel
					planetName = "Fractured Planet";
				} else if (source == depotLabel) { // depotLabel
					planetName = "Depot";
				} else if (source == jupiterLabel) { // jupiterLabel
					planetName = "Jupiter Planet";
				} else if (source == moonLabel) { // moonLabel
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
			drawText2 = "Deep Space Viewer projected Resources: " + tempCount;
		} else {
			drawText2 = "";
		}
		g.drawString(drawText1, 20, 2 * screenY / 3 + 50);
		g.drawString(drawText2, 20, 2 * screenY / 3 + 150);

		repaint();
	}

	public static void main(String args[]) {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new MapPanel());
		frame.setPreferredSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
				(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}

	public void sendInstructions(String instructions) {

	}

}


import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;

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
	Color clearColour = new Color(239, 161, 4, 225); // create 50% transparent colour
	Font bigFont = new Font("Helvetica", Font.BOLD, 40);
	Color textColour = new Color(209, 0, 198);
	String planetName = "";
	String drawText1 = "";
	String drawText2 = "";
	int tempCount = 0;

	public MapPanel() {
		try { // loading images
			backgroundImage = new ImageIcon("SpaceMap.png").getImage();
			yarnPlanet = ImageIO.read(new File("yarnPlanet.png"));
			flatPlanet = ImageIO.read(new File("flatEarth.png"));
			potatoPlanet = ImageIO.read(new File("potatoPlanet.png"));
			specklePlanet = ImageIO.read(new File("specklePlanet.png"));
			fracturedPlanet = ImageIO.read(new File("fracturedPlanet.png"));
			depot = ImageIO.read(new File("SpaceDepot.png"));
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
		
		yarnLabel = createImageButton(yarnPlanet);
		yarnLabel.setBounds(0, 100, yarnPlanet.getWidth(), yarnPlanet.getHeight());
		
		flatLabel = createImageButton(flatPlanet);
		flatLabel.setBounds(50, 2*screenY/3 - flatPlanet.getWidth() - 100, flatPlanet.getWidth(), flatPlanet.getHeight());
		
		potatoLabel = createImageButton(potatoPlanet);
		potatoLabel.setBounds(screenX - potatoPlanet.getWidth() - 50, 50, potatoPlanet.getWidth(), potatoPlanet.getHeight());
		
		speckleLabel = createImageButton(specklePlanet);
		speckleLabel.setBounds(screenX/5 - specklePlanet.getWidth()/2, screenY/2 - specklePlanet.getHeight()/2, specklePlanet.getWidth(), specklePlanet.getHeight());
		
		fracturedLabel = createImageButton(fracturedPlanet);
		//setBounds(0, 10, yarnPlanet.getWidth(), yarnPlanet.getHeight());
		
		depotLabel = createImageButton(depot);
		depotLabel.setBounds(screenX/2 - depot.getWidth()/2, screenY/2 - depot.getHeight(), depot.getWidth(), depot.getHeight());
		
		this.add(yarnLabel);
		this.add(flatLabel);
		this.add(potatoLabel);
		this.add(speckleLabel);
		this.add(fracturedLabel);
		this.add(depotLabel);
		
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

	/*
	 * createImageButton
	 * creates JLabel with MouseListener so it acts like a button
	 * @param Buffered image to be turned into JLabel
	 * @return button JLabel
	 */
	public JLabel createImageButton(final BufferedImage planet) {
		JLabel imageButton = new JLabel(new ImageIcon(planet));
		imageButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				// check if clicked area is transparent
				boolean trans = (planet.getRGB(e.getX(), e.getY()) & 0x00ffffff) != 0;
				if (trans) {
					//replace with planet name
					planetName = "Hello";
					tempCount += 1;
				}
			}
		});
		return imageButton;
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
		drawText1 = "Planet" + planetName;
		drawText2 = "Deep Space Viewer projected Resources: " + tempCount;
		g.drawString(drawText1, 20, 2*screenY/3 + 50);
		g.drawString(drawText2, 20, 2*screenY/3 + 150);
		
		repaint();
	}

	public static void main(String args[]) {
		JFrame frame = new JFrame("hello");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new MapPanel());
		frame.setPreferredSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
				(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}

}

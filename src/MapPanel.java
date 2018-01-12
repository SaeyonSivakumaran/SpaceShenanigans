
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

	public MapPanel() {
		try { // loading images
			backgroundImage = new ImageIcon("SpaceMap.png").getImage();
			yarnPlanet = ImageIO.read(new File("yarnPlanet.png"));
			flatPlanet = ImageIO.read(new File("flatEarth.png"));
			potatoPlanet = ImageIO.read(new File("potatoPlanet.png"));
			specklePlanet = ImageIO.read(new File("specklePlanet.png"));
			fracturedPlanet = ImageIO.read(new File("fracturedPlanet.png"));
		} catch (Exception ex) {
			System.out.println("image didn't load");
		}

		// Resize images
		backgroundImage = backgroundImage.getScaledInstance(screenX, screenY, Image.SCALE_DEFAULT);
		yarnPlanet = resizeImage(yarnPlanet, screenX / 10, screenX / 10);
		flatPlanet = resizeImage(flatPlanet, screenX / 10, screenX / 10);
		potatoPlanet = resizeImage(potatoPlanet, screenX/10, screenX/10);
		specklePlanet = resizeImage(specklePlanet, screenX/10, screenX/10);
		fracturedPlanet = resizeImage(fracturedPlanet, screenX/10, screenX/10);
		
		yarnLabel = createImageButton(yarnPlanet);
		flatLabel = createImageButton(flatPlanet);
		potatoLabel = createImageButton(potatoPlanet);
		speckleLabel = createImageButton(specklePlanet);
		fracturedLabel = createImageButton(fracturedPlanet);
		
		this.add(yarnLabel);
		this.add(flatLabel);
		this.add(potatoLabel);
		this.add(speckleLabel);
		this.add(fracturedLabel);
		
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
	public static JLabel createImageButton(BufferedImage planet) {
		JLabel imageButton = new JLabel(new ImageIcon(planet));
		imageButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				// check if clicked area is transparent
				boolean trans = (planet.getRGB(e.getX(), e.getY()) & 0x00ffffff) != 0;
				if (trans) {
					System.out.println("button pressed");
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
		repaint();
	}

	public static void main(String args[]) {
		JFrame frame = new JFrame("hello");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new MapPanel());
		frame.setPreferredSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
				(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
		frame.pack();
		frame.setVisible(true);
	}

}

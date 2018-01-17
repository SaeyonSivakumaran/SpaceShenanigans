
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;

public class BattlePanel extends JPanel {
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static JFrame frame = new JFrame();
	int screenX = (int) screenSize.getWidth();
	int screenY = (int) screenSize.getHeight();
	Image backgroundImage = null;

	BufferedImage backgroundPlanet = null;
	
	public BattlePanel(String planetName) {
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
			}
		} catch (Exception ex) {
			System.out.println("image didn't load");
		}
		
		backgroundImage = backgroundImage.getScaledInstance(screenX, screenY, Image.SCALE_DEFAULT);
		backgroundPlanet = resizeImage(backgroundPlanet, screenX / 4, screenX / 4);
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
		g.drawImage()
		
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

}

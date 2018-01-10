
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class MapPanel extends JPanel {

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int screenX = (int) screenSize.getWidth();
	int screenY = (int) screenSize.getHeight();
	Image backgroundImage = null;
	Image yarnPlanet = null;
	
	
	public MapPanel() {
		try { // loading images
			backgroundImage = new ImageIcon("SpaceMap.png").getImage();
			yarnPlanet = new ImageIcon("yarnPlanet.png").getImage();
		} catch (Exception ex) {
			System.out.println("image didn't load");
		}
		yarnPlanet = yarnPlanet.getScaledInstance(screenX/10, screenX/10, Image.SCALE_DEFAULT);
		backgroundImage = backgroundImage.getScaledInstance(screenX, screenY, Image.SCALE_DEFAULT); // resize background image
		JButton buttonBrowse = new JButton();
		buttonBrowse.setIcon(new ImageIcon(yarnPlanet));
		buttonBrowse.setBorderPainted(false);
		buttonBrowse.setFocusPainted(false);
		buttonBrowse.setContentAreaFilled(false);
		buttonBrowse.addActionListener(new planetListener());
		this.add(buttonBrowse);
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
	
	//ActionListener for button to go back to TypeSelectFrame
    class planetListener implements ActionListener{
      public void actionPerformed(ActionEvent e){
    	  System.out.println("Poked planet");
      }
    }
	
	public static void main(String args[]) {
		JFrame frame = new JFrame("hello");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new MapPanel());
		frame.setPreferredSize(new Dimension((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
		frame.pack();
		frame.setVisible(true);
	}

}

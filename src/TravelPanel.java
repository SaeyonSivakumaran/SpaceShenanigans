
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class TravelPanel extends JPanel {
	Image backgroundIcon = null;
	int x = 0;
	public TravelPanel(){
		try { //loading images
			backgroundIcon = new ImageIcon("SpaceBackground.png").getImage();
		} catch (Exception ex) {
			System.out.println("image didn't load");
		}
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // required to ensure the panel is correctly redrawn
		g.drawImage(backgroundIcon, 0, 0, 750 - x, 300, x, 0, 750 -x , 300, null); //draw the part of the image from the left
		//g.drawImage(backgroundIcon, 750 - x, 0, null); //draw the rest of the image
		if(x < 750) {
			x += 1; //continue moving to the right
		}else {
			x = 0;
		}
		// request a repaint
		repaint();
	}
	
	public static void main(String args[]) {
		JFrame frame = new JFrame("hello");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new TravelPanel());
        frame.pack();
		frame.setVisible(true);
	}
}



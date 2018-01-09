
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class TravelPanel extends JPanel {
	Image backgroundImage = null;
	Image shipImage = null;
	Clock clock = new Clock();
	int x = 0;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int screenX = (int)screenSize.getWidth();
	int screenY = (int)screenSize.getHeight();
	
	public TravelPanel(){
		try { //loading images
			backgroundImage = new ImageIcon("SpaceBackground.png").getImage();
			shipImage = new ImageIcon("ShieldSupercharger.png").getImage();
		} catch (Exception ex) {
			System.out.println("image didn't load");
		}
		backgroundImage = backgroundImage.getScaledInstance(screenX, screenY, Image.SCALE_DEFAULT); //resize background image
	}
	public void paintComponent(Graphics g) {
		clock.update();
		super.paintComponent(g); // required to ensure the panel is correctly redrawn
		g.drawImage(backgroundImage, 0, 0, screenX - x, screenY, x, 0, screenX, screenY, null); //draw the part of the image from the left
		g.drawImage(backgroundImage, screenX - x, 0, screenX, screenY, 0, 0, x, screenY, null); //draw the rest of the image
		g.drawImage(shipImage, 200, 50, null);
		if(x < screenX) {
			x = (int) (x + clock.getElapsedTime()*1500); //continue scrolling from left to right
		}else {
			x = 0; //right image has completely covered, reset to continue the loop
		}
		// request a repaint
		repaint();
	}
	
	class Clock {
		 double elapsedTime; //time that has passed
		 double lastTimeCheck; //when the last time check occured
		 public Clock() { //constructor starts with the first time check
		 lastTimeCheck=System.nanoTime();
		 elapsedTime=0;
		 }

		 public void update() { //called each loop of the game loop
		 double currentTime = System.nanoTime(); //if the computer is fast you need more precision
		 elapsedTime=currentTime - lastTimeCheck; //update current time
		 lastTimeCheck=currentTime; //update last time check
		 }

		 //return elapsed time in milliseconds
		 public double getElapsedTime() {
		 return elapsedTime/1.0E9; //used to get the elapsed time
		 }
		}
	
	public static void main(String args[]) {
		JFrame frame = new JFrame("hello");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new TravelPanel());
        frame.setPreferredSize(new Dimension(1920,1080));
        frame.pack();
		frame.setVisible(true);
	}
}



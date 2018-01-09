
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class TravelPanel extends JPanel {
	Image backgroundIcon = null;
	Image shipImage = null;
	Clock clock = new Clock();
	int x = 0;
	public TravelPanel(){
		try { //loading images
			backgroundIcon = new ImageIcon("SpaceBackground.png").getImage();
			shipImage = new ImageIcon("ShieldSupercharger.png").getImage();
		} catch (Exception ex) {
			System.out.println("image didn't load");
		}
	}
	public void paintComponent(Graphics g) {
		clock.update();
		System.out.println(clock.getElapsedTime());
		super.paintComponent(g); // required to ensure the panel is correctly redrawn
		g.drawImage(backgroundIcon, 0, 0, 750 - x, 300, x, 0, 750, 300, null); //draw the part of the image from the left
		g.drawImage(backgroundIcon, 750 - x, 0, 750, 300, 0, 0, x, 300, null); //draw the rest of the image
		g.drawImage(shipImage, 200, 50, null);
		if(x < 750) {
			x = (int) (x + clock.getElapsedTime()*1000); //continue scrolling from left to right
		}else {
			x = 0; //right image has completely covered, reset to continue the loop
		}
		// request a repaint
		repaint();
	}
	
	class Clock {
		 long elapsedTime; //time that has passed
		 long lastTimeCheck; //when the last time check occured
		 public Clock() { //constructor starts with the first time check
		 lastTimeCheck=System.nanoTime();
		 elapsedTime=0;
		 }

		 public void update() { //called each loop of the game loop
		 long currentTime = System.nanoTime(); //if the computer is fast you need more precision
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
        frame.pack();
		frame.setVisible(true);
	}
}



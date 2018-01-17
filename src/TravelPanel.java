
import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * TravelPanel.java
 * Creates panel with countdown for player travel and background animation
 * @author Felix Tai
 * @version 1.0
 */
public class TravelPanel extends JPanel {
	Image backgroundImage = null;
	Image shipImage = null;
	Clock clock = new Clock();
	int x = 0;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int screenX = (int) screenSize.getWidth();
	int screenY = (int) screenSize.getHeight();
	Font bigFont = new Font("Helvetica", Font.BOLD, 40);
	Color clearColour = new Color(36, 5, 51, 225); // create 50% transparent colour
	Color textColour = new Color(209, 0, 198);
	String drawText1 = "";
	String drawText2 = "";
	String planetName;
	int travelSec;
	int timePassed;
	double startTime = System.currentTimeMillis(); // time of travel start
	double currentTimeCheck;
	String timeString = "";

	/*
	 * constructor
	 * @param planetName String containing name of planet being traveled to
	 * @param travelSec Integer representing the amount of time in seconds needed to travel to the planet
	 */
	public TravelPanel(String planetName, int travelSec) {
		try { // loading images
			backgroundImage = new ImageIcon("SpaceBackground.png").getImage();
			shipImage = new ImageIcon("EngineModule4.png").getImage();
		} catch (Exception ex) {
			System.out.println("image didn't load");
		}
		backgroundImage = backgroundImage.getScaledInstance(screenX, screenY, Image.SCALE_DEFAULT); // resize background
																									// image
		this.planetName = planetName;
		this.travelSec = travelSec;
	}

	/*
	 * paintComponent
	 * @param Graphics g
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // required to ensure the panel is correctly redrawn
		clock.update(); // update time

		g.drawImage(backgroundImage, 0, 0, screenX - x, screenY, x, 0, screenX, screenY, null); // draw the part of the
																								// background from the left
		g.drawImage(backgroundImage, screenX - x, 0, screenX, screenY, 0, 0, x, screenY, null); // draw the rest of the
																								// background
		g.drawImage(shipImage, 200, 50, null);

		// Draw translucent rectangle
		g.setColor(clearColour);
		g.fillRect(0, 2 * screenY / 3, screenX, screenY / 3);

		// Draw text with countdown and location being traveled to
		g.setFont(bigFont);
		g.setColor(textColour);
		currentTimeCheck = System.currentTimeMillis();
		timePassed = (int) (currentTimeCheck - startTime)/1000;

		if (timePassed <= travelSec) { // check if travel time has not elapsed yet
			if ((travelSec - timePassed) / 60 < 1) { // no minutes
				timeString = Integer.toString(travelSec - timePassed) + " sec";
			} else {
				timeString = Integer.toString((travelSec - timePassed) / 60) + " min || "
						+ Integer.toString((travelSec - timePassed) % 60) + " sec";
			}
		} else {
			// exit
		}
		drawText1 = "Travelling to: " + planetName;
		drawText2 = "Time remaining: " + timeString;
		
		g.drawString(drawText1, 20, 2*screenY/3 + 50);
		g.drawString(drawText2, 20, 2*screenY/3 + 150);

		if (x < screenX) {
			x = (int) (x + clock.getElapsedTime() * 1500); // continue scrolling from left to right
		} else {
			x = 0; // right image has completely covered, reset to continue the loop
		}
		// request a repaint
		repaint();
	}

	class Clock {
		double elapsedTime; // time that has passed
		double lastTimeCheck; // when the last time check occured

		public Clock() { // constructor starts with the first time check
			lastTimeCheck = System.nanoTime();
			elapsedTime = 0;
		}

		public void update() { // called each loop of the game loop
			double currentTime = System.nanoTime(); // if the computer is fast you need more precision
			elapsedTime = currentTime - lastTimeCheck; // update current time
			lastTimeCheck = currentTime; // update last time check
		}

		// return elapsed time in milliseconds
		public double getElapsedTime() {
			return elapsedTime / 1.0E9; // used to get the elapsed time
		}
	}
}


import javax.swing.*;
import java.awt.*;
import java.io.*;

public class MapPanel extends JPanel {

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int screenX = (int) screenSize.getWidth();
	int screenY = (int) screenSize.getHeight();
	
	public MapPanel() {
		
	}
	/*
	 * paintComponent
	 * @param Graphics g
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // required to ensure the panel is correctly redrawn
	}

}

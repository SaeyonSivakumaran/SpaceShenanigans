/**
 * SpaceServer.java
 * Class for the main server
 * @author Saeyon Sivakumaran
 */

//Imports for GUI
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JButton;
imprt javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.GridLayout;

class SpaceServer extends JFrame{
	
	//GUI Variables
	private JPanel mainPanel;
	private JPanel buttonPanel;
	private JTextArea consoleOutput;
	private JButton startButton;
	private JButton endButton;
	
	/**
	 * Constructor for SpaceServer
	 */
	SpaceServer(){
		//Setting properties of the JFrame
		this.setSize(600,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Initializing all GUI components
		mainPanel = new JPanel(new BorderLayout());
		buttonPanel = new JPanel(new GridLayout(2,1));
		consoleOutput = new JTextArea();
		consoleOutput.setEditable(false);
		startButton = new JButton("Start Server");
		endButton = new JButton("Stop Server");
		//Adding buttons to the button panel
		buttonPanel.add(startButton);
		buttonPanel.add(endButton);
		//Adding components to main panel
		mainPanel.add(consoleOutput, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.PAGE_END);
		//Completing the main frame
		this.add(mainPanel);
		this.setVisible(true);
	}
	
}

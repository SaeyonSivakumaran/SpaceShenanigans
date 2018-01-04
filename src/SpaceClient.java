
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class SpaceClient {
	Socket mySocket; // socket for connection
	BufferedReader input; // reader for network stream
	PrintWriter output; // printwriter for network output
	boolean running = true; // thread status via boolean

	public static void main(String[] args) {
		SpaceClient client = new SpaceClient(); // start the client
		client.go(); // begin the connection
	}

	/**
	 * go This method sets up the login frame and calls messengerLaunch(which
	 * constructs the main frame but leaves it invisible)
	 * 
	 * @param N/A
	 * @return N/A
	 */
	public void go() {
		try {
			mySocket = new Socket("127.0.0.1", 5000); // attempt socket connection (local address)
			InputStreamReader stream1 = new InputStreamReader(mySocket.getInputStream()); // Stream for network input
			input = new BufferedReader(stream1);

			output = new PrintWriter(mySocket.getOutputStream()); // assign printwriter to network stream

		} catch (IOException e) { // connection error occured
			System.out.println("Connection to Server Failed");
			e.printStackTrace();
		}

		System.out.println("Connection made.");

		receive();
	}

	/**
	 * receive This method continually loops and receives data from the server
	 * 
	 * @param N/A
	 * @return N/A
	 */
	public void receive() {
		// after connecting loop and keep appending[.append()] to the JTextArea

		while (running) { // loop continually receives and processes messages coming from the server
			try {
				if (input.ready()) { // check for an incoming messge
					String msg = input.readLine(); // read the message

					// decipher server messages
					if (msg.indexOf("") == 0) {

					}
				}

			} catch (IOException e) {
				System.out.println("Failed to receive msg from the server");
				e.printStackTrace();
			}
		}

	}
}

package client;

import java.net.*;
import java.util.*;
import java.io.*;

/**
 * ChatClient class represents and contains the functionality of the Client end
 * of the chat application.
 * 
 * @author Cian Gibbons 
 * @version 1.0
 */
public class ChatClient {

	private Scanner sc;
	private Socket socket;
	private String host;
	private int port;
	private BufferedReader clientInputReader;
	private OutputStream clientOutputStream;
	private PrintWriter printWriter;
	private InputStream serverInputStream;
	private BufferedReader serverInputReader;
	private String serverMessage;
	private String clientMessage;
	private boolean keepRunning = true;

	/**
	 * 
	 * @return the value of port.
	 */
	private int getPort() {
		return port;
	}
	
	/**
	 * 
	 * @param port set the value of port.
	 */
	private void setPort(int port) {
		this.port = port;
	}

	/**
	 * 
	 * @return the value of host.
	 */
	private String getHost() {
		return host;
	}
	
	/**
	 * 
	 * @param host set the value of host.
	 */
	private void setHost(String host) {
		this.host = host;
	}

	/**
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void start() throws UnknownHostException, IOException {

		this.clientSocket();
	}

	/**
	 * Creates a client socket and connects it to a specific host and port based on
	 * user input, if the host can be found and the port available for connection.
	 * 
	 * @throws UnknownHostException if the host does not exist/is not available.
	 * @throws IOException          if the port is not ready for a socket
	 *                              connection.
	 */
	private void clientSocket() throws UnknownHostException, IOException {

		// Scanner class used to take in user input and set host and port values.
		this.sc = new Scanner(System.in);
		System.out.println("[INFO] Enter host name: ");
		this.setHost(sc.next());
		System.out.println("[INFO] Enter port number: ");
		this.setPort(sc.nextInt());

		try {

			// Creates a new Socket based on specified host and port.
			this.socket = new Socket(this.getHost(), this.getPort());

			// Creates a new instance of BufferedReader, OutputStream and InputStreamReader
			// to take in user input on the client side and produce an output stream for the
			// socket.
			this.clientInputReader = new BufferedReader(new InputStreamReader(System.in));
			this.clientOutputStream = socket.getOutputStream();
			this.printWriter = new PrintWriter(clientOutputStream, true);

			// Creates an input stream from the server end to the client socket and reads it
			// using a BufferedReader.
			this.serverInputStream = socket.getInputStream();
			this.serverInputReader = new BufferedReader(new InputStreamReader(serverInputStream));

			System.out.println("[INFO] Start the chat by typing and press Enter: ");

			// While-loop used to keep the chat running.
			while (keepRunning) {

				// User input read on client end and printed to output stream.
				this.clientMessage = clientInputReader.readLine();
				printWriter.println(clientMessage);
				printWriter.flush();

				// If statement used to exit and end the chat gracefully if client enters "\q".
				if (clientMessage.equalsIgnoreCase("\\q")) {
					break;
				}

				// If statement used to print message from server to the console should if it
				// contains information.
				if ((this.serverMessage = serverInputReader.readLine()) != null) {
					System.out.println(serverMessage);
				}
			}
			// Closes the socket once chat is complete.
			System.out.println("[INFO] Chat connection ended.");
			socket.close();

		} catch (UnknownHostException uho) {
			System.out.println("[ERROR] Invalid host: " + getHost() + " selected.");
		} catch (IOException ioe) {
			System.out.println("[ERROR] Invalid port: " + getPort() + " selected.");
		}

	}

}
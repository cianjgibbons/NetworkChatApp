package server;

import java.net.*;
import java.util.*;
import java.io.*;

/**
 * ChatServer class represents and contains the functionality of the Server end
 * of the chat application.
 * 
 * @author Cian Gibbons 
 * @version 1.0
 */
public class ChatServer {

	private ServerSocket serverSocket;
	private Socket clientSocket;
	private BufferedReader serverInputReader;
	private BufferedReader clientInputReader;
	private InputStream clientInputStream;
	private OutputStream outputStream;
	private PrintWriter printWriter;
	private String clientMessage;
	private String serverMessage;
	private Scanner sc;
	private int port;
	private boolean keepRunning = true;

	/**
	 * 
	 * @return the value of port.
	 */
	public int getPort() {
		return port;
	}
	
	/**
	 * 
	 * @param port set the value of port.
	 */
	public void setPort(int port) {
		this.port = port;
	}

	public void start() throws IOException {

		this.serverSocket();
	}

	/**
	 * Creates a server socket bound to a specific port based on user inout, if the
	 * port entered is available.
	 * 
	 * @throws IOException is the port is already in use/does not exist.
	 */
	private void serverSocket() throws IOException {

		this.sc = new Scanner(System.in);
		System.out.println("[INFO] Enter server port:");
		this.setPort(sc.nextInt());

		try {

			// Creates a new ServerSocket based on a user specified port number.
			this.serverSocket = new ServerSocket(getPort());
			System.out.println("[INFO] Server socket connected successfully to port " + serverSocket.getLocalPort()
					+ ". Ready to chat");

			// Accepts valid connection to the server socket and creates instance of the
			// client socket on the server end.
			this.clientSocket = serverSocket.accept();

			// Creates a new instance of BufferedReader, OutputStream and InputStreamReader
			// to take in user input on the server side and produce an output stream for the
			// serverSocket.
			this.serverInputReader = new BufferedReader(new InputStreamReader(System.in));
			this.outputStream = clientSocket.getOutputStream();
			this.printWriter = new PrintWriter(outputStream, true);

			// Creates an input stream from the client end to the server socket and reads it
			// using a BufferedReader.
			this.clientInputStream = clientSocket.getInputStream();
			this.clientInputReader = new BufferedReader(new InputStreamReader(clientInputStream));

			while (keepRunning) {

				// If statement used to print message from client to the console should if it
				// contains information.
				if ((this.clientMessage = clientInputReader.readLine()) != null) {
					System.out.println(clientMessage);
				}
				// If statement used to exit and end the chat gracefully if client enters "\q".
				if (clientMessage.equalsIgnoreCase("\\q")) {
					break;
				}

				// User input read on server end and printed to output stream.
				this.serverMessage = serverInputReader.readLine();
				printWriter.println(serverMessage);
				printWriter.flush();

			}
			// Closes the serverSocket once chat is complete.
			System.out.println("[INFO] Chat connection ended by client.");
			serverSocket.close();
		} catch (IOException e) {
			System.out.println("[ERROR] Server socket connection could not be established to port " + getPort()
					+ ". Please ensure valid port is selected.");
		}

	}

}

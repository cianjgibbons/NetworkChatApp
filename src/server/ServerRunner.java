package server;

import java.io.*;

public class ServerRunner {
	
	public static void main(String[] args) throws IOException {
		
		// Creates a new instance of the ChatServer class and calls the start method.
		ChatServer cs = new ChatServer();
		cs.start();

	}

}

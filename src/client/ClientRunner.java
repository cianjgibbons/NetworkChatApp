package client;

import java.io.*;
import java.net.*;

public class ClientRunner {

	public static void main(String[] args) throws UnknownHostException, IOException {
		
		// Creates a new instance of the ChatClient class and calls the start method.
		ChatClient cc = new ChatClient();
		cc.start();
		
	}

}

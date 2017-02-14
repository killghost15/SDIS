package sdislab1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramSocket;

public class ServerThread {

	public ServerThread() throws IOException {
	    this("QuoteServer");
	}

	public ServerThread(String name) throws IOException {
	    super(name);
	    socket = new DatagramSocket(4445);

	    try {
	        in = new BufferedReader(new FileReader("one-liners.txt"));
	    }   
	    catch (FileNotFoundException e){
	        System.err.println("Couldn't open quote file.  Serving time instead.");
	    }
	}  
}

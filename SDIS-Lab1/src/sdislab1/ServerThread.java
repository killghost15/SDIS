package sdislab1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerThread extends Thread {
	
	protected String[] plates = {"Fulano","AA|44|BB"};
	
	protected DatagramSocket socket = null;

	public ServerThread() throws IOException {
	    this("Server");
	}

	public ServerThread(String name) throws IOException {
	    super(name);
	    socket = new DatagramSocket(4445);
	    
	    byte[] buf = new byte[256];
	    DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        
	    String msg = new String(packet.getData(), 0, packet.getLength());
	    
	    System.out.println("Client connected");
	    System.out.println("Client message: "+msg);
	    
	    String[] splitted = msg.split("#");
	    
	    String answer = "blabla";
	    
	    if(splitted[0].equals("lookup")) {
	    	if(splitted[1] == plates[1])
	    		answer = "Plate "+splitted[1]+" exists!";
	    	else
	    		answer = "Plate "+splitted[1]+" not found!";
	    }
	    
	    buf = answer.getBytes();
        
	    InetAddress address = packet.getAddress();
        int port = packet.getPort();
        packet = new DatagramPacket(buf, buf.length, address, port);
        socket.send(packet);
        

	}  
}

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
	    
	    String answer = null;
	    
	    buf = answer.getBytes();
        
	    InetAddress address = packet.getAddress();
        int port = packet.getPort();
        packet = new DatagramPacket(buf, buf.length, address, port);
        socket.send(packet);
        

	}  
}

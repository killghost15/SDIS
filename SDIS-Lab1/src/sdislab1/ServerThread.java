package sdislab1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Hashtable;

public class ServerThread extends Thread {
	
	protected Hashtable<String, String> database = new Hashtable<String,String>();
	protected DatagramSocket socket = null;

	public ServerThread() throws IOException {
	    this("Server");
	}

	public ServerThread(String name) throws IOException {
	    super(name);
	    database.put( "AA|44|BB","Fulano");
	    database.put("AB|33|CC","Tipo2");
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
	    	if(database.containsKey(splitted[1]))
	    		answer = "Plate "+splitted[1]+" exists!\n"
	    				+"owner: " + database.get(splitted[1]).toString();
	    	else
	    		answer = "Plate "+splitted[1]+"NOT_FOUND";
	    }
	    
	    buf = answer.getBytes();
        
	    InetAddress address = packet.getAddress();
        int port = packet.getPort();
        packet = new DatagramPacket(buf, buf.length, address, port);
        socket.send(packet);
        

	}  
}

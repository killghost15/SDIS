

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Hashtable;

public class MultiCastServerThread extends Thread {

	protected Hashtable<String, String> database = new Hashtable<String,String>();
	protected DatagramSocket socket = null;

	public MultiCastServerThread(String port,String mac,String mac_port) throws IOException, InterruptedException {
		this("Server",port,mac,mac_port);
	}

	public MultiCastServerThread(String name,String portentry,String mac,String mac_port) throws IOException, InterruptedException {
		super(name);
		database.put( "AA|44|BB","Fulano");
		database.put("AB|33|CC","Tipo2");

		socket = new DatagramSocket(Integer.parseInt(portentry));

		socket.setSoTimeout(10000);
		byte[] buf = new byte[256];
		DatagramPacket packet;
		String[] splitted;
		String msg;
		String answer;
		InetAddress address=InetAddress.getByName(mac);
		int i=0;
		while(i<5){        // recieve data until timeout or until more than 5 packages were sent
			try {




				buf = new byte[256];
				packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);

				msg = new String(packet.getData(), 0, packet.getLength());

				
				System.out.println("multicast: <mcast_addr> <mcast_port>: <srvc_addr> <srvc_port> ");
				System.out.println("multicast: <" + mac+ "> <"+mac_port+">"+": <"+packet.getAddress() +">"+portentry);
				splitted = msg.split("#");

				answer = "blabla";

				if(splitted[0].equals("lookup")) {
					if(database.containsKey(splitted[1]))
						answer = "Plate "+splitted[1]+" exists!\n"
								+"owner: " + database.get(splitted[1]).toString();
					else
						answer = "Plate "+splitted[1]+"NOT_FOUND";
				}
				if(splitted[0].equals("register")) {
					if(database.containsKey(splitted[1]))
						answer = "-1";

					else{
						database.put(splitted[1], splitted[2]);
						answer=database.size()+"";
					}
				}

				buf = answer.getBytes();

				
				packet = new DatagramPacket(buf, buf.length, address, Integer.parseInt(mac_port));
				socket.send(packet);
			}
			catch (SocketTimeoutException e) {
				// timeout exception.
				System.out.println("Timeout reached!!! "+ name+"closed");
				i++;
				socket.close();
				return;
			}
		}

	}  
}
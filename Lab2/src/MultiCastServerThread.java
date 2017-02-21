

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

		socket.setSoTimeout(1000);
		byte[] buf = new byte[256];
		DatagramPacket packet;
		String[] splitted;
		String msg;
		String answer;
		InetAddress address=InetAddress.getByName(mac);
		int i=0;
		while (true){
			//não sei o q ele quer dizer com advertise portanto pus uma mensagem qualquer
			msg="multicast: "+ portentry;
			buf = new byte[256];
			buf = msg.getBytes();
			packet = new DatagramPacket(buf, buf.length, address, Integer.parseInt(mac_port));
			socket.send(packet);
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
							System.out.println("lookup "+splitted[1]);
							if(database.containsKey(splitted[1]))
								answer = "Plate "+splitted[1]+" exists!\n"
										+"owner: " + database.get(splitted[1]).toString();
							else
								answer = "Plate "+splitted[1]+" NOT_FOUND";
						}
						if(splitted[0].equals("register")) {
							System.out.println("register "+splitted[1]+" "+splitted[2]);
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
						i++;
					}
				catch (SocketTimeoutException e) {
					// timeout exception.
					//System.out.println("Timeout reached!!! "+ name+"closed");
					
					/*socket.close();
					return;*/
				}
			if(i>=5)
				break;
			
			

		}
		socket.close();



		

	}  
}
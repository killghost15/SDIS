

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Hashtable;

public class TCPServerThread extends Thread {

	protected Hashtable<String, String> database = new Hashtable<String,String>();
	protected DatagramSocket socket = null;

	public TCPServerThread(String port,String mac,String mac_port) throws IOException, InterruptedException {
		this("Server",port,mac,mac_port);
	}

	public TCPServerThread(String name,String portentry,String mac,String mac_port) throws IOException, InterruptedException {
		super(name);
		database.put( "AA|44|BB","Fulano");
		database.put("AB|33|CC","Tipo2");

		socket = new DatagramSocket(Integer.parseInt(portentry));

		
		byte[] buf = new byte[256];
		DatagramPacket packet;
		String[] splitted;
		String msg;
		String answer;
		InetAddress address=InetAddress.getByName(mac);
		int i=0;
		while (true){
			//não sei o q ele quer dizer com advertise portanto pus uma mensagem qualquer
			msg="multicast:"+ portentry+":"+socket.getInetAddress(); //é inutil  o que se envia o q interessa é mandar o packet
			buf = new byte[256];
			buf = msg.getBytes();
			packet = new DatagramPacket(buf, buf.length, address, Integer.parseInt(mac_port));
			socket.send(packet);
			System.out.println("Server is announcing its presence");
			socket.setSoTimeout(1000);
			try {	
						buf = new byte[256];
						//System.out.println("chegou");
						packet = new DatagramPacket(buf, buf.length);
						socket.receive(packet);

						msg = new String(packet.getData(), 0, packet.getLength());


						System.out.println("multicast: <mcast_addr> <mcast_port>: <srvc_addr> <srvc_port> ");
						System.out.println("multicast: <" + mac+ "> <"+mac_port+">"+": <"+packet.getAddress() +">"+packet.getPort());
						splitted = msg.split("#");

						answer = "teste";

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


						packet = new DatagramPacket(buf, buf.length, address, packet.getPort());
						socket.send(packet);
						i++;
					}
				catch (SocketTimeoutException e) {
					// timeout exception.
					//System.out.println("Timeout reached!!! "+ name+"closed");
					
					/*socket.close();
					return;*/
				}
			if(i>=5)//só para terminar pode terminar ao primeiro mas assim permite um teste mais abrangentes inclusive o teste de vários em simultaneo
				break;
			
			

		}
		socket.close();



		

	}  
}
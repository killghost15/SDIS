import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Peer {
	
	private static String mac="224.0.0.3";
	public Peer(){
		

	}
	public static void main(String[] args) throws NumberFormatException, IOException, NotBoundException{
		//Estabilish connection with Mac group
		MulticastSocket socket = new MulticastSocket(Integer.parseInt(args[0]));
		//socket.setTimeToLive(1);
		//host_name
        
        
		byte[] buf = new byte[256];
		InetAddress address = InetAddress.getByName(mac);
		DatagramPacket packet = null;
		packet = new DatagramPacket(buf, buf.length);
		//junta-se ao grupo com o mac
		socket.joinGroup(address);

		//Treat the message received

		socket.receive(packet);
		
		String received1 = new String(packet.getData(), 0, packet.getLength());
		System.out.println(received1);
		
		Registry registry = LocateRegistry.getRegistry(packet.getAddress().getHostName());
        RemoteInterface stub=(RemoteInterface)registry.lookup("Service");
		
		
		//Use RMI application for subprotocol function
		/*
		try {
        	String response="";
        	Registry registry = LocateRegistry.getRegistry(args[0]);
            RemoteInterface stub=(RemoteInterface)registry.lookup(args[1]);
            /*
        	if(args[2].equals("register"))
            response=stub.register(args[3], args[4]);

            if(args[2].equals("lookup")){
            	 response=stub.lookup(args[3]);
            	 }

            System.out.println("response: " + response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }*/
	}



	public void Backup(){

	}
	public void Delete(){

	}
	public void Restore(){

	}
}
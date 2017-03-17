import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Peer {
	public int id;
	public Peer(int id){
		this.id=id;
		
	}
	public static void main(String[] args) throws NumberFormatException, IOException{
		//Estabilish connection with Mac group
		MulticastSocket socket = new MulticastSocket(Integer.parseInt(args[1]));
		socket.setTimeToLive(1);

		byte[] buf = new byte[256];
		InetAddress address = InetAddress.getByName(args[0]);
		DatagramPacket packet = null;
		packet = new DatagramPacket(buf, buf.length);
		//junta-se ao grupo com o mac
		socket.joinGroup(address);
		
		//Treat the message received
		
		
		//Use RMI application for subprotocol function
		
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
            */
            System.out.println("response: " + response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
	}
	


public void Backup(){
	
}
public void Delete(){
	
}
public void Restore(){
	
}
}
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
		
		String request = new String(packet.getData(), 0, packet.getLength());
		System.out.println(request);
		String splitted[];
		splitted=request.split(" ");
		//need to convert the "PUTCHUNK" Bytes to String or find away to split the bytes data
		if(splitted[0].equals("PUTCHUNK")){
			Registry registry = LocateRegistry.getRegistry(packet.getAddress().getHostName());
	        RemoteInterface stub=(RemoteInterface)registry.lookup("SDIS");
	        //content será o body ainda n sei como o extrair
	        stub.StoreBackupProtocol(splitted[3], splitted[4], content)
			
		}
		
		
		
		//Use RMI application for subprotocol function
		
	}


}
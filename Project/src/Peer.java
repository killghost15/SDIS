import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.charset.StandardCharsets;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Peer {
	
	private static String mac="224.0.0.3";
	/* #TODO aldrabei para string pk perdi a paciencia
	private static byte[] CR ={'0','x','D'};
	private static byte[] LF={'0','x','A'};
	*/
	private static String CR="0xD";
	private static String LF="0xA";	public Peer(){
		

	}
	public static void main(String[] args) throws NumberFormatException, IOException, NotBoundException{
		//Estabilish connection with Mac group
		MulticastSocket socket = new MulticastSocket(Integer.parseInt(args[0]));
		//socket.setTimeToLive(1);
		//host_name
        
        String header=null;
        String body=null;
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
		/*System.out.println(CR+LF);
		byte b[]={0xD,0xA};
		System.out.println(new String(b.toString()));
		System.out.println(new String(b).getBytes());
		*/
		//Regex this way you can put as much spaces as you want it will still divide it the same
		String msghead=request.split(" +"+CR+LF+" +")[0];
		String msgbody=request.split(" +"+CR+LF+" +")[1];
		System.out.println(msghead);
		System.out.println(msgbody);
		System.out.println(msgbody.getBytes());
		/*
		//need to convert the "PUTCHUNK" Bytes to String or find away to split the bytes data
		if(splittedHead[0].equals("PUTCHUNK")){
			Registry registry = LocateRegistry.getRegistry(packet.getAddress().getHostName());
	        RemoteInterface stub=(RemoteInterface)registry.lookup("SDIS");
	        //content será o body ainda n sei como o extrair
	        stub.StoreBackupProtocol(splittedHead[3], Integer.parseInt(splittedHead[4]), msgbody.getBytes());
			
		}
		
		*/
		
		//Use RMI application for subprotocol function
		
	}


}
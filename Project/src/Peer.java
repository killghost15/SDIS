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
        
        String []splittedHead;
       
		byte[] buf = new byte[64000];
		InetAddress address = InetAddress.getByName(mac);
		
		DatagramPacket packetreceive = null;
		DatagramPacket packetsend = null;
		packetreceive = new DatagramPacket(buf, buf.length);
		//junta-se ao grupo com o mac
		socket.joinGroup(address);
		String msghead=null;
		String msgbody=null;
		Registry registry;
		//Treat the message received loop infinito para f
//while(true){
		socket.receive(packetreceive);
		String request = new String(packetreceive.getData(), 0, packetreceive.getLength());
		
		/*System.out.println(CR+LF);
		byte b[]={0xD,0xA};
		System.out.println(new String(b.toString()));
		System.out.println(new String(b).getBytes());
		*/
		//Regex this way you can put as much spaces as you want it will still divide it the same
		 msghead=request.split(" +"+CR+LF+" +")[0];
		 msgbody=request.split(" +"+CR+LF+" +")[1];
		 System.out.println(msghead);
		
		
		splittedHead=msghead.split(" +");
		//need to convert the "PUTCHUNK" Bytes to String or find away to split the bytes data
		if(splittedHead[0].equals("PUTCHUNK")){
			 try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			buf=new byte[256];
			//System.out.println(LocateRegistry.getRegistry(packetreceive.getAddress().getCanonicalHostName()));
			registry = LocateRegistry.getRegistry(packetreceive.getAddress().getHostName());
	        RemoteInterface stub=(RemoteInterface)registry.lookup("Teste2");
	        //content será o body ainda n sei como o extrair
	        stub.StoreBackupProtocol(splittedHead[3], Integer.parseInt(splittedHead[4]), msgbody.getBytes());
	        System.out.println("Saved file");
	        buf="STORED".getBytes();
	        
	        packetsend=new DatagramPacket(buf, buf.length,packetreceive.getAddress(),packetreceive.getPort());
			socket.send(packetsend);
		}
		socket.close();
		
		
		
		//Use RMI application for subprotocol function
		
	}
	//}


}
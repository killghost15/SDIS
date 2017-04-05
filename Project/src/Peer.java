import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.charset.StandardCharsets;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


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
		
		RemoteApplication app=new RemoteApplication();
		RemoteInterface stub=null;
		Registry registry=null;
		
		stub=(RemoteInterface)UnicastRemoteObject.exportObject(app, 0);
		registry= LocateRegistry.getRegistry();
		
		DatagramPacket packetreceive = null;
		DatagramPacket packetsend = null;
		
		//junta-se ao grupo com o mac
		socket.joinGroup(address);
		String msghead=null;
		String msgbody=null;
		String answer=null;
		try {
			registry.bind("Teste2", stub);
		} catch (AlreadyBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//Treat the message received loop infinito
		//need a break condition that is usefull
		
		while(true){
		buf = new byte[stub.getreadLength()];
		packetreceive = new DatagramPacket(buf, buf.length);
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
			 
			buf=new byte[256];
			
	        System.out.println("Saved file");
	        answer="STORED "+" "+splittedHead[1]+" "+splittedHead[2]+" " +splittedHead[3]+" " + splittedHead[4]+" " +CR+LF+" ";
	        buf=answer.getBytes();
	        
	        packetsend=new DatagramPacket(buf, buf.length,packetreceive.getAddress(),packetreceive.getPort());
			socket.send(packetsend);
			
			registry.rebind("Teste2", stub);
			
			
		}
		
		
		
	}
		//socket.close();
	}


}
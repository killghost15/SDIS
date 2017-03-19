import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class Service_App {
	//estou um bocado confuso com as mensagens n percebi se é assim e depois junto numa string ou se pode ser tudo String 
	private static char [] versionId={'1','.','0'};
	private static char [] CR={'0','x','D'};
	private static char[] LF={'0','x','A'};
	private static char [] PUTCHANK={'P','U','T','C','H','A','N','K'};
	private static String mac="224.0.0.1";
	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		if(args.length==0)
			return;
		//inicialização das variaveis 
		RemoteApplication app=new RemoteApplication();
		RemoteInterface stub=null;
		Registry registry=null;
		DatagramSocket socket = new DatagramSocket();
		

		byte[] buf = new byte[256];
		
		DatagramPacket packet;
		
		String msgHeader;
		String msgBody;
		String answer;
		InetAddress address=InetAddress.getByName(mac);
		
		
		if(args[0].equals("BACKUP") && args.length==4){
			
			//envio da mensagem para o grupo multicast
			
			msgHeader=PUTCHANK.toString()+" "+versionId.toString()+" "+senderId+" "+FileId+" "+ChunkNo+" "+args[4] +" "+CR.toString()+" "+LF.toString();
			buf = new byte[256];
			buf = msgHeader.getBytes();
			packet = new DatagramPacket(buf, buf.length, address, Integer.parseInt(args[0]));
			socket.send(packet);
			//tratar a recepcção da resposta, o backup por exemplo tem que receber o numero de respostas iguais ao replication degree
			//Cria o objecto RMI para executar os subprotocols 
			//fazer igual para os ifs todos 
			
			 stub=(RemoteInterface)UnicastRemoteObject.exportObject(app, 0);
			 registry= LocateRegistry.getRegistry();
			 registry.bind("Service", stub);
			 }	
		
			//BACKUP
		if(args[0].equals("RESTORE") && args.length==4){
			//envio da mensagem para o grupo multicast
			//tratar a recepcção da resposta, o backup por exemplo tem que receber o numero de respostas iguais ao replication degree
			//Cria o objecto RMI para executar os subprotocols 
			//fazer igual para os ifs todos 
			
			 stub=(RemoteInterface)UnicastRemoteObject.exportObject(app, 0);
			 registry= LocateRegistry.getRegistry();
			 registry.bind("Service", stub);
			 }
				//RESTORE 
		if(args[0].equals("DELETE")){
			//envio da mensagem para o grupo multicast
			//tratar a recepcção da resposta, o backup por exemplo tem que receber o numero de respostas iguais ao replication degree
			//Cria o objecto RMI para executar os subprotocols 
			//fazer igual para os ifs todos 
			
			 stub=(RemoteInterface)UnicastRemoteObject.exportObject(app, 0);
			 registry= LocateRegistry.getRegistry();
			 registry.bind("Service", stub);
			 }
			//DELETE
		if(args[0].equals("STATE")){
			//envio da mensagem para o grupo multicast
			//tratar a recepcção da resposta, o backup por exemplo tem que receber o numero de respostas iguais ao replication degree
			//Cria o objecto RMI para executar os subprotocols 
			//fazer igual para os ifs todos 
			
			 stub=(RemoteInterface)UnicastRemoteObject.exportObject(app, 0);
			 registry= LocateRegistry.getRegistry();
			 registry.bind("Service", stub);
			 }
			//Space allocation management
		

	}
	

}

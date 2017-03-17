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

	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		if(args.length==0)
			return;
		//inicialização das variaveis 
		RemoteApplication app=new RemoteApplication();
		RemoteInterface stub=null;
		Registry registry=null;
		DatagramSocket socket = new DatagramSocket(Integer.parseInt(portentry));
		

		byte[] buf = new byte[256];
		DatagramPacket packet;
		String[] splitted;
		String msg;
		String answer;
		InetAddress address=InetAddress.getByName(mac);
		
		
		if(args[0].equals("BACKUP")){
			//envio da mensagem para o grupo multicast
			//tratar a recepcção da resposta, o backup por exemplo tem que receber o numero de respostas iguais ao replication degree
			//Cria o objecto RMI para executar os subprotocols 
			//fazer igual para os ifs todos 
			
			 stub=(RemoteInterface)UnicastRemoteObject.exportObject(app, 0);
			 registry= LocateRegistry.getRegistry();
			 registry.bind("Service", stub);
			 }	
		
			//BACKUP
		if(args[0].equals("RESTORE")){
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

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class Server {

	
	
	public Server() {
		
	}
	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		
		if(args.length!=1){
			System.out.println("Usage: java Server <remote_object_name>");
			return ;}
		
		RemoteApplication app=new RemoteApplication();
		RemoteInterface stub=(RemoteInterface)UnicastRemoteObject.exportObject(app, 0);

		
		Registry registry = LocateRegistry.getRegistry();
        registry.bind(args[0], stub);
        
        
        
        
        registry.rebind(args[0], stub);
	}

}

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;


public class Server {

	protected Hashtable<String, String> database = new Hashtable<String,String>();
	
	public Server() {
		database.put( "AA|44|BB","Fulano");
		database.put("AB|33|CC","Tipo2");
	}
	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		Server s=new Server();
		RemoteApplication app=new RemoteApplication();
		RemoteInterface stub=(RemoteInterface)UnicastRemoteObject.exportObject(app, 0);

		
		Registry registry = LocateRegistry.getRegistry();
        registry.bind("R", stub);
        
        registry.rebind("R2", stub);
	}

}

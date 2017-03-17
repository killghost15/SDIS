import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Peer {
	public int id;
	public Peer(int id){
		this.id=id;
		
	}
	public static void main(String[] args){
		//Estabilish connection with Mac group
		
		
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
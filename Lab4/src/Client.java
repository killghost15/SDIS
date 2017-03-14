import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Client {
	private Client() {}

    public static void main(String[] args) {

    	 if (args.length != 4 && args.length !=5) {
             System.out.println("Usage: java Client <host_name> <remote_object_name> <oper> <opnd> * ");
             return;
        }
    		
        try {
        	String response="";
        	Registry registry = LocateRegistry.getRegistry(args[0]);
            RemoteInterface stub=(RemoteInterface)registry.lookup(args[1]);
            
        	if(args[2].equals("register"))
            response=stub.register(args[3], args[4]);
            
            if(args[2].equals("lookup")){
            	 response=stub.lookup(args[3]);
            	 }
            
            System.out.println("response: " + response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

}

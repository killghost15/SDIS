import java.rmi.Remote;
import java.rmi.RemoteException;


public interface RemoteInterface extends Remote{
	
	public String register(String plate,String owner)throws RemoteException;
	public String lookup(String plate)throws RemoteException;

}

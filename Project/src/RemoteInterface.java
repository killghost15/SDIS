import java.rmi.Remote;
import java.rmi.RemoteException;


public interface RemoteInterface extends Remote{
	
	public String RestoreProtocol()throws RemoteException;
	public String DeleteProtocol()throws RemoteException;
	public String ReclaimProtocol()throws RemoteException;
	public String BackupProtocol(String filename, String Chunkname)throws RemoteException;
}


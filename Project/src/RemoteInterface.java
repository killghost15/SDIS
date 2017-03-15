import java.rmi.Remote;
import java.rmi.RemoteException;


public interface RemoteInterface extends Remote{
	public String BackupProtocol()throws RemoteException;
	public String RestoreProtocol()throws RemoteException;
	public String DeleteProtocol()throws RemoteException;
	public String ReclaimProtocol()throws RemoteException;
}


import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface RemoteInterface extends Remote{

	public int getreadLength ()throws RemoteException;
	public String RestoreProtocol(String filename, int Chunknr) throws FileNotFoundException, IOException;

	public String ReclaimProtocol()throws RemoteException;
	//public String BackupProtocol(String filename, String Chunkname)throws RemoteException;
	public String StoreBackupProtocol(String filename, int Chunknr, byte[] content) throws RemoteException;
	String DeleteProtocol(String filename, int Chunknr)throws RemoteException;
}


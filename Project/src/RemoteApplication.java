import java.rmi.RemoteException;
import java.util.Hashtable;


public class RemoteApplication implements RemoteInterface{
	protected Hashtable<String, String> database = new Hashtable<String,String>();

	@Override
	public String BackupProtocol() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String RestoreProtocol() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String DeleteProtocol() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String ReclaimProtocol() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	

}

package Client;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CBClient extends Remote, Serializable {
	public void notifyMe(String msg,String user) throws RemoteException;
	
	public void messageNew(String user) throws RemoteException;
	
	public void messageLeft(String user) throws RemoteException;
}

package Serveur;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface ServeurIntf extends Remote{

	static Vector<String> messages = new Vector<String>(0);

	public String messageBienvenue() throws RemoteException;
	
	public Vector<String> getMessages(int lastMessageKey) throws RemoteException;
	
	public void ecrireMessage(String msg,String user) throws RemoteException;

}
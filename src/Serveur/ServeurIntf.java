package Serveur;

import java.rmi.Remote;
import java.rmi.RemoteException;
import Client.CBClient;

public interface ServeurIntf extends Remote{

	public void ecrireMessage(String msg,String user) throws RemoteException;
	
	public boolean newUser(String username, CBClient callback) throws RemoteException;
	
	public void userLeft(String username) throws RemoteException;

}
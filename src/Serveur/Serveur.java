package Serveur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import Client.CBClient;

public class Serveur extends UnicastRemoteObject implements ServeurIntf{
	
	private static final long serialVersionUID = 1L;
	private Vector<String> users = new Vector<String>(0);
	private Vector<CBClient> CBs = new Vector<CBClient>(0);
	
	public Serveur() throws RemoteException{
		super(1099);
	}
	
	
	public void ecrireMessage(String msg,String user) throws RemoteException {
		for(CBClient cb : CBs) {
			cb.notifyMe(msg, user);
		}
	}


	public boolean newUser(String username, CBClient callback) throws RemoteException {
		if (!users.contains(username)) {
			users.add(username);
			CBs.add(callback);
			return true;
		}
		else return false;
	}

	public void userLeft(String username) throws RemoteException {
		if(users.contains(username)) users.remove(username);		
	}



}
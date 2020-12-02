package Serveur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Vector;

import Client.CBClient;

public class Serveur extends UnicastRemoteObject implements ServeurIntf{
	
	private static final long serialVersionUID = 1L;
	private HashMap<Integer,String> users = new HashMap<Integer,String>(0);
	private HashMap<Integer,CBClient> CBs = new HashMap<Integer,CBClient>(0);
	
	
	public Serveur() throws RemoteException{
		super(1099);
	}
	
	
	public void ecrireMessage(String msg,String user) throws RemoteException {
		Vector<Integer> buff = new Vector<Integer>(0);
		CBs.forEach((key,callback) -> {
			if(!user.equals(users.get(key)))
				try {
					callback.notifyMe(msg,"\n" + user);
				} catch (RemoteException e) {

				}
		});
		buff.forEach(key -> {
			CBs.remove(key);
			users.remove(key);
		});
	}


	public boolean newUser(String username, CBClient callback) throws RemoteException {
		Vector<Integer> buff = new Vector<Integer>(0);
		
		if (!users.containsValue(username)) {
			int id = (int) (Math.random()*10000 + Math.random()*100);
			users.put(id,username);
			CBs.put(id,callback);
			CBs.forEach((key,value) -> {
				try {
					CBs.get(key).messageNew(username);
				} catch (RemoteException e) {
					buff.add(key);
				}
			});
			buff.forEach(left -> {
				users.remove(left);
				CBs.remove(left);
			});
			return true;
		}
		else return false;
	}

	public void userLeft(String username) throws RemoteException {
		Vector<Integer> buff = new Vector<Integer>(0);
		if(users.containsValue(username)) {
			users.forEach((key,user) -> {
				if(user.equals(username)) buff.add(key);
				else {
					try {
						CBs.get(key).messageLeft(username);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			});
			users.remove(buff.get(0));
			CBs.remove(buff.get(0));
		}
		
	}


	
	public int userNumber() throws RemoteException {
		return users.size();
	}


	



}
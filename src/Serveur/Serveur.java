package Serveur;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class Serveur extends UnicastRemoteObject implements ServeurIntf{
	
	private static final long serialVersionUID = 1L;
	
	
	public Serveur() throws RemoteException{
		super(1099);
	}
	
	public String messageBienvenue() throws RemoteException{
		return "Bienvenue!";
	}
	
	 public static void main(String args[]) throws Exception {
		 
	        try { 
	            LocateRegistry.createRegistry(1099); 
	        } catch (RemoteException e) {
	        }
	        Serveur chatServeur = new Serveur();
	        Naming.rebind("//localhost/RmiServer", chatServeur);
	        System.out.println("Serveur pret!");
	 }

	
	public Vector<String> getMessages(int lastMessagePos) throws RemoteException {
		
		Vector<String> msg = new Vector<String>(0);
		for(int i = lastMessagePos;i<messages.size();i++) {
			msg.add(messages.get(i));
		}
		
		return msg;
	}
	
	public void ecrireMessage(String msg,String user) throws RemoteException {
		messages.add(user + " : " + msg);
	}



}
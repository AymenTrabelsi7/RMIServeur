package Serveur;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class MainServeur {
	 public static void main(String args[]) throws Exception {
		 
	        try { 
	            LocateRegistry.createRegistry(1099); 
	        } catch (RemoteException e) {
	        }
	        Serveur chatServeur = new Serveur();
	        Naming.rebind("//localhost/RmiServer", chatServeur);
	        System.out.println("Serveur pret!");
	 }
}

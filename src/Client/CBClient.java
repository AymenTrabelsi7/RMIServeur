package Client;

import java.rmi.Remote;

public interface CBClient extends Remote {
	public void notifyMe(String msg,String user);
}

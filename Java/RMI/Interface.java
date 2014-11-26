package lichen;

//A simple RMI interface file - M. Liu
import java.rmi.*;

/**
 * This is a remote interface.
 * 
 * @author Lichen
 */

public interface Interface extends Remote {
	/**
	 * This remote method returns a message.
	 * 
	 * @param name
	 *            - a String containing a name.
	 * @return a String message.
	 */
	
	public boolean register(String userName,String userPassword) throws RemoteException;
	
	
} // end interface

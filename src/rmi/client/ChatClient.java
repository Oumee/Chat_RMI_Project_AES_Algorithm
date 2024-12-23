package rmi.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatClient extends Remote {
    
	// Diffusion du message a tous les clients
	void receiveMessage(byte[] message) throws RemoteException;

	void receiveKey(byte[] encoded)throws RemoteException;

 	
}

package rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import rmi.client.ChatClient;

public class ChatServiceImpl extends UnicastRemoteObject implements ChatService {
   
	private static final long serialVersionUID = 1L;
	
	private final List<ChatClient> clients;

	private SecretKey sharedKey;
	
	
    public ChatServiceImpl() throws RemoteException {
        super();
        clients = new ArrayList<>();
        
        KeyGenerator keyGen;
		try {
			keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(128);
	        sharedKey = keyGen.generateKey();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
     
    // prend un message et l envoyer tous les clients
    public synchronized void sendMessage(byte[] message) throws RemoteException {
        // essayer de lire mais on a arrive pas 
    	
    	for (ChatClient client : clients) {
         
        	client.receiveMessage(message); // Diffuse le message a tous les clients
        }
    }
    
    // enregistrer un client
    public synchronized void registerClient(ChatClient client) throws RemoteException {
        client.receiveKey(sharedKey.getEncoded());

    	clients.add(client);
        // envoie la cle partagee au client
        System.out.print("Un nouvel utilisateur s'est connecte !");
 
    }
}

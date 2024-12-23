package rmi.client;

import rmi.server.ChatService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.util.Base64;


public class ChatClientImpl extends UnicastRemoteObject implements ChatClient, Runnable {
   
	private static final long serialVersionUID = 1L;
	private  String name=null;
    private ChatService chatService;
	KeyGenerator keyGen = null;
	SecretKey secretKey = null;
	Cipher cipher = null;	
	
    public ChatClientImpl(String name, ChatService chatService) throws RemoteException {
        super();
        this.chatService = chatService;
        this.name = name;
        try {
			cipher = Cipher.getInstance("AES");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
        chatService.registerClient(this);
        
    }
    
    // affiche le message 
    public void receiveMessage(byte[] message) throws RemoteException {
    	 	
    	// chiffrer
    	try {
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    	byte[] decryptedMessage = null;
		try {
	    	// Dechiffrer le message !!
			decryptedMessage = cipher.doFinal(message);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    	System.out.println("Message dechiffre : " + new String(decryptedMessage));
    	
    }
    
    // affiche son nom
    public String getName() {
        return name;
    }
    
 
 	public void run(){
     try (Scanner scanner = new Scanner(System.in)) {
		
    	String message;
        // Generer une cle AES
           

		while (true) {
			     message = scanner.nextLine();
		        // Chiffrer un message
 		        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		        String nvmessage = name + " : " + message;
		        // chiffrer le massage !!
		        byte[] encryptedMessage = cipher.doFinal(nvmessage.getBytes());
			 try {
				chatService.sendMessage(encryptedMessage);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		 }
	  } catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
    }

 	public void receiveKey(byte[] key) throws RemoteException 
 	{
 		 // Charger la cle recue
 	    secretKey = new SecretKeySpec(key, "AES");
 	    System.out.println("Cle AES recue du serveur.");	 
		
	}
    
}

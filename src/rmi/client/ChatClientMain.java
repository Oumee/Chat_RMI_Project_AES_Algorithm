package rmi.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
 

import rmi.server.ChatService;

public class ChatClientMain {
	
    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
      
    	String chatServerURL = "rmi://localhost:1096/ChatService"; 
    	ChatService chatservice = (ChatService) Naming.lookup(chatServerURL);
     	new Thread(new ChatClientImpl(args[0], chatservice)).start();
        
    }
}

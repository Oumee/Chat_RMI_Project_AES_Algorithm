package rmi.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import rmi.server.*;
public class ChatServer {
    public static void main(String[] args) throws RemoteException, MalformedURLException {
     try {
        Registry registry = LocateRegistry.createRegistry(1096); // Creer un registre RMI sur le port 1096
       // Naming.rebind("ChatService",new ChatServiceImpl()); // Enregistre le service avec le nom "ChatService"
               
       // Creer et enregistrer un objet distant
       ChatService chatService = new ChatServiceImpl();
       registry.rebind("ChatService", chatService);

            System.out.println("Serveur de chat lanc !");
      } catch (Exception e) {
           e.printStackTrace();
       }
    }
}

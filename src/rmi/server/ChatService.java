package rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import rmi.client.ChatClient;

public interface ChatService extends Remote {
    // appeler par le client afin d envoyer le message au serveur
	void sendMessage(byte[] message) throws RemoteException;
	// Enregistrer un client aupres du serveur afin qu'il puisse recevoir les messages diffuses.
    // le client est ajoute a la table client deja existante
	void registerClient(ChatClient client) throws RemoteException;
 }

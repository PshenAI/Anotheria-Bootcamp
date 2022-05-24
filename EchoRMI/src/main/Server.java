package main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements Echo{

    @Override
    public Track echo(Track aTrack) {
        System.out.println("Request: " + aTrack);
        return aTrack;
    }

    public static void main(String[] args) {
        try{
            Server server = new Server();
            Echo stub = (Echo) UnicastRemoteObject.exportObject(server, 0);

            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Echo", stub);
            System.out.println("Server's up!");

        } catch (Exception ex){
            System.err.println("Server exception: " + ex);
            ex.printStackTrace();
        }
    }
}

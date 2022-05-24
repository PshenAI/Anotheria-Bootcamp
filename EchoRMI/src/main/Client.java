package main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class Client {

    public static void main(String[] args) {
        String host = (args.length < 1) ? null : args[0];
        try {
            Track request = new Track("Rose Quartz", "Toro y Moi","4:16", List.of("Keys", "Drums", "Bass", "Kongo"));
            System.out.println("Request: " + request);
            Registry registry = LocateRegistry.getRegistry(host);
            Echo stub = (Echo) registry.lookup("Echo");
            Track response = stub.echo(request);
            System.out.println("Response: " + response);
        } catch (Exception ex){
            System.err.println("Client exception: " + ex);
            ex.printStackTrace();
        }
    }
}

package main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    public static void main(String[] args) {
        String host = (args.length < 1) ? null : args[0];
        try {
            String request = "SomeTest";
            System.out.println("Request: " + request);
            Registry registry = LocateRegistry.getRegistry(host);
            Echo stub = (Echo) registry.lookup("Echo");
            String response = stub.echo(request);
            System.out.println("Response: " + response);
        } catch (Exception ex){
            System.err.println("Client exception: " + ex);
            ex.printStackTrace();
        }
    }
}

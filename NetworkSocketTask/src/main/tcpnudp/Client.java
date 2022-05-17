package main.tcpnudp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;

public class Client {

    public static final int PORT = 19000;
    public static final String HOST = "localhost";

    public static void main(String[] args) {

        //===============================
        //TCP-PROTOCOL
        //===============================

        try(Socket socket = new Socket(HOST, PORT)){//TCP PROTOCOL
            try(InputStream is = socket.getInputStream();
                OutputStream os = socket.getOutputStream()){

                String request= "";
                if(args.length > 0){
                    for (String arg: args) {
                        request = request + " " + arg;
                    }
                } else {
                    request = "test";
                }
                os.write(request.getBytes());
                os.flush();

                byte[] response = new byte[32 * 1024];
                int readBytes = is.read(response);

                System.out.printf("Server: %s", new String(response, 0, readBytes));
            }
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }

//        //===============================
//        //UDP-PROTOCOL
//        //===============================
//
//        try(DatagramSocket client = new DatagramSocket()){
//                String request= "";
//                if(args.length > 0){
//                    for (String arg: args) {
//                        request = request + " " + arg;
//                    }
//                } else {
//                    request = "test";
//                }
//
//            InetAddress IPAddress = InetAddress.getByName(HOST);
//            byte[] reqBytes = request.getBytes();
//            DatagramPacket requestPacket = new DatagramPacket(reqBytes, reqBytes.length,IPAddress,PORT);
//            client.send(requestPacket);
//
//            byte[] respData = new byte[15];
//            DatagramPacket responsePacket = new DatagramPacket(respData, respData.length);
//            client.receive(responsePacket);
//
//            System.out.printf("Server: %s", new String(responsePacket.getData()).trim());
//        }  catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
}

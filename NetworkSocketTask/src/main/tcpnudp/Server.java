package main.tcpnudp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;


class RequestHandler implements Runnable{

    private static final File storage = new File("C:\\Users\\Velvet X\\Documents\\Java Studies" +
            "\\Anotheria-Bootcamp\\NetworkSocketTask\\storage");
    Socket comSocket;
    Socket dataSocket;

    public RequestHandler(Socket comSocket) {
        this.comSocket = comSocket;
    }

    @Override
    public void run() {
        try(InputStream is = comSocket.getInputStream();
            OutputStream os = comSocket.getOutputStream()){

            byte[] request = new byte[32 * 1024];
            int readBytes = is.read(request);
            String test = new String(request, 0, readBytes);
            System.out.printf("Client: %s", test);

            String response = "Request accepted.";
            os.write(response.getBytes());
            os.flush();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
}

public class Server {

    public static final int COMPORT = 19000;
    public static final int DATAPORT = 19001;

    public static void main(String[] args) {

        //===============================
        //TCP-PROTOCOL
        //===============================

        try(ServerSocket comSocket = new ServerSocket(COMPORT);
        ServerSocket dataSocket = new ServerSocket(DATAPORT)){
            while(true){
                System.out.println("Server on, waiting for requests...");

                Socket socket = comSocket.accept();
                Thread respThread = new Thread(new RequestHandler(socket));
                respThread.start();

                System.out.println("Request accepted from " + socket.getInetAddress());
            }
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }

//        //===============================
//        //UDP-PROTOCOL
//        //===============================
//
//        try(DatagramSocket server = new DatagramSocket(19000)){
//            System.out.println("Server on, waiting for requests...");
//
//            byte[] reqData = new byte[15];
//            DatagramPacket requestPacket = new DatagramPacket(reqData, reqData.length);
//            server.receive(requestPacket);
//
//            System.out.println("Request accepted from " + requestPacket.getAddress());
//
//            String response = "Request accepted.";
//            byte[] sendData = response.getBytes();
//            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, requestPacket.getAddress(),
//                    requestPacket.getPort());
//            server.send(sendPacket);
//
//            System.out.printf("Server: %s", new String(requestPacket.getData()).trim());
//        }  catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
}

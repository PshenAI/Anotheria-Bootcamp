package main.ftp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class RequestHandler implements Runnable{

    private static final File serverStorage = new File("C:\\Users\\Velvet X\\Documents\\Java Studies" +
            "\\Anotheria-Bootcamp\\NetworkSocketTask\\serverStorage");
    Socket comSocket;
    ServerSocket dataSocket;

    public RequestHandler(Socket comSocket, ServerSocket dataSocket) {
        this.comSocket = comSocket;
        this.dataSocket = dataSocket;
    }

    @Override
    public void run() {
        try(InputStream is = comSocket.getInputStream();
            OutputStream os = comSocket.getOutputStream()){

            byte[] request = new byte[32 * 1024];
            int readBytes = is.read(request);
            String[] command = new String(request, 0, readBytes).split(" ");

            switch (command[0]) {
                case "DIR" -> dirCommand(os);
                case "PUT" -> putCommand(os, command[1]);
                case "GET" -> getCommand(os, command[1]);
                default -> wrongCommand(os);
            }


        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private void wrongCommand(OutputStream os) throws IOException {
        String response = "Wrong command. Try again!";
        os.write(response.getBytes());
        os.flush();
    }
    private void dirCommand(OutputStream os) throws IOException {
        String response = "";
        for (String file: serverStorage.list()) {
            response += file + "\n";
        }
        os.write(response.getBytes());
        os.flush();
    }

    private void putCommand(OutputStream os, String fileName) throws IOException {
        Socket socket = dataSocket.accept();

        try(InputStream is = socket.getInputStream();
            FileOutputStream fos = new FileOutputStream(serverStorage + "\\" + fileName)){

            byte[] file = toByteArray(is);
            fos.write(file);

        }

        os.write(("File accepted.").getBytes());
        os.flush();
    }

    private void getCommand(OutputStream osComm, String fileName) throws IOException{
        Socket socket = dataSocket.accept();
        try (OutputStream os = socket.getOutputStream()) {

            Path path = Paths.get(serverStorage + "\\" + fileName);

            os.write(Files.readAllBytes(path));
            os.flush();
        }
        osComm.write(("File sent.").getBytes());
        osComm.flush();
    }

    public static byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int len;

        while ((len = in.read(buffer)) != -1)
        {
            os.write(buffer, 0, len);
        }

        return os.toByteArray();
    }
}

public class Server {
    public static final int COMPORT = 19000;
    public static final int DATAPORT = 19001;

    public static void main(String[] args) {

        try (ServerSocket comSocket = new ServerSocket(COMPORT);
             ServerSocket dataSocket = new ServerSocket(DATAPORT)) {
            while (true) {
                System.out.println("Server on, waiting for requests...");

                Socket socket = comSocket.accept();
                Thread respThread = new Thread(new RequestHandler(socket, dataSocket));
                respThread.start();

                System.out.println("Request accepted from " + socket.getInetAddress());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

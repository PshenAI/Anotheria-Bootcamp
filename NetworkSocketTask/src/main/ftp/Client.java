package main.ftp;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Client {

    public static final int PORT = 19000;
    public static final int DATAPORT = 19001;
    public static final String HOST = "localhost";
    public static final String clientStorage = "C:\\Users\\Velvet X\\Documents\\Java Studies\\" +
            "Anotheria-Bootcamp\\NetworkSocketTask\\clientStorage";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        input: while(true) {
            try (Socket socket = new Socket(HOST, PORT);
                 Socket dataSocket = new Socket(HOST, DATAPORT)) {
                try (InputStream is = socket.getInputStream();
                     OutputStream os = socket.getOutputStream()) {

                    System.out.println("Enter command: ");
                    String command = sc.nextLine();
                    String[] comArray = command.split(" ");

                    switch (comArray[0]) {
                        case "stop" -> {
                            break input;
                        }
                        case "PUT" -> {
                            sendCommand(os, command);
                            sendToServer(command, dataSocket);
                        }
                        case "GET" -> {
                            sendCommand(os, command);
                            getFromServer(command, dataSocket);
                        }
                        default -> {
                            sendCommand(os, command);
                        }
                    }

                    byte[] response = new byte[32 * 1024];
                    int readBytes = is.read(response);

                    System.out.printf("Server:\n%s", new String(response, 0, readBytes));
                    System.out.println("\n");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void getFromServer(String command, Socket dataSocket) throws IOException{
        String fileName = command.split(" ")[1];
            try (InputStream is = dataSocket.getInputStream();
                 FileOutputStream fos = new FileOutputStream(clientStorage + "\\" + fileName)) {

                byte[] file = toByteArray(is);
                fos.write(file);
            }
    }

    public static void sendToServer(String command, Socket dataSocket) throws IOException{
            try (OutputStream os = dataSocket.getOutputStream()) {
                String fileName = command.split(" ")[1];

                Path path = Paths.get(clientStorage + "\\" + fileName);

                os.write(Files.readAllBytes(path));
                os.flush();
            }
    }

    public static void sendCommand(OutputStream os, String command) throws IOException{
        os.write(command.getBytes());
        os.flush();
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


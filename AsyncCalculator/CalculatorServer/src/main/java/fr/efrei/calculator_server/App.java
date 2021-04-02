package fr.efrei.calculator_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class App 
{
    public static void main( String[] args ) throws IOException, SocketException
    {
        ServerSocket serverSocket = new ServerSocket(4242);

        System.out.println("Server started on port 4242...");

        while(true) {
            Socket sock = serverSocket.accept();
            System.out.println("New connection " + sock);

            new Thread(new SocketRunnable(sock)).start();
        }
    }
}

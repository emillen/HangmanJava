package hangman.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by daseel on 2016-11-09.
 */
public class ServerHandler {


    private static final int PORT_NUMBER = 4100;

    public static void main(String[] args) {


        try {
            ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
            System.out.println( "IP address: " + InetAddress.getLocalHost().getHostAddress());
            System.out.println( "Port Number: " + serverSocket.getLocalPort());

            while(true) {
                Socket clientSocket = serverSocket.accept();
                Thread gameServer = new GameServer(clientSocket);
                gameServer.setPriority(gameServer.getPriority() + 1);
                gameServer.start();
            }

        } catch (IOException e) {
            System.out.println(
                    e.getMessage()
            );
        }
    }
}

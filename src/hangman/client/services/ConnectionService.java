package hangman.client.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by daseel on 2016-11-09.
 */
public class ConnectionService extends Service<Socket> {

    private final int TIMEOUT_LENGTH = 1000;
    private InetAddress ip;
    private int port;

    public ConnectionService(InetAddress ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public Task<Socket> createTask(){

        return new Task<Socket>(){
            public Socket call() throws IOException{

                Socket serverSock = new Socket();
                serverSock.connect(new InetSocketAddress(ip, port), TIMEOUT_LENGTH);
                return serverSock;
            }

        };
    }
}

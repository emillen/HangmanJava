package hangman.client.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by daseel on 2016-11-09.
 */
public class ConnectionService extends Service<Socket> {

    private final int TIMEOUT_LENGTH = 10000;
    private InetAddress ip;
    private int port;

    public ConnectionService(InetAddress ip, int port) {
        this.ip = ip;
        this.port = port;

        setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent e) {

            }
        });
    }

    public Task<Socket> createTask(){

        return new Task<Socket>(){
            public Socket call() throws IOException{

                Socket serverSock = new Socket(ip, port);
                serverSock.setSoTimeout(TIMEOUT_LENGTH);
                return serverSock;
            }

        };
    }
}

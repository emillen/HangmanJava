package hangman.client.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.net.Socket;

/**
 * Created by daseel on 2016-11-09.
 */
public class GameService extends Service<Socket>{

    public GameService(Socket socket){
        // TODO
    }

    @Override
    protected Task<Socket> createTask() {
        return new Task<Socket>() {
            @Override
            protected Socket call() throws Exception {
                return null;
            }
        };
    }
}

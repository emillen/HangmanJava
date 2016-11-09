package hangman.client.services;

import hangman.communication.Message;
import hangman.communication.TurnResult;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.*;
import java.net.Socket;

/**
 * Created by daseel on 2016-11-09.
 */
public class StartGameService extends Service<TurnResult>{

    Socket socket;

    public StartGameService(Socket socket){
        this.socket = socket;
    }

    @Override
    protected Task<TurnResult> createTask() {
        return new Task<TurnResult>() {
            @Override
            protected TurnResult call() throws Exception {

                PrintWriter writer = new PrintWriter(socket.getOutputStream());
                writer.println(Message.START_GAME);
                return getResult();
            }
        };
    }

    private TurnResult getResult() throws IOException, ClassNotFoundException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        if(reader.readLine().equals(Message.NEW_TURN))
            return (TurnResult) new ObjectInputStream(socket.getInputStream()).readObject();
        else
            throw new IOException();
    }
}

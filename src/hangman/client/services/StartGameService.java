package hangman.client.services;

import hangman.communication.Message;
import hangman.communication.Result;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.*;
import java.net.Socket;

/**
 * Created by daseel on 2016-11-09.
 */
public class StartGameService extends Service<Result> {

    private Socket socket;

    public StartGameService(Socket socket) {
        this.socket = socket;
    }

    @Override
    protected Task<Result> createTask() {
        return new Task<Result>() {
            @Override
            protected Result call() throws Exception {

                PrintWriter writer = new PrintWriter(socket.getOutputStream());
                writer.println(Message.START_GAME);
                writer.flush();
                return getResult();
            }
        };
    }

    private Result getResult() throws IOException, ClassNotFoundException {
        return (Result) new ObjectInputStream(socket.getInputStream()).readObject();
    }
}

package hangman.client.services;

import hangman.communication.Message;
import hangman.communication.Result;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by daseel on 2016-11-09.
 */
public class GameService extends Service<Result> {

    private Socket socket;
    private String guess;

    public GameService(Socket socket, String guess) {

        this.socket = socket;
        this.guess = guess;
    }

    @Override
    protected Task<Result> createTask() {
        return new Task<Result>() {
            @Override
            protected Result call() throws Exception {
                PrintWriter writer = new PrintWriter(socket.getOutputStream());
                writer.println(Message.GUESS);
                writer.println(guess);
                writer.flush();

                return (Result) new ObjectInputStream(socket.getInputStream()).readObject();
            }
        };
    }
}
